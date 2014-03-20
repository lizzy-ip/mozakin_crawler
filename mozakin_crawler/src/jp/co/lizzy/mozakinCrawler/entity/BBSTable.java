package jp.co.lizzy.mozakinCrawler.entity;

import java.util.ArrayList;
import java.util.regex.Matcher;

import jp.co.lizzy.mozakinCrawler.utility.HtmlParser;
import jp.co.lizzy.mozakinCrawler.utility.HtmlRegEx;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BBSTable {
	private final Logger log = LogManager.getLogger(this.getClass());

	private final ArrayList<ThreadInformation> threadInformations = new ArrayList<ThreadInformation>();
	private final String pageContents;

	public BBSTable(String pageContents) {
		this.pageContents = pageContents;
	}

	public int pageParser() {
		log.trace("start");
		int errorCount = 0;

		Matcher match = HtmlRegEx.extractThread(this.pageContents);
		while (match.find()) {
			ThreadInformation threadInformation = HtmlParser.parseThread(match.group(1));
			if (threadInformation != null) {
				threadInformations.add(threadInformation);
			} else {
				errorCount ++;
			}
		}
		log.trace("end");
		return errorCount;
	}

	public ThreadInformation getThreadInformation(int index) {
		return threadInformations.get(index);
	}

	public int getThreadLength() {
		return threadInformations.size();
	}
}
