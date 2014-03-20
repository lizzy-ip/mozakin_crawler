package jp.co.lizzy.mozakinCrawlerTest;

import jp.co.lizzy.common.UnixTime;
import jp.co.lizzy.common.http.HttpProtocol;
import jp.co.lizzy.common.http.HttpQuery;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class HttpProtocolTest {

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
	public void testGet() {
		HttpQuery query = new HttpQuery();
		query
			.setParameter("t", UnixTime.now())
			.setParameter("stg", 43344)
			.setParameter("page", 1);
		System.out.println(HttpProtocol.get("http://bbs2.mozakin.com/bbs.php", query));
		System.out.println(HttpProtocol.get("http://www.maruwasoft.co.jp", "hhhhhutf-8"));
		System.out.println(HttpProtocol.get("http://bbs2.moakin.com/bbs.php", query));
	}

}
