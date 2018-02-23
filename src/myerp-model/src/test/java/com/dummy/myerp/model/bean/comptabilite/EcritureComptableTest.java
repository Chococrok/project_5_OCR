package com.dummy.myerp.model.bean.comptabilite;

import java.math.BigDecimal;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;


public class EcritureComptableTest {
	
	private static final Logger LOGGER = LogManager.getLogger(EcritureComptableTest.class);

    private LigneEcritureComptable createLigne(Integer pCompteComptableNumero, String pDebit, String pCredit) {
        BigDecimal vDebit = pDebit == null ? null : new BigDecimal(pDebit);
        BigDecimal vCredit = pCredit == null ? null : new BigDecimal(pCredit);
        String vLibelle = ObjectUtils.defaultIfNull(vDebit, BigDecimal.ZERO)
                                     .subtract(ObjectUtils.defaultIfNull(vCredit, BigDecimal.ZERO)).toPlainString();
        LigneEcritureComptable vRetour = new LigneEcritureComptable(new CompteComptable(pCompteComptableNumero),
                                                                    vLibelle,
                                                                    vDebit, vCredit);
        return vRetour;
    }
    
    @Test
    public void getTotalDebit() {
        EcritureComptable vEcriture = new EcritureComptable();
        
        vEcriture.setLibelle("Equilibrée");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "200.50", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "100.50", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "1", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "100"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "20", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(3, "1000", null));
        int result =  (int) (200.50 + 100.50 + 1 + 20 + 1000);
        Assert.assertEquals(result, vEcriture.getTotalDebit().intValue());;
    }
    
    @Test
    public void getTotalCredit() {
        EcritureComptable vEcriture = new EcritureComptable();
        
        vEcriture.setLibelle("Equilibrée");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "200.50", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "100.50", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "1", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "100"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "20", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(3, "1000", null));

        Assert.assertEquals(100, vEcriture.getTotalCredit().intValue());;
    }

    @Test
    public void isEquilibree() {
        EcritureComptable vEcriture = new EcritureComptable();

        vEcriture.setLibelle("Equilibrée");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "200.50", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "100.50", "33"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "301"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "40", "7"));
        Assert.assertTrue(vEcriture.toString(), vEcriture.isEquilibree());

        vEcriture.getListLigneEcriture().clear();
        vEcriture.setLibelle("Non équilibrée");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "10", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "20", "1"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "30"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "1", "2"));
        Assert.assertFalse(vEcriture.toString(), vEcriture.isEquilibree());
    }

}
