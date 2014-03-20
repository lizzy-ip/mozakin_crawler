package jp.co.lizzy.common.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HttpProtocol {
	private static final Logger log = LogManager.getLogger(HttpProtocol.class);
	private static final int MAX_RETRY_COUNT = 5;
	private static final long RETRY_WAIT = 5;

	public static HttpResponse get(String url, HttpQuery query) throws HttpException {
		return get(query.appendUrl(url));
	}

	public static HttpResponse get(String urlString) throws HttpException {
		URL url;
		try {
			url = new URL(urlString);
			log.info("get url: {}" , urlString);

			for (int retryCount = 0; retryCount < MAX_RETRY_COUNT; retryCount ++) {
				try {
					return getUrl(url);
				} catch (UnknownHostException ex) {
					log.warn("サーバが見つかりません: {}", ex.getMessage());
				} catch (IOException ex) {
					ex.printStackTrace();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				Thread.sleep(RETRY_WAIT * 1000);
			}
			throw new HttpException("Retry Error.");
		} catch (MalformedURLException ex) {
			log.warn("Illigal Url: {}", ex.getMessage());
			throw new HttpException(ex);
		} catch (InterruptedException ex) {
			log.error(ex);
			throw new HttpException(ex);
		} finally {
			log.trace("end");
		}
	}


	private static HttpResponse getUrl(URL url) throws IOException {
		log.trace("start");

		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");

			if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
				log.error("error");
			}

			HttpResponse response = new HttpResponse(connection);
			response.setHttpContents(getBinary(connection.getInputStream()));

			httpDebugInformation(response.getHeaderField());
			log.debug("Content Length: {}; Actual Size: {}", connection.getContentLength(), response.getLength());
			return response;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
			log.trace("end");
		}
	}

	private static void httpDebugInformation(Map<String, List<String>> headerField) {
		if (!log.isDebugEnabled()) {
			return;
		}

		for (Map.Entry<String, List<String>>  header : headerField.entrySet()) {
			log.debug("header - {}: {};", header.getKey(), header.getValue());
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
}
