package jp.co.lizzy.mozakinCrawler.entity;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BBSMessage implements IMessage{
	private static String ImageUrlRegEx = "http://([^/]+?)/imx_imagelink.php?.*?pic=([^&]+)";

	/** スレッドID */
	private int threadId;

	/** レスポンスID */
	private int responseId = 0;

	/** 投稿者名 */
	private String contributorName;

	/** 投稿者ID */
	private String contributorId;

	/** 投稿日時 */
	private Date contributeDate;

	/** メッセージ */
	private String message;

	/** メッセージ色 */
	private String messageColor;

	/** 画像URL */
	private String imageUrl;

	/** 動画ID */
	private String movieId;

	/** 画像サーバ */
	private String imageServer;

	/** 画像ID */
	private String imageId;


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
	public BBSMessage setThreadId(int threadId) {
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
	public BBSMessage setResponseId(int responseId) {
		this.responseId = responseId;
		return this;
		}

	/**
	 * 投稿者名を取得します。
	 * @return 投稿者名
	 */
	public String getContributorName() {
		return contributorName;
	}

	/**
	 * 投稿者名を設定します。
	 * @param contributorName 投稿者名
	 */
	public BBSMessage setContributorName(String contributorName) {
		this.contributorName = contributorName;
		return this;
	}

	/**
	 * 投稿者IDを取得します。
	 * @return 投稿者ID
	 */
	public String getContributorId() {
		return contributorId;
	}

	/**
	 * 投稿者IDを設定します。
	 * @param contributorId 投稿者ID
	 */
	public BBSMessage setContributorId(String contributorId) {
		this.contributorId = contributorId;
		return this;
	}

	/**
	 * 投稿日時を取得します。
	 * @return 投稿日時
	 */
	public Date getContributeDate() {
		return contributeDate;
	}

	/**
	 * 投稿日時を設定します。
	 * @param contributeDate 投稿日時
	 */
	public BBSMessage setContributeDate(Date contributeDate) {
		this.contributeDate = contributeDate;
		return this;
	}

	/**
	 * メッセージを取得します。
	 * @return メッセージ
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * メッセージを設定します。
	 * @param message メッセージ
	 */
	public BBSMessage setMessage(String message) {
		this.message = message;
		return this;
	}

	/**
	 * メッセージ色を取得します。
	 * @return メッセージ色
	 */
	public String getMessageColor() {
		return messageColor;
	}

	/**
	 * メッセージ色を設定します。
	 * @param messageColor メッセージ色
	 */
	public BBSMessage setMessageColor(String messageColor) {
		this.messageColor = messageColor;
		return this;
	}

	/**
	 * 画像URLを取得します。
	 * @return 画像URL
	 */
	public String getImageUrl() {
		return imageUrl;
	}

	/**
	 * 画像URLを設定します。
	 * @param imageUrl 画像URL
	 */
	public BBSMessage setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
		imageUrlParser();
		return this;
	}

	/**
	 * 動画URLを取得します。
	 * @return 動画URL
	 */
	public String getMovieId() {
		return movieId;
	}

	/**
	 * 動画URLを設定します。
	 * @param movieUrl 動画URL
	 */
	public BBSMessage setMovieId(String movieId) {
		this.movieId = movieId;
		return this;
	}

	private void imageUrlParser() {
		if (this.imageUrl == null) {
			return;
		}
		Pattern urlPatternRegEx = Pattern.compile(ImageUrlRegEx, Pattern.DOTALL);
		Matcher match = urlPatternRegEx.matcher(getImageUrl());
		if (match.find()) {
			this.setImageId(match.group(2));
			this.setImageServer(match.group(1));
		}
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
	public BBSMessage setImageServer(String imageServer) {
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
	public BBSMessage setImageId(String imageId) {
		this.imageId = imageId;
		return this;
	}

}