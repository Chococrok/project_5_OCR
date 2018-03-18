package com.dummy.myerp.consumer.it;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.Assert;
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
public class ConsmuerIntegrationDeleteTest extends AbstractIntegrationTest {

	@Inject
	ComptabiliteDao comptabiliteDao;

	@Test
	public void deleteEcritureComptableTest() {
		try {
			EcritureComptable eC = this.comptabiliteDao.getEcritureComptableByRef("BQ-2016/00005");
			comptabiliteDao.deleteEcritureComptable(eC.getId());
		} catch (NotFoundException e) {
			e.printStackTrace();
			Assert.fail();
		}
		
		try {
			this.comptabiliteDao.getEcritureComptableByRef("BQ-2016/00005");
		} catch (NotFoundException e) {
			Assert.assertNotNull("There should be a NotFoundException", e);
		}

	}

}
