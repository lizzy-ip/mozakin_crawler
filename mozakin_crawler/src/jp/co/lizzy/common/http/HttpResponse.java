package jp.co.lizzy.common.http;

import java.net.HttpURLConnection;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HttpResponse {
	/** 規定のエンコーディング */
	private static final String DEFAULT_ENCODING_CHARSET = "Shift-JIS";
	private static final Logger log = LogManager.getLogger(HttpResponse.class);

	/** httpのコンテンツ */
	private byte [] httpContents;

	/** httpのヘッダ情報 */
	private Map<String, List<String>> headerField;

	/** htmlのエンコーディング情報 */
	private String encoding;

	/** htmlのコンテンツ情報 */
	private String contentType;

	public HttpResponse() {

	}

	public HttpResponse(HttpURLConnection connection) {
		this.setHeaderField(connection.getHeaderFields());
		this.setEncoding(connection.getContentEncoding());
		this.setContentType(connection.getContentType());
	}

	/**
	 * httpのコンテンツを取得します。
	 * @return httpのコンテンツ情報
	 */
	public byte[] getHttpContents() {
		return httpContents;
	}

	/**
	 * httpのコンテンツを設定します。
	 * @param httpContents httpのコンテンツ情報
	 */
	public void setHttpContents(byte[] httpContents) {
		this.httpContents = httpContents;
	}

	/**
	 * htmlのエンコーディング情報を取得します。
	 * @return htmlのエンコーディング情報
	 */
	public String getEncoding() {
		return encoding;
	}

	/**
	 * htmlのエンコーディング情報を設定します。
	 * @param encoding htmlのエンコーディング情報
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * httpのヘッダ情報を取得します。
	 * @return htmlのヘッダ情報
	 */
	public Map<String,List<String>> getHeaderField() {
		return headerField;
	}

	/**
	 * httpのヘッダ情報を設定します。
	 * @param headerField htmlのヘッダ情報
	 */
	public void setHeaderField(Map<String,List<String>> headerField) {
		this.headerField = headerField;
	}

	/**
	 * htmlのコンテンツ情報を取得します。
	 * @return htmlのコンテンツ情報
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * htmlのコンテンツ情報を設定します。
	 * @param contentType htmlのコンテンツ情報
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * httpContentsの長さを取得します。
	 * @return
	 */
	public int getLength() {
		return this.httpContents.length;
	}

	/**
	 * 文字列にして返却します。
	 * @return
	 */
	public String toHtml() {
		String encoding;
		encoding = getEncodingFromHttpContent();
		encoding = (encoding == null) ? this.encoding : encoding;
		encoding = (encoding == null) ? DEFAULT_ENCODING_CHARSET : encoding;

		return toHtmlConvert(httpContents, encoding);
	}

	public String toHtml(String forceEncoding) {
		return toHtmlConvert(httpContents, forceEncoding);
	}


	/**
	 * httpContentsの中からエンコード情報を取得する。
	 * @return エンコード文字列
	 */
	private String getEncodingFromHttpContent() {
		log.trace("start");
		try {
			String assertString = new String(httpContents, Charset.forName(DEFAULT_ENCODING_CHARSET));

			Matcher matcher = Pattern.compile("<meta.*?content ?=.*?charset=([^\" ;]+)", Pattern.CASE_INSENSITIVE).matcher(assertString);
			if (matcher.find()) {
				log.debug("Charset Find in Html: {}.", matcher.group(1));
				return matcher.group(1);
			} else {
				return null;
			}
		} finally {
			log.trace("end");
		}
	}

	/**
	 * 指定されたエンコードにて、Httpをエンコードする。
	 * @param charset エンコードキャラクターセット
	 * @return
	 */
	private static String toHtmlConvert(byte [] httpContents, String encodingCharset) {
		Charset objCharset;

		try {
			objCharset = Charset.forName(encodingCharset);
		} catch (UnsupportedCharsetException ex) {
			log.warn("Unsupported CharsetName - '{}', Use Default Encoding.", ex.getMessage());
			objCharset = Charset.forName(DEFAULT_ENCODING_CHARSET);
		} catch (IllegalCharsetNameException ex) {
			log.warn("Illigal CharsetName - '{}', Use Default Encoding.", ex.getMessage());
			objCharset = Charset.forName(DEFAULT_ENCODING_CHARSET);
		} catch (IllegalArgumentException ex) {
			log.warn("encodingCharset = null.");
			objCharset = Charset.forName(DEFAULT_ENCODING_CHARSET);
		}
		return new String(httpContents, objCharset);
	}
}
