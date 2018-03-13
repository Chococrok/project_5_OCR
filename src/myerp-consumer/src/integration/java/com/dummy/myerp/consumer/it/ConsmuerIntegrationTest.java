package com.dummy.myerp.consumer.it;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Assert;
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
		List<CompteComptable> compteComptables = this.comptabiliteDao.getListCompteComptable();
		Assert.assertEquals(7, compteComptables.size());
	}

	@Test
	public void getListJournalComptableTest() {
		List<JournalComptable> journalComptables = this.comptabiliteDao.getListJournalComptable();
		Assert.assertEquals(4, journalComptables.size());
	}

	@Test
	public void getListEcritureComptableTest() {
		List<EcritureComptable> ecritureComptables = this.comptabiliteDao.getListEcritureComptable();
		Assert.assertEquals(5, ecritureComptables.size());
	}

	@Test
	public void getEcritureComptableTest() throws NotFoundException {
		EcritureComptable ecritureComptable = this.comptabiliteDao.getEcritureComptable(-1);
		Assert.assertEquals("Cartouches d’imprimante", ecritureComptable.getLibelle());
		Assert.assertEquals("AC-2016/00001", ecritureComptable.getReference());
	}

	@Test
	public void getEcritureComptableByRefTest() throws NotFoundException {
		EcritureComptable ecritureComptable = this.comptabiliteDao.getEcritureComptableByRef("AC-2016/00001");
		Assert.assertEquals("Cartouches d’imprimante", ecritureComptable.getLibelle());
		Assert.assertTrue(-1 == ecritureComptable.getId());
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
