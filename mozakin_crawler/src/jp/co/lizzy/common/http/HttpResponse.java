package jp.co.lizzy.common.http;

public class HttpResponse {
	/** httpのコンテンツ情報 */
	private byte [] httpContents;

	/** htmlのエンコーディング情報 */
	private String encoding;

	public byte [] getHttpContents() {
		return httpContents;
	}

	public void setHttpContents(byte [] httpContents) {
		this.httpContents = httpContents;
	}

	public String contentsToString() {
		return null;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public int getLength() {
		return httpContents.length;
	}
}
