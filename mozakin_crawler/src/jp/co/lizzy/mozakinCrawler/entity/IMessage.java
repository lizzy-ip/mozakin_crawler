package jp.co.lizzy.mozakinCrawler.entity;


public interface IMessage {
	/**
	 * スレッドIDを取得します。
	 * @return スレッドID
	 */
	public int getThreadId();

	/**
	 * レスポンスIDを取得します。
	 * @return レスポンスID
	 */
	public int getResponseId();

	/**
	 * メッセージを取得します。
	 * @return メッセージ
	 */
	public String getMessage();

	/**
	 * 動画IDを取得します。
	 * @return 動画ID
	 */
	public String getMovieId();

	/**
	 * 画像サーバを取得します。
	 * @return 画像サーバ
	 */
	public String getImageServer();

	/**
	 * 画像IDを取得します。
	 * @return 画像ID
	 */
	public String getImageId();
}
