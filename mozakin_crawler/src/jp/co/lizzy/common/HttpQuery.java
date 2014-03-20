package jp.co.lizzy.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HttpQuery {
	private final Logger log = LogManager.getLogger(this.getClass());
	Map<String, String> parameters = new HashMap<String, String>();

	public HttpQuery() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public HttpQuery setParameter(String parameterName, String value) {
		parameters.put(parameterName, value);
		return this;
	}

	public HttpQuery setParameter(String parameterName, long value) {
		String stringValue = String.valueOf(value);
		parameters.put(parameterName, stringValue);
		return this;
	}

	public HttpQuery setParameter(String parameterName, int value) {
		String stringValue = String.valueOf(value);
		parameters.put(parameterName, stringValue);
		return this;
	}

	public void setParameter(Map<String, String>value) {
		parameters = value;
	}

	public String appendUrl(String url) {
		log.trace("start");
		StringBuilder urlString = new StringBuilder(url);
		urlString.append("?");
		urlString.append(getQueryString());
		log.trace("end");
		return urlString.toString();
	}

	public String getQueryString() {
		log.trace("start");
		Set<String> parameterSet = parameters.keySet();

		StringBuilder queryString = new StringBuilder();

		for(Iterator<String> i = parameterSet.iterator(); i.hasNext();) {
			String parameterName = i.next();
			String parameterValue = parameters.get(parameterName);
			log.debug("parameter name: {}; parameter value: {};", parameterName, parameterValue);
			queryString.append(parameterName).append("=").append(parameterValue).append("&");
		}
		queryString.setLength(queryString.length() - 1);
		log.debug("query string: {}", queryString.toString());
		log.trace("end");
		return queryString.toString();
	}
}
