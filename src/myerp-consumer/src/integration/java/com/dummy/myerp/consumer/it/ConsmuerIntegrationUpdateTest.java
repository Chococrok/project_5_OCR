package com.dummy.myerp.consumer.it;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Assert;
import org.junit.Before;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dummy.myerp.consumer.dao.ComptabiliteDao;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.NotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class ConsmuerIntegrationUpdateTest extends AbstractIntegrationTest {

	@Inject
	ComptabiliteDao comptabiliteDao;

	@Test
	public void updateEcritureComptableTest() {
		EcritureComptable eCToInsert = new EcritureComptable();
		EcritureComptable eCInserted = null;
		
		Date now = new Date();
		JournalComptable journalComptable = new JournalComptable();
		journalComptable.setCode("AC");
		LigneEcritureComptable lEC1 = new LigneEcritureComptable();
		LigneEcritureComptable lEC2 = new LigneEcritureComptable();
		lEC1.setCredit(new BigDecimal(9));
		lEC1.setDebit(new BigDecimal(9));
		lEC1.setLibelle("hello libelle 1");
		lEC2.setCredit(new BigDecimal(9));
		lEC2.setDebit(new BigDecimal(9));
		lEC2.setLibelle("hello libelle 2");
		CompteComptable cC = new CompteComptable();
		cC.setNumero(401);
		lEC1.setCompteComptable(cC);
		lEC2.setCompteComptable(cC);
		
		eCToInsert.setId(-5);
		eCToInsert.setDate(now);
		eCToInsert.setJournal(journalComptable);
		eCToInsert.setLibelle("hello libelle");
		eCToInsert.setReference("hello ref");
		
		eCToInsert.addOneListLigneEcriture(lEC1);
		eCToInsert.addOneListLigneEcriture(lEC2);
		
		this.comptabiliteDao.insertEcritureComptable(eCToInsert);
		
		try {
			eCInserted = this.comptabiliteDao.getEcritureComptableByRef("hello ref");
		} catch (NotFoundException e) {
			e.printStackTrace();
			Assert.fail();
		}

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Assert.assertEquals(eCToInsert.getId(), eCInserted.getId());
		Assert.assertEquals(format.format(eCToInsert.getDate()), format.format(eCInserted.getDate()));
		Assert.assertEquals(eCToInsert.getJournal().getCode(), eCInserted.getJournal().getCode());
		Assert.assertEquals(eCToInsert.getLibelle(), eCInserted.getLibelle());
		Assert.assertEquals(eCToInsert.getReference(), eCInserted.getReference());
		Assert.assertEquals(eCToInsert.getListLigneEcriture().size(), eCInserted.getListLigneEcriture().size());
	}


}
