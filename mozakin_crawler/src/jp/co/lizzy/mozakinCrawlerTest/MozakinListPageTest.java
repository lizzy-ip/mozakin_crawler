package jp.co.lizzy.mozakinCrawlerTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MozakinListPageTest {
	private static final String TEST_FILE = "C:\\eclipse\\workspace_java\\mozakin_crawler\\sample\\bbs.html";
	private static final String THREAD_HTML = "C:\\eclipse\\workspace_java\\mozakin_crawler\\sample\\thread.html";

	public static String fileToString(String filename) throws IOException {
		File file = new File(filename);
		BufferedReader br = null;
		try {
			int charactor;
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "Shift-JIS"));
			StringBuffer sb = new StringBuffer();
			while ((charactor = br.read()) != -1) {
				sb.append((char) charactor);
			}
			return sb.toString();
		} finally {
			br.close();
		}
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testPageParser() throws Exception {
		//ListPageCrawler.doCrawl(0, 10);
	}

	@Test
	public void testResponseParser() throws Exception {
//		String responseText = fileToString(THREAD_HTML);
//		HtmlParser.parseResponses(responseText);
	}

	@Test
	public void test() throws UnsupportedEncodingException, IOException {
//		Map<String, byte[]> map = new HashMap<String, byte[]>();
//		byte [] image = ImageDownloader.doDownload("img02.mozakin.com", 43164, "pjpeg_043164");
//		System.out.println(image.length);
//
//		map.put("raw_data", image);
//		sessionManager.getSession().insert("jp.co.lizzy.mozakinCrawler.db.mapper.MapperImage.insert", map);
//		sessionManager.getSession().commit();
	}
}