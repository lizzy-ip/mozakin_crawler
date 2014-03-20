package jp.co.lizzy.common.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HttpProtocol {
	private static final String DEFAULT_ENCODING = "Shift-JIS";
	private static final Logger log = LogManager.getLogger(HttpProtocol.class);

	public static String get(String url) {
		return getMain(url, null);
	}

	public static String get(String url, String encoding) {
		return getMain(url, encoding);
	}

	public static String get(String url, HttpQuery query) {
		return getMain(query.appendUrl(url), null);
	}


	public static String get(String url, HttpQuery query, String encoding) {
		return getMain(query.appendUrl(url), encoding);
	}

	private static String getMain(String urlString, String forceEncoding) {
		try {
			return getHtml(urlString, forceEncoding);
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return null;
		}
	}


	private static String getHtml(String urlString, String forceEncoding) throws IOException {
		log.trace("start");
		log.info("get url: {}" , urlString);

		HttpResponse response = new HttpResponse();

		URL url = new URL(urlString);

		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");

			if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
				log.error("error");
			}

			httpHeader(connection);
			String encoding = getEncoding(connection, forceEncoding);
			response.setHttpContents(getBinary(connection.getInputStream()));

			log.info("Content Length: {}; Actual Size: {}", connection.getContentLength(), response.getLength());

			return toString(response.getHttpContents(), encoding);
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
			log.trace("end");
		}
	}

	private static void httpHeader(HttpURLConnection connection) {
		if (!log.isDebugEnabled()) {
			return;
		}
		Map<String, List<String>> headerField = connection.getHeaderFields();
		for (Map.Entry<String, List<String>>  header : headerField.entrySet()) {
			log.debug("header - {}: {};", header.getKey(), header.getValue());

		}
	}

	private static String getEncoding(HttpURLConnection connection, String forceEncoding) {
		String encoding;

		if (forceEncoding == null) {
			encoding = connection.getContentEncoding();
			if (encoding == null) {
				encoding = DEFAULT_ENCODING;
				log.debug("Encoding is none. Use Default Encoding: {}.", encoding);
			} else {
				log.debug("Use Contents Encoding: {}.", encoding);
			}
		} else {
			encoding = forceEncoding;
			log.debug("Use Force Encoding: {}", encoding);
		}
		return encoding;
	}

	/**
	 * エンコードに関するエラーはここで回復される。
	 * @param htmlContents
	 * @param encoding
	 * @return
	 */
	private static String toString(byte [] htmlContents, String encoding) {
		String assertString = null;
		try {
			assertString = new String(htmlContents, Charset.forName(DEFAULT_ENCODING));
			Pattern charset = Pattern.compile("charset=([^\" ]+)");

			Matcher matcher = charset.matcher(assertString);
			if (matcher.find()) {
				String htmlEncoding = matcher.group(1);
				log.debug("html charset: {}.", htmlEncoding);
				return new String(htmlContents, Charset.forName(htmlEncoding));
			} else {
				return new String(htmlContents, Charset.forName(encoding));
			}
		} catch (UnsupportedCharsetException ex) {
			log.warn("Unsupported CharsetName - '{}'", ex.getMessage());
			return assertString;
		} catch (IllegalCharsetNameException ex) {
			log.warn("Illigal CharsetName.");
			return assertString;
		}
	}

	private static byte[] getBinary(InputStream inputStream) throws IOException {
		ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
		try {
			byte[] buf = new byte[1024];
			int len = 0;

			while ((len = inputStream.read(buf)) > 0) {
				byteOutputStream.write(buf, 0, len);
			}

			byteOutputStream.flush();
			return byteOutputStream.toByteArray();
		} finally {
			byteOutputStream.close();
			inputStream.close();
		}
	}

	public static byte[] getBinary(String urlString) throws IOException {
		URL url = new URL(urlString);
		InputStream in = url.openStream();
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {
			byte[] buf = new byte[1024];
			int len = 0;

			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}

			out.flush();
			return out.toByteArray();
		} finally {
			out.close();
			in.close();
		}
	}
}
