package com.dummy.myerp.consumer.dao.impl.cache;

import org.junit.Test;

import com.dummy.myerp.consumer.dao.impl.cache.CompteComptableDaoCache;

import org.junit.Assert;

public class CompteComptableDaoCacheTest {

	@Test
	public void methodToTest() {
		CompteComptableDaoCache compteComptableDaoCache = new CompteComptableDaoCache();
		Integer number = 1;
		compteComptableDaoCache.getByNumero(number);
		Assert.assertTrue(false);
	}

}
