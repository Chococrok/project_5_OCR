package com.dummy.myerp.consumer.db.helper;

import org.junit.Test;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Assert;
import static org.mockito.Mockito.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.sql.Date;

public class ResultSetHelperTest {

	/* 
	 * 
	 * Test on Integer
	 * 
	*/
	@Test
	public void getIntegerReturnIntegerTest() {
		ResultSet mockedResultSet = mock(ResultSet.class);
		String colName = "Integer";

		try {
			when(mockedResultSet.getInt(colName)).thenReturn(1);

			Assert.assertTrue(ResultSetHelper.getInteger(mockedResultSet, colName) instanceof Integer);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void getIntegerReturnExpectedValueTest() {
		ResultSet mockedResultSet = mock(ResultSet.class);
		String colName = "Integer";

		try {
			when(mockedResultSet.getInt(colName)).thenReturn(1);

			Assert.assertTrue(ResultSetHelper.getInteger(mockedResultSet, colName).intValue() == 1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void getIntegerReturnNullValueTest() {
		ResultSet mockedResultSet = mock(ResultSet.class);
		String colName = "Integer";

		try {
			when(mockedResultSet.getInt(colName)).thenReturn(0);
			when(mockedResultSet.wasNull()).thenReturn(true);
			
			Assert.assertNull(ResultSetHelper.getInteger(mockedResultSet, colName));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/* 
	 * 
	 * Test on Long
	 * 
	*/
	@Test
	public void getLongReturnLongTest() {
		ResultSet mockedResultSet = mock(ResultSet.class);
		String colName = "Long";

		try {
			when(mockedResultSet.getLong(colName)).thenReturn(1l);

			Assert.assertTrue(ResultSetHelper.getLong(mockedResultSet, colName) instanceof Long);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void getLongReturnExpectedValueTest() {
		ResultSet mockedResultSet = mock(ResultSet.class);
		String colName = "Long";

		try {
			when(mockedResultSet.getLong(colName)).thenReturn(1l);

			Assert.assertEquals(1l, ResultSetHelper.getLong(mockedResultSet, colName).longValue());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void getLongReturnNullValueTest() {
		ResultSet mockedResultSet = mock(ResultSet.class);
		String colName = "Long";

		try {
			when(mockedResultSet.getLong(colName)).thenReturn(0l);
			when(mockedResultSet.wasNull()).thenReturn(true);

			Assert.assertNull(ResultSetHelper.getLong(mockedResultSet, colName));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/* 
	 * 
	 * Test on Date
	 * 
	*/
	@Test
	public void getDateReturnDateTest() {
		ResultSet mockedResultSet = mock(ResultSet.class);
		String colName = "Date";

		try {
			Calendar calendar = Calendar.getInstance();
			when(mockedResultSet.getDate(colName)).thenReturn(new Date(calendar.getTimeInMillis()));

			Assert.assertTrue(ResultSetHelper.getDate(mockedResultSet, colName) instanceof java.util.Date);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void getDateReturnExpectedValueTest() {
		ResultSet mockedResultSet = mock(ResultSet.class);
		String colName = "Date";

		try {
			Calendar calendar = Calendar.getInstance();
			java.util.Date dateRef = new java.util.Date(calendar.getTimeInMillis());
			dateRef = DateUtils.truncate(dateRef, Calendar.HOUR);
			
			when(mockedResultSet.getDate(colName)).thenReturn(new Date(calendar.getTimeInMillis()));

			Assert.assertEquals(dateRef, ResultSetHelper.getDate(mockedResultSet, colName));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void getDateReturnNullValueTest() {
		ResultSet mockedResultSet = mock(ResultSet.class);
		String colName = "Date";

		try {
			when(mockedResultSet.getDate(colName)).thenReturn(null);

			Assert.assertNull(ResultSetHelper.getDate(mockedResultSet, colName));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
