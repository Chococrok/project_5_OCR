package com.dummy.myerp.consumer.dao.impl.cache;

import org.junit.Test;

import com.dummy.myerp.consumer.dao.ComptabiliteDao;
import com.dummy.myerp.consumer.dao.impl.ComptabiliteDaoImpl;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Assert;

public class JournalComptableDaoCacheTest {

	@Test
	public void getByCodeTest() {

		ArrayList<JournalComptable> list = new ArrayList<JournalComptable>();
		list.add(new JournalComptable("foo", "foo"));
		list.add(new JournalComptable("bar", "bar"));
		list.add(new JournalComptable("foobar", "foobar"));

		ArrayList<JournalComptable> otherList = new ArrayList<JournalComptable>();
		otherList.add(new JournalComptable("foo", "boo"));
		otherList.add(new JournalComptable("bar", "far"));
		otherList.add(new JournalComptable("foobar", "boofar"));

		ComptabiliteDao comptabiliteDao = mock(ComptabiliteDaoImpl.class);
		when(comptabiliteDao.getListJournalComptable())
			.thenReturn(list)
			.thenReturn(otherList);

		JournalComptableDaoCache journalComptableDaoCache = new JournalComptableDaoCache(comptabiliteDao);

		String code = "foo";
		Assert.assertEquals("foo", journalComptableDaoCache.getByCode(code).getLibelle());

		Assert.assertEquals("boo", comptabiliteDao.getListJournalComptable().get(0).getLibelle());

		Assert.assertEquals("foo", journalComptableDaoCache.getByCode(code).getLibelle());
		
	}

}
