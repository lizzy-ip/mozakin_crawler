package jp.co.lizzy.mozakinCrawler.entity;

import java.util.ArrayList;

public class BBSThread {
	/** スレッド情報 */
	private ThreadInformation threadInformation;

	/** レスポンス */
	private final ArrayList<BBSMessage> responses = new ArrayList<BBSMessage>();

	/**
	 * スレッド情報を取得します。
	 * @return スレッド情報
	 */
	public ThreadInformation getThreadInformation() {
		return threadInformation;
	}

	/**
	 * スレッド情報を設定します。
	 * @param threadInformation スレッド情報
	 */
	public void setThreadInformation(ThreadInformation threadInformation) {
		this.threadInformation = threadInformation;
	}

	/**
	 * レスポンスを取得します。
	 * @return レスポンス
	 */
	public BBSMessage getResponses(int index) {
		return responses.get(index);
	}

	/**
	 * レスポンスを設定します。
	 * @param responses レスポンス
	 */
	public void setResponses(BBSMessage response) {
		this.responses.add(response);
	}

	/**
	 * レスポンスを追加します。
	 * @param responses
	 */
	public void addResponses(ArrayList<BBSMessage> responses) {
		this.responses.addAll(responses);
	}

	public int getResponseSize() {
		return this.responses.size();
	}
}
