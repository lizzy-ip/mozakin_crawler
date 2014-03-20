package jp.co.lizzy.common;

import java.util.Date;

public class UnixTime {

	public static long now() {
		Date date = new Date();
		return date.getTime() / 1000;
	}
}
