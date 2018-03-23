package com.dummy.myerp.business.it;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import java.io.FileInputStream;

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
public class ConsmuerIntegrationReadTest extends AbstractIntegrationTest {

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

}
