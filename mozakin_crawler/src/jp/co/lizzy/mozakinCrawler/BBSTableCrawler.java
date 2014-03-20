package jp.co.lizzy.mozakinCrawler;

import java.io.IOException;

import jp.co.lizzy.common.HttpProtocol;
import jp.co.lizzy.common.HttpQuery;
import jp.co.lizzy.common.UnixTime;
import jp.co.lizzy.mozakinCrawler.db.model.ModelThreadInformation;
import jp.co.lizzy.mozakinCrawler.entity.BBSTable;
import jp.co.lizzy.mozakinCrawler.entity.ThreadInformation;
import jp.co.lizzy.mozakinCrawler.utility.ImageDownloader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BBSTableCrawler {
	private static final Logger log = LogManager.getLogger(BBSTableCrawler.class);

	public static void doCrawl(int startPage, int endPage) throws IOException {
		for (int page = startPage; page <= endPage; page ++) {
			crawlPage(page);
		}
	}

	private static void crawlPage(int page) throws IOException {
		log.entry(page);

		int errorCount = 0;
		HttpQuery query = new HttpQuery();
		query
			.setParameter("t", UnixTime.now())
			.setParameter("stg", "main")
			.setParameter("page", page);
		log.info("loading index page {}", page);
		String htmlContents = HttpProtocol.get(Const.BBS_URL, query, Const.BBS_ENCODE);

		BBSTable bbsTable = new BBSTable(htmlContents);
		errorCount += bbsTable.pageParser();

		for (int index = 0; index < bbsTable.getThreadLength(); index ++) {
			ThreadInformation info = bbsTable.getThreadInformation(index);
			log.debug("thread information: {}", info);
			if (ModelThreadInformation.save(info)) {
				if (info.getImageId() != null) {
					ImageDownloader.saveImage(info);
				}
			}
		}

		log.warn("parse error: {}", errorCount);
		log.trace("end");
	}
}
