package jp.co.lizzy.mozakinCrawler.entity;

import java.util.Date;

/**
 * スレッド情報クラス
 *
 * @author 佐久間祈
 *
 */
public class ThreadInformation extends BBSMessage {

	/** レスポンス数 */
	private int numberOfResponses = 0;

	/** ページ数 */
	private int numberOfPages = 0;

	/** 最終レスポンスID */
	private int lastResponseId = 0;

	/**
	 * レスポンス数を取得します。
	 * @return レスポンス数
	 */
	public int getNumberOfResponses() {
		return numberOfResponses;
	}

	/**
	 * レスポンス数を設定します。
	 * @param numberOfResponses レスポンス数
	 */
	public ThreadInformation setNumberOfResponses(int numberOfResponses) {
		this.numberOfResponses = numberOfResponses;
		return this;
	}

	/**
	 * ページ数を取得します。
	 * @return ページ数
	 */
	public int getNumberOfPages() {
		return numberOfPages;
	}

	/**
	 * ページ数を設定します。
	 * @param numberOfPages ページ数
	 */
	public ThreadInformation setNumberOfPages(int numberOfPages) {
		this.numberOfPages = numberOfPages;
		return this;
	}

	/**
	 * 最終レスポンスIDを取得します。
	 * @return 最終レスポンスID
	 */
	public int getLastResponseId() {
		return lastResponseId;
	}

	/**
	 * 最終レスポンスIDを設定します。
	 * @param lastResponseId 最終レスポンスID
	 */
	public void setLastResponseId(int lastResponseId) {
		this.lastResponseId = lastResponseId;
	}

	/**
	 * スレッドオーナーを取得します。
	 * @return スレッドオーナー
	 */
	public String getThreadOwner() {
		return getContributorName();
	}

	/**
	 * スレッドオーナーを設定します。
	 * @param threadOwnner スレッドオーナー
	 */
	public ThreadInformation setThreadOwner(String threadOwnner) {
		setContributorName(threadOwnner);
		return this;
	}

	/**
	 * スレッドオーナーIDを取得します。
	 * @return スレッドオーナーID
	 */
	public String getThreadOwnerId() {
		return getContributorId();
	}

	/**
	 * スレッドオーナーIDを設定します。
	 * @param threadOwnnerId スレッドオーナーID
	 */
	public ThreadInformation setThreadOwnerId(String threadOwnnerId) {
		setContributorId(threadOwnnerId);
		return this;
	}

	/**
	 * スレッド作成日を取得します。
	 * @return スレッド作成日
	 */
	public Date getThreadCreateDate() {
		return getContributeDate();
	}

	/**
	 * スレッド作成日を設定します。
	 * @param createDate スレッド作成日
	 */
	public ThreadInformation setThreadCreateDate(Date createDate) {
		setContributeDate(createDate);
		return this;
	}

}
