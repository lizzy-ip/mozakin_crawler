package jp.co.lizzy.mozakinCrawler.db.model;

import jp.co.lizzy.mozakinCrawler.db.SessionManager;
import jp.co.lizzy.mozakinCrawler.db.mapper.MapperResponse;
import jp.co.lizzy.mozakinCrawler.entity.BBSMessage;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModelResponse {
	private static final Logger log = LogManager.getLogger(ModelResponse.class);

	private static MapperResponse mapper =
			SessionManager.getSession().getMapper(MapperResponse.class);

	public static boolean save(BBSMessage bbsMessage) {
		try {
			if (!mapper.isExist(bbsMessage)) {
				log.debug("insert new message.");
				mapper.insert(bbsMessage);
				return true;
			} else {
				log.debug("message already exist - thread id: {}; response id: {}.",
						bbsMessage.getThreadId(), bbsMessage.getResponseId());
				return false;
			}
		} catch(PersistenceException ex) {
			log.info(ex.getCause().getMessage());
			SessionManager.rollbackSession();
			return false;
		} finally {

		}
	}

}
