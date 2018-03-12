package com.dummy.myerp.consumer.dao.impl.cache;

import org.junit.Test;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import com.dummy.myerp.consumer.dao.ComptabiliteDao;
import com.dummy.myerp.consumer.dao.impl.ComptabiliteDaoImpl;
import com.dummy.myerp.consumer.dao.impl.cache.CompteComptableDaoCache;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;

import org.junit.Assert;

public class CompteComptableDaoCacheTest {

	@Test
	public void getListCompteComptableTest() {

		ArrayList<CompteComptable> list = new ArrayList<CompteComptable>();
		list.add(new CompteComptable(1, "foo"));
		list.add(new CompteComptable(2, "bar"));
		list.add(new CompteComptable(3, "foobar"));

		ArrayList<CompteComptable> otherList = new ArrayList<CompteComptable>();
		otherList.add(new CompteComptable(1, "boo"));
		otherList.add(new CompteComptable(2, "far"));
		otherList.add(new CompteComptable(3, "boofar"));

		ComptabiliteDao comptabiliteDao = mock(ComptabiliteDaoImpl.class);
		when(comptabiliteDao.getListCompteComptable())
			.thenReturn(list)
			.thenReturn(otherList);

		CompteComptableDaoCache compteComptableDaoCache = new CompteComptableDaoCache(comptabiliteDao);

		Integer number = 1;
		Assert.assertEquals("foo", compteComptableDaoCache.getByNumero(number).getLibelle());

		Assert.assertEquals("boo", comptabiliteDao.getListCompteComptable().get(0).getLibelle());

		Assert.assertEquals("foo", compteComptableDaoCache.getByNumero(number).getLibelle());
		
	}

}
