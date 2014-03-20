package jp.co.lizzy.mozakinCrawler.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;

import jp.co.lizzy.mozakinCrawler.entity.BBSMessage;
import jp.co.lizzy.mozakinCrawler.entity.ThreadInformation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HtmlParser {
	private static final Logger log = LogManager.getLogger("jp.co.lizzy.mozakinCrowler");

	private static final String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";
	private static SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

	public static ThreadInformation parseThread(String threadText) {
		log.trace("start");

		ThreadInformation information = null;

		Matcher match = HtmlRegEx.threadInformation(threadText);
		try {
			if (match.find()) {
				information = threadFactory(match);
				information.setLastResponseId(lastResponseId(threadText));
			} else {
				information = blankThread(threadText);
			}

			return information;
		} catch (NumberFormatException ex) {
			log.warn("Number Format Error.");
			return null;
		} catch (ParseException e) {
			log.warn("Date Format Error.");
			return null;
		} finally {
			if (log.isDebugEnabled() && information != null) {
				log.debug("Thread Id: {}; Ownner: {}; Ownner Id: {}; Thread Create Date: {};",
						information.getThreadId(), information.getThreadOwner(), information.getThreadOwnerId(),
						information.getThreadCreateDate());
				log.debug("number of response: {}; number of pages: {}; last Response Id: {};",
						information.getNumberOfResponses(), information.getNumberOfPages(), information.getLastResponseId());
				log.debug("Message Color: {}; Message: {};",
						information.getMessageColor(), information.getMessage());
				log.debug("Image Id: {}; Image Server {};",
						information.getImageId(), information.getImageServer());
			}

			log.trace("end");
		}
	}

	private static ThreadInformation threadFactory(Matcher match) throws NumberFormatException, ParseException {
		ThreadInformation information = new ThreadInformation();
		information
			.setThreadId(Integer.parseInt(match.group(1)));
		information
			.setThreadOwner(match.group(3))
			.setThreadOwnerId(match.group(5))
			.setThreadCreateDate(sdf.parse(match.group(6)))
			.setNumberOfResponses(Integer.parseInt(match.group(10)))
			.setNumberOfPages(Integer.parseInt(match.group(11)))
			.setImageUrl(match.group(7))
			.setMessageColor(match.group(8))
			.setMessage(match.group(9));
		return information;
	}

	private static ThreadInformation blankThread(String threadText) throws NumberFormatException, ParseException {
		ThreadInformation information = new ThreadInformation();

		Matcher match = HtmlRegEx.blankThreadInformation(threadText);
		if (match.find()) {
			information
			.setThreadId(Integer.parseInt(match.group(1)));
			information
				.setThreadOwner("blank")
				.setThreadOwnerId("0000")
				.setThreadCreateDate(sdf.parse(match.group(2)))
				.setMessageColor("000000")
				.setMessage(threadText);
		} else {
			log.warn(threadText);
			log.warn("No Match Contents.");
			return null;
		}

		return information;
	}

	private static int lastResponseId(String threadText) {
		log.trace("start");

		int responseId = 0;
		Matcher match = HtmlRegEx.responseId(threadText);

		while(match.find()) {
			responseId = Integer.parseInt(match.group(2));
		}

		return log.exit(responseId);
	}

	public static ArrayList<BBSMessage> parseResponses(String responseText) {
		log.trace("start");
		ArrayList<BBSMessage> responseList = new ArrayList<BBSMessage>();

		Matcher match = HtmlRegEx.extractResponse(responseText);

		while(match.find()) {
			try {
				BBSMessage bbsMessage = parseSingleResponse(match.group(1));
				responseList.add(bbsMessage);
			} catch (NumberFormatException ex) {
				log.warn("Number Format Error.");
				return null;
			} catch (ParseException e) {
				log.warn("Date Format Error.");
				return null;
			}
		}

		log.trace("end");
		return responseList;
	}

	private static BBSMessage parseSingleResponse(String responseLine) throws NumberFormatException, ParseException {
		BBSMessage response = new BBSMessage();

		Matcher match = HtmlRegEx.responseInformation(responseLine);

		if (match.find()) {
			response.setThreadId(Integer.parseInt(match.group(1)))
					.setResponseId(Integer.parseInt(match.group(2)))
					.setContributorName(match.group(4))
					.setContributorId(match.group(6))
					.setContributeDate(sdf.parse(match.group(7)))
					.setMessageColor(match.group(8))
					.setMessage(match.group(9));
			Matcher imageUrlMatch = HtmlRegEx.extractImageUrl(responseLine);
			if (imageUrlMatch.find()) {
				response.setImageUrl(imageUrlMatch.group(1));
			}
			Matcher movieIdMatch = HtmlRegEx.extractMovieId(responseLine);
			if (movieIdMatch.find()) {
				response.setMovieId(movieIdMatch.group(1));
			}

			log.debug("threadId: {}; responseId: {};",
					response.getThreadId(), response.getResponseId());
			log.debug("contributorName: {}; contributorId: {}; contributeDate: {};",
					response.getContributorName(), response.getContributorId(), response.getContributeDate());
			log.debug("image server: {}; image id: {}; movie id: {};",
					response.getImageServer(), response.getImageId(), response.getMovieId());
			log.debug("messageColor: {}; message: {};",
					response.getMessageColor(), response.getMessage());

			return response;
		} else {
			return null;
		}
	}
}
