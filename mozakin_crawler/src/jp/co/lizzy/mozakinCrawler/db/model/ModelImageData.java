package jp.co.lizzy.mozakinCrawler.db.model;

import jp.co.lizzy.mozakinCrawler.db.SessionManager;
import jp.co.lizzy.mozakinCrawler.db.dao.ImageData;
import jp.co.lizzy.mozakinCrawler.db.mapper.MapperImageData;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModelImageData {
	private static final Logger log = LogManager.getLogger("jp.co.lizzy.mozakinCrawler.db.model");

	private static MapperImageData mapper =
			SessionManager.getSession().getMapper(MapperImageData.class);

	public static void save(ImageData imageData) {
		log.debug("thread id: {}", imageData.getThreadId());
		mapper.insert(imageData);
		SessionManager.commitSession();
	}

	public static boolean isExist(ImageData imageData) {
		log.debug("thread id: {}", imageData.getThreadId());
		return mapper.isExist(imageData);
	}
}
