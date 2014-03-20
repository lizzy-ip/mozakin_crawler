package jp.co.lizzy.mozakinCrawler.db.dao;

import jp.co.lizzy.mozakinCrawler.entity.IMessage;

public class ImageData {

	/** スレッドID */
	private int threadId;

	/** レスポンスID */
	private int responseId;

	/** 画像サーバ */
	private String imageServer;

	/** 画像ID */
	private String imageId;

	/** ファイルサイズ */
	private int fileSize;

	/** 画像データ */
	private byte[] rawData;

	public ImageData(IMessage iMessage) {
		setThreadId(iMessage.getThreadId());
		setResponseId(iMessage.getResponseId());
		setImageServer(iMessage.getImageServer());
		setImageId(iMessage.getImageId());
	}

	/**
	 * スレッドIDを取得します。
	 * @return スレッドID
	 */
	public int getThreadId() {
		return threadId;
	}

	/**
	 * スレッドIDを設定します。
	 * @param threadId スレッドID
	 */
	public ImageData setThreadId(int threadId) {
		this.threadId = threadId;
		return this;
	}

	/**
	 * レスポンスIDを取得します。
	 * @return レスポンスID
	 */
	public int getResponseId() {
		return responseId;
	}

	/**
	 * レスポンスIDを設定します。
	 * @param responseId レスポンスID
	 */
	public ImageData setResponseId(int responseId) {
		this.responseId = responseId;
		return this;
	}

	/**
	 * 画像サーバを取得します。
	 * @return 画像サーバ
	 */
	public String getImageServer() {
		return imageServer;
	}

	/**
	 * 画像サーバを設定します。
	 * @param imageServer 画像サーバ
	 */
	public ImageData setImageServer(String imageServer) {
		this.imageServer = imageServer;
		return this;
	}

	/**
	 * 画像IDを取得します。
	 * @return 画像ID
	 */
	public String getImageId() {
		return imageId;
	}

	/**
	 * 画像IDを設定します。
	 * @param imageId 画像ID
	 */
	public ImageData setImageId(String imageId) {
		this.imageId = imageId;
		return this;
	}

	/**
	 * ファイルサイズを取得します。
	 * @return ファイルサイズ
	 */
	public int getFileSize() {
		return fileSize;
	}

	/**
	 * ファイルサイズを設定します。
	 * @param fileSize ファイルサイズ
	 */
	public ImageData setFileSize(int fileSize) {
		this.fileSize = fileSize;
		return this;
	}

	/**
	 * 画像データを取得します。
	 * @return 画像データ
	 */
	public byte[] getRawData() {
		return rawData;
	}

	/**
	 * 画像データを設定します。
	 * @param rawData 画像データ
	 */
	public ImageData setRawData(byte[] rawData) {
		this.rawData = rawData;
		return this;
	}
}
