package jp.co.lizzy.mozakinCrawler.db.mapper;

import jp.co.lizzy.mozakinCrawler.entity.BBSMessage;

public interface MapperResponse {
	/**
	 * データを保存する
	 * @param bbsMessage
	 */
	public void insert(BBSMessage bbsMessage);

	public boolean isExist(BBSMessage criteria);
}
