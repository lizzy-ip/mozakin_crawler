package jp.co.lizzy.mozakinCrawler.utility;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.co.lizzy.common.http.HttpProtocol;
import jp.co.lizzy.common.http.HttpQuery;
import jp.co.lizzy.mozakinCrawler.Const;
import jp.co.lizzy.mozakinCrawler.db.dao.ImageData;
import jp.co.lizzy.mozakinCrawler.db.model.ModelImageData;
import jp.co.lizzy.mozakinCrawler.entity.IMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ImageDownloader {
	private static final Logger log = LogManager.getLogger("jp.co.lizzy.mozakinCrawler.utility");

	private static final String EXTRACT_POST_FORM_REG_EX = "<FORM.*?action=([^ \"]+)[^>]*?>(.*?)</FORM>";
	private static final String EXTRACT_PARAMETER_REG_EX = "<INPUT[^>]*?name=([^ \">]*)[^>]*value=\"?([^ \">]*)[^>]*>";

	public static void saveImage(IMessage iMessage) throws IOException {
		log.trace("start");
		ImageData imageData = new ImageData(iMessage);

		if (!ModelImageData.isExist(imageData)) {
			log.info("download image - thread id: {}; response id: {}; image id: {};",
					iMessage.getThreadId(),
					iMessage.getResponseId(),
					iMessage.getImageId());
			byte[] rawData = doDownload(
					iMessage.getImageServer(),
					iMessage.getThreadId(),
					iMessage.getImageId());
			imageData.setRawData(rawData)
					.setFileSize(rawData.length);
			ModelImageData.save(imageData);
		} else {
			log.info("Already Saved - thread id: {}; response id: {};"
					, imageData.getThreadId(), imageData.getResponseId());
		}
		log.trace("end");
	}

	public static byte [] doDownload(String server, int threadId, String imageId) throws IOException {
		String htmlContents = getFirstContents(server, threadId, imageId);

		Map<String,String> parameters = parseForm(htmlContents);

		log.debug("picture id: {}", parameters.get("pic"));
		log.debug("picture url: {}", createPictureUrl(server, parameters.get("pic")));
		byte [] image = HttpProtocol.getBinary(createPictureUrl(server, parameters.get("pic")));
		return image;
	}

	private static String getFirstContents(String server, int threadId, String imageId) {
		HttpQuery query = new HttpQuery();
		query
			.setParameter("tno", threadId)
			.setParameter("res", 0)
			.setParameter("pic", imageId);
		return HttpProtocol.get(createUrl(server), query, "utf-8");
	}

	private static String createUrl(String server) {
		StringBuilder sb = new StringBuilder();
		sb.append("http://").append(server).append(Const.IMAGE_PHP);
		return sb.toString();
	}

	private static String createPictureUrl(String server, String pictureId) {
		StringBuilder sb = new StringBuilder();
		sb.append("http://").append(server).append(Const.IMAGE_URL).append(pictureId);
		return sb.toString();
	}

	private static Map<String, String> parseForm(String htmlContents) {
		Map<String,String> parameters = new HashMap<String, String>();

		Pattern extractForm = Pattern.compile(EXTRACT_POST_FORM_REG_EX);
		Pattern extractInput = Pattern.compile(EXTRACT_PARAMETER_REG_EX);

		Matcher formMatch = extractForm.matcher(htmlContents);
		if (formMatch.find()) {
			parameters.put("url", formMatch.group(1));

			Matcher parameterMatch = extractInput.matcher(formMatch.group(2));
			while (parameterMatch.find()) {
				log.debug("input: {}; name: {}; value: {};", parameterMatch.group(0), parameterMatch.group(1), parameterMatch.group(2));
				parameters.put(parameterMatch.group(1), parameterMatch.group(2));
			}
		} else {
			log.warn("no match form.");
		}

		return parameters;
	}
}

