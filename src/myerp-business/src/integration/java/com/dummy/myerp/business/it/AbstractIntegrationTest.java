package com.dummy.myerp.business.it;

import java.lang.ClassLoader;
import java.nio.charset.StandardCharsets;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Statement;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.apache.commons.io.IOUtils;
import org.junit.Rule;
import org.junit.rules.ExternalResource;

public abstract class AbstractIntegrationTest {

	@Inject
	DataSource dataSource;

	@Rule
	public final ExternalResource resource = new ExternalResource() {
		@Override
		protected void before() throws Throwable {
			System.out.println("### refreshing database... ###");

			ClassLoader classLoader = getClass().getClassLoader();

			Connection connection = dataSource.getConnection();
			Statement statement = connection.createStatement();

			try (InputStream stream = classLoader.getResourceAsStream("21_insert_data_demo.sql")) {
				String sqlScript = IOUtils.toString(stream, StandardCharsets.UTF_8);
				String[] querys = sqlScript.split(";");

				statement.execute("TRUNCATE TABLE myerp.journal_comptable CASCADE;");
				statement.execute("TRUNCATE TABLE myerp.compte_comptable CASCADE;");

				for (String query : querys) {
					statement.execute(query);
				}

				System.out.println("### database refreshed ###");
			} catch(Exception e) {
				throw new Exception("An error occured while setting up a test: ", e);
			} finally {
				statement.close();
				connection.close();
			}

		};

		@Override
		protected void after() {
		};
	};

}
