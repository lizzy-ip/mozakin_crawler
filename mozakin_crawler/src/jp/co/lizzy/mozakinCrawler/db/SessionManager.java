package jp.co.lizzy.mozakinCrawler.db;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class SessionManager {
	private static final String DATABASE_CONFIG = "jp/co/lizzy/mozakinCrawler/db/xml/database.xml";
	private static SessionManager instance = new SessionManager();
	private SqlSession session = null;

	private SessionManager() {
		Reader reader;
		try {
			reader = Resources.getResourceAsReader(DATABASE_CONFIG);
			session = new SqlSessionFactoryBuilder().build(reader).openSession();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static SqlSession getSession() {
		return instance.session;
	}

	public static void commitSession() {
		instance.session.commit();
	}

	public static void rollbackSession() {
		instance.session.rollback();
	}
}
