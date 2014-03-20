package jp.co.lizzy.mozakinCrawler;

import java.io.IOException;
import java.util.ArrayList;

import jp.co.lizzy.common.ElapsedTime;
import jp.co.lizzy.common.UnixTime;
import jp.co.lizzy.common.http.HttpProtocol;
import jp.co.lizzy.common.http.HttpQuery;
import jp.co.lizzy.mozakinCrawler.db.model.ModelResponse;
import jp.co.lizzy.mozakinCrawler.entity.BBSMessage;
import jp.co.lizzy.mozakinCrawler.entity.BBSThread;
import jp.co.lizzy.mozakinCrawler.entity.ThreadInformation;
import jp.co.lizzy.mozakinCrawler.utility.HtmlParser;
import jp.co.lizzy.mozakinCrawler.utility.ImageDownloader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BBSThreadCrawler {
	private static final Logger log = LogManager.getLogger(BBSThreadCrawler.class);

	public static boolean doCrawl(ThreadInformation threadInformation) throws IOException {
		log.trace("start");
		log.info("start Crawl thread ID: {}", threadInformation.getThreadId());
		ElapsedTime time = new ElapsedTime();

		BBSThread bbsThread = new BBSThread();
		bbsThread.setThreadInformation(threadInformation);

		for (int page = 0; page < threadInformation.getNumberOfPages(); page ++) {
			ArrayList<BBSMessage> responses = crawlPage(threadInformation.getThreadId(), page);
			if (page != threadInformation.getNumberOfPages() - 1
				&& responses.size() < 6) {
				log.warn("Response message is smaller than 6. Parser lost response messages." );
			}
			if (responses != null) {
				bbsThread.addResponses(responses);
			}
		}
		log.info("thread ID: {}; total Response: {}; elapsed time: {} sec.",
				bbsThread.getThreadInformation().getThreadId(), bbsThread.getResponseSize(), time.get());
		log.trace("end");
		return true;
	}

	/**
	 *
	 * BBSのアドレスサンプル:<br />
	 * http://bbs2.mozakin.com/bbs.php?t=1395148439&stg=38845&page=&rpage=0
	 * @param threadId
	 * @param page
	 * @throws IOException
	 */
	private static ArrayList<BBSMessage> crawlPage(int threadId, int page) throws IOException {
		log.entry(threadId, page);

		HttpQuery query = new HttpQuery();
		query
			.setParameter("t", UnixTime.now())
			.setParameter("stg", threadId)
			.setParameter("page", "")
			.setParameter("rpage", page);
		log.info("loading index page {}", page);
		String htmlContents = HttpProtocol.get(Const.BBS_URL, query, Const.BBS_ENCODE);
		ArrayList<BBSMessage> responses = HtmlParser.parseResponses(htmlContents);
		if (responses == null) {
			return null;
		}

		log.info("Number Of Message: {}", responses.size());

		for (int index = 0; index < responses.size(); index ++) {
			ModelResponse.save(responses.get(index));
			if (responses.get(index).getImageId() != null) {
				ImageDownloader.saveImage(responses.get(index));
			}
		}
		return responses;
	}
}
