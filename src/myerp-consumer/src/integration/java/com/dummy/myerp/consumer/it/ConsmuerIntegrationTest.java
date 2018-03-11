package com.dummy.myerp.consumer.it;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dummy.myerp.consumer.dao.ComptabiliteDao;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.technical.exception.NotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class ConsmuerIntegrationTest {
	
	@Inject
	ComptabiliteDao comptabiliteDao;
	
	@Test
	public void printSomthing() throws NotFoundException {
		System.out.println("@@@@@@@@@@@@@@@@@@@@@");
		comptabiliteDao.iAmAlive();
		EcritureComptable e = this.comptabiliteDao.getEcritureComptable(-2);
		System.out.println("@@@@@@@@@@@@@@@@@@@@@" + e);
	}

}
