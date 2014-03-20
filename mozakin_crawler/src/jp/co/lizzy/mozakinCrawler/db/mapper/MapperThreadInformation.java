package jp.co.lizzy.mozakinCrawler.db.mapper;

import java.util.List;

import jp.co.lizzy.mozakinCrawler.entity.ThreadInformation;

/**
 * ThreadInformationテーブルのマッパーインターフェース
 * @author Inori
 *
 */
public interface MapperThreadInformation {
	/**
	 * スレッドIDで示されるレコードの存在確認をします。
	 * @param threadId スレッドID
	 * @return 存在する場合trueを存在しない場合はfalseを返却します。
	 */
	public boolean existThreadId(int threadId);

	/**
	 * スレッドIDより、スレッド情報を取得します。
	 * @param threadId スレッドID
	 * @return スレッド情報
	 */
	public ThreadInformation findByThreadId(int threadId);

	/**
	 * スレッド情報を保存します。
	 * @param threadInformation
	 */
	public void insert(ThreadInformation threadInformation);

	/**
	 * スレッド情報を更新します。
	 * @param threadInformation
	 */
	public void update(ThreadInformation threadInformation);

	/**
	 * スレッドを既読にします。
	 * @param threadId スレッドID
	 */
	public void toReaded(int threadId);

	/**
	 * 更新未読のスレッドを取得します。
	 * @return
	 */
	public List<ThreadInformation> findUpdateThread();
}
