package com.dummy.myerp.model.bean.comptabilite;

import org.junit.Test;
import org.junit.Assert;
import java.util.ArrayList;

public class CompteComptableTest {

	@Test
	public void getByNumeroTest() {
		ArrayList<CompteComptable> list = new ArrayList<CompteComptable>();
		list.add(new CompteComptable(1, "foo"));
		list.add(new CompteComptable(2, "bar"));
		list.add(new CompteComptable(3, "foobar"));
		
		CompteComptable c = CompteComptable.getByNumero(list, 2);
		CompteComptable d = CompteComptable.getByNumero(list, 3);
		
		Assert.assertTrue(c.getLibelle().equals("bar"));
		Assert.assertTrue(d.getLibelle().equals("foobar"));
	}
	
	@Test
	public void getByNumeroEmptyListTest() {
		ArrayList<CompteComptable> list = new ArrayList<CompteComptable>();
		
		Assert.assertTrue(CompteComptable.getByNumero(list, 3) == null);
	}

}
