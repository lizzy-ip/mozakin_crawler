package jp.co.lizzy.mozakinCrawlerTest;

import java.io.IOException;

import jp.co.lizzy.mozakinCrawler.BBSThreadCrawler;
import jp.co.lizzy.mozakinCrawler.entity.ThreadInformation;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BBSThreadCrawlerTest {

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
	public void testDoCrawl() throws IOException {
		try {
			ThreadInformation threadInformation = new ThreadInformation();
			threadInformation.setThreadId(38845);
			threadInformation.setNumberOfPages(40);

			BBSThreadCrawler.doCrawl(threadInformation);
		} catch (IOException ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

}
