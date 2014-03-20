package jp.co.lizzy.mozakinCrawler.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlRegEx {
	private static final String FONT_COLOR_TAG = "<font color=[^>]+?>";
	private static final String DATE_FORMAT = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}";
	private static final String RESPONSE_ID = "\\[&nbsp;<b>(\\d+)-(\\d+)</b>&nbsp;\\]&nbsp;&nbsp;";

	private static final String EXTRACT_THREAD = "<table.*?class=thread[^>]*>(.*?)<td style='padding-bottom:[^>]*>";
	private static final String THREAD_INFORMATION = "\\[&nbsp;<b>(\\d+)</b>&nbsp;\\]&nbsp;&nbsp;"
			+ ".*?(<a href=\".+?\"[^>]*?>)?" + FONT_COLOR_TAG + "(.*?)</font>(</a>)?" + FONT_COLOR_TAG + "&nbsp;\\[(.+?)\\]</font>"
			+ ".*?" + FONT_COLOR_TAG + "(" + DATE_FORMAT + ")</font>&nbsp;"
			+ ".*?<a href=\"(.*?)\" target=_blank>"
			+ ".*?<td valign=top width=100%><font color=([0-9A-Fa-f]+)>(.*?)</font></TD><TR></TABLE>"
			+ ".*?" + FONT_COLOR_TAG + "レス数(\\d+)件&nbsp;(\\d+)ページ&nbsp;";

	private static final String BLANK_THREAD_INFORMATION = "\\[&nbsp;<b>(\\d+)</b>&nbsp;\\]&nbsp;&nbsp;"
			+ ".*?" + FONT_COLOR_TAG + "(" + DATE_FORMAT + ")</font>&nbsp;";

	private static final String EXTRACT_RESPONSE = "<table.*?class=reply[^>]*>(.*?<font color=#000000>削除</font></A></TD></TR></TABLE></TD></TR></table></td></tr>)</table>";

	private static final String RESPONSE_INFORMATION = "\\[&nbsp;<b>(\\d+)-(\\d+)</b>&nbsp;\\]&nbsp;&nbsp;"
			+ ".*?(<a href=\".+?\"[^>]*?>)?" + FONT_COLOR_TAG + "(.*?)</font>(</a>)?&nbsp;\\[(.+?)\\]</font>"
			+ ".*?" + FONT_COLOR_TAG + "(" + DATE_FORMAT + ")</font>&nbsp;"
			+ ".*?<td.*?width=100%><font color=([0-9A-Fa-f]+)>(.*?)</font></TD></TR></TABLE>";

	private static final String EXTRACT_IMAGE_URL = "<td valign=top align=center>"
			+ "<a href=\"(http://[^\"]+)\" target=_blank>"
			+ "<IMG src=\"http://[^\"]+\".*?></a></td>";

	private static final String EXTRACT_MOVIE_ID = "flvx\\(\\d+,\\d+,'([^']+)','[^']+','\\d+\\.\\d+\\.\\d+\\.\\d+'\\);";

	public static Matcher extractThread(String input) {
		return Pattern.compile(EXTRACT_THREAD, Pattern.DOTALL).matcher(input);
	}

	public static Matcher threadInformation(String input) {
		return Pattern.compile(THREAD_INFORMATION, Pattern.DOTALL).matcher(input);
	}

	public static Matcher blankThreadInformation(String input) {
		return Pattern.compile(BLANK_THREAD_INFORMATION, Pattern.DOTALL).matcher(input);
	}

	public static Matcher responseId(String input) {
		return Pattern.compile(RESPONSE_ID).matcher(input);
	}

	public static Matcher extractResponse(String input) {
		return Pattern.compile(EXTRACT_RESPONSE, Pattern.DOTALL | Pattern.CASE_INSENSITIVE).matcher(input);
	}

	public static Matcher responseInformation(String input) {
		return Pattern.compile(RESPONSE_INFORMATION).matcher(input);
	}

	public static Matcher extractImageUrl(String input) {
		return Pattern.compile(EXTRACT_IMAGE_URL).matcher(input);
	}

	public static Matcher extractMovieId(String input) {
		return Pattern.compile(EXTRACT_MOVIE_ID).matcher(input);
	}
}
