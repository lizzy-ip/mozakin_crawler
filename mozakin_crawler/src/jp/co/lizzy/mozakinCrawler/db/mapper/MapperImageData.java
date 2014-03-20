package jp.co.lizzy.mozakinCrawler.db.mapper;

import jp.co.lizzy.mozakinCrawler.db.dao.ImageData;

public interface MapperImageData {
	public void insert(ImageData imageData);
	public boolean isExist(ImageData imageData);
}
