package com.dummy.myerp.consumer.it;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dummy.myerp.consumer.dao.ComptabiliteDao;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
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

	@Test
	public void getListCompteComptableTest() {
		String test = this.comptabiliteDao.getListCompteComptable().get(0).getLibelle();
		System.out.println("99999999999999999999" + test);
	}

	@Test
	public void getListJournalComptableTest() {

	}

	@Test
	public void getListEcritureComptableTest() {

	}

	@Test
	public void getEcritureComptableTest() throws NotFoundException {

	}

	@Test
	public void getEcritureComptableByRefTest() throws NotFoundException {

	}

	@Test
	public void loadListLigneEcritureTest() {

	}

	@Test
	public void insertEcritureComptableTest() {

	}

	@Test
	public void updateEcritureComptableTest() {

	}

	@Test
	public void deleteEcritureComptableTest() {

	}

}
