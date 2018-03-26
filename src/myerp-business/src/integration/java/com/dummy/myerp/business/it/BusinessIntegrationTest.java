package com.dummy.myerp.business.it;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import java.io.FileInputStream;

import com.dummy.myerp.technical.exception.FunctionalException;
import org.apache.commons.io.IOUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Assert;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dummy.myerp.business.contrat.manager.ComptabiliteManager;
import com.dummy.myerp.business.impl.manager.ComptabiliteManagerImpl;
import com.dummy.myerp.consumer.dao.ComptabiliteDao;
import com.dummy.myerp.model.bean.comptabilite.*;
import com.dummy.myerp.technical.exception.NotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class BusinessIntegrationTest extends AbstractIntegrationTest {

	@Inject
	ComptabiliteManager comptabiliteManager;
	
	@Inject
	ComptabiliteDao comptabiliteDao;

	@Test
	public void addRefTest() throws NotFoundException {
		EcritureComptable ecritureComptable = this.comptabiliteManager.getEcritureComptable(-5);
		
		String journalCode = ecritureComptable.getJournal().getCode();
		Date date = ecritureComptable.getDate();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int annee = calendar.get(Calendar.YEAR);
		
		SequenceEcritureComptable sequenceEcritureComptable = this.comptabiliteDao.getSequenceEcritureComptableByJournalCodeAndByAnne(journalCode, annee);
		Assert.assertEquals(51, sequenceEcritureComptable.getDerniereValeur().intValue());
		
		this.comptabiliteManager.addReference(ecritureComptable);
		sequenceEcritureComptable = this.comptabiliteDao.getSequenceEcritureComptableByJournalCodeAndByAnne(journalCode, annee);
		
		Assert.assertEquals("BQ-2016/00052", ecritureComptable.getReference());
		Assert.assertEquals(52, sequenceEcritureComptable.getDerniereValeur().intValue());
	}

	@Test
	public void getEcritureComptableTest() throws NotFoundException {
		EcritureComptable ecritureComptable = this.comptabiliteManager.getEcritureComptable(-1);

		Assert.assertEquals("Cartouches d’imprimante", ecritureComptable.getLibelle());
		Assert.assertEquals("AC-2016/00001", ecritureComptable.getReference());
	}

	@Test
	public void getListCompteComptableTest() {
		List<CompteComptable> compteComptables = this.comptabiliteManager.getListCompteComptable();
		Assert.assertEquals(7, compteComptables.size());
	}

	@Test
	public void getListJournalComptableTest() {
		List<JournalComptable> journalComptables = this.comptabiliteManager.getListJournalComptable();
		Assert.assertEquals(4, journalComptables.size());
	}

	@Test
	public void getListEcritureComptableTest() {
		List<EcritureComptable> ecritureComptables = this.comptabiliteManager.getListEcritureComptable();
		Assert.assertEquals(5, ecritureComptables.size());
	}

	@Test()
	public void checkEcritureComptableTest() throws FunctionalException {
		EcritureComptable vEcritureComptable = new EcritureComptable();
		vEcritureComptable.setReference("AC-2018/00001");
		vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
		vEcritureComptable.setDate(new Date());
		vEcritureComptable.setLibelle("Libelle");
		vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
				null, new BigDecimal(123),
				null));
		vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
				null, null,
				new BigDecimal(123)));

		this.comptabiliteManager.checkEcritureComptable(vEcritureComptable);
	}

	@Test(expected = FunctionalException.class)
	public void checkEcritureComptableFailureTest() throws FunctionalException {
		EcritureComptable vEcritureComptable = new EcritureComptable();
		vEcritureComptable.setReference("AC-2016/00001");
		vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
		vEcritureComptable.setDate(new Date());
		vEcritureComptable.setLibelle("Libelle");
		vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
				null, new BigDecimal(123),
				null));
		vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
				null, null,
				new BigDecimal(123)));

		this.comptabiliteManager.checkEcritureComptable(vEcritureComptable);
	}

}
