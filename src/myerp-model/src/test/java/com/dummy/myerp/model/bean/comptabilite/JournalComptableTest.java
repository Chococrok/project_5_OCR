package com.dummy.myerp.model.bean.comptabilite;

import org.junit.Test;

import java.util.ArrayList;

import org.junit.Assert;

public class JournalComptableTest {

	@Test
	public void getByCodeTest() {
		ArrayList<JournalComptable> list = new ArrayList<JournalComptable>();
		list.add(new JournalComptable("1", "foo"));
		list.add(new JournalComptable("2", "bar"));
		list.add(new JournalComptable("3", "foobar"));
		
		JournalComptable c = JournalComptable.getByCode(list, "2");
		JournalComptable d = JournalComptable.getByCode(list, "3");
		
		Assert.assertTrue(c.getLibelle().equals("bar"));
		Assert.assertTrue(d.getLibelle().equals("foobar"));
	}
	
	@Test
	public void getByCodeEmptyListTest() {
		ArrayList<JournalComptable> list = new ArrayList<JournalComptable>();
		
		Assert.assertTrue(JournalComptable.getByCode(list, "3") == null);
	}

}
