package jp.co.lizzy.mozakinCrawler;

import java.io.IOException;
import java.util.List;

import jp.co.lizzy.common.ElapsedTime;
import jp.co.lizzy.mozakinCrawler.db.model.ModelThreadInformation;
import jp.co.lizzy.mozakinCrawler.entity.ThreadInformation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MozakinCrawler {
	private static final Logger log = LogManager.getLogger(MozakinCrawler.class);

	public static void doCrawl() throws IOException {
		ElapsedTime time = new ElapsedTime();

		log.info("Start BBS Table Crawling.");
		//BBSTableCrawler.doCrawl(0, 30);

		List<ThreadInformation> threadInformations =
				ModelThreadInformation.findUpdateThread();
		log.info("{} Update Thread Founded. Each Thread Crawling", threadInformations.size());

		for (ThreadInformation threadInformation : threadInformations) {
			if (BBSThreadCrawler.doCrawl(threadInformation)) {
				ModelThreadInformation.toReaded(threadInformation.getThreadId());
			};
		}
		log.info("End Crawling. Total Elapsed Time: {} sec.",time.get());
	}

}
