package jp.co.lizzy.mozakinCrawler.db.model;

import java.util.List;

import jp.co.lizzy.mozakinCrawler.db.SessionManager;
import jp.co.lizzy.mozakinCrawler.db.mapper.MapperThreadInformation;
import jp.co.lizzy.mozakinCrawler.entity.ThreadInformation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModelThreadInformation {
	private static final Logger log = LogManager.getLogger(ModelThreadInformation.class);

	private static MapperThreadInformation mapper =
			SessionManager.getSession().getMapper(MapperThreadInformation.class);

	public static List<ThreadInformation> findUpdateThread() {
		log.trace("start");
		List<ThreadInformation> threadInformations =
				mapper.findUpdateThread();
		log.trace("end");
		return threadInformations;
	}

	public static boolean save(ThreadInformation threadInformation) {
		log.trace("start");

		try {
			log.debug("thread id: {}", threadInformation.getThreadId());
			ThreadInformation result =
					mapper.findByThreadId(threadInformation.getThreadId());

			if (result == null) {
				log.debug("insert thread information.");
				mapper.insert(threadInformation);
				return true;
			} else if (threadInformation.getNumberOfPages() > result.getNumberOfPages()
					|| threadInformation.getNumberOfResponses() > threadInformation.getNumberOfResponses()
					|| threadInformation.getLastResponseId() > result.getLastResponseId()
				) {
				log.debug("update thread information.");
				mapper.update(threadInformation);
				return false;
			} else {
				log.debug("No Update data");
				return false;
			}
		} finally {
			SessionManager.commitSession();
			log.trace("end");
		}
	}

	public static void toReaded(int threadId) {
		mapper.toReaded(threadId);
	}
}
