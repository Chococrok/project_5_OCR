package com.dummy.myerp.consumer.it;


import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Assert;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dummy.myerp.consumer.dao.ComptabiliteDao;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.technical.exception.NotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class ConsmuerIntegrationCreateTest extends AbstractIntegrationTest {

	@Inject
	ComptabiliteDao comptabiliteDao;
	
	@Test
	public void insertEcritureComptableTest() {
		
		EcritureComptable eCToInsert = new EcritureComptable();
		EcritureComptable eCInserted = null;
		
		Date now = new Date();
		JournalComptable journalComptable = new JournalComptable();
		journalComptable.setCode("AC");
		
		eCToInsert.setId(-666);		
		eCToInsert.setDate(now);
		eCToInsert.setJournal(journalComptable);
		eCToInsert.setLibelle("hello libelle");
		eCToInsert.setReference("hello ref");
		
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
	}

}
