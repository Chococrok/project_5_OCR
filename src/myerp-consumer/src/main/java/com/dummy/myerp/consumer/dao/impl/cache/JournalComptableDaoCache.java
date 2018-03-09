package com.dummy.myerp.consumer.dao.impl.cache;

import java.util.List;

import javax.inject.Inject;

import com.dummy.myerp.consumer.dao.ComptabiliteDao;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;

/**
 * Cache DAO de {@link JournalComptable}
 */
public class JournalComptableDaoCache {

	// ==================== Attributs ====================
	/** The List compte comptable. */
	private List<JournalComptable> listJournalComptable;

	private ComptabiliteDao comptabiliteDao;

	public JournalComptableDaoCache() {}
	
	public JournalComptableDaoCache(ComptabiliteDao comptabiliteDao) {
		this.comptabiliteDao = comptabiliteDao;
	}

	// ==================== Méthodes ====================
	/**
	 * Gets by code.
	 *
	 * @param pCode
	 *            le code du {@link JournalComptable}
	 * @return {@link JournalComptable} ou {@code null}
	 */
	public JournalComptable getByCode(String pCode) {
		if (listJournalComptable == null) {
			listJournalComptable = comptabiliteDao.getListJournalComptable();
		}

		JournalComptable vRetour = JournalComptable.getByCode(listJournalComptable, pCode);
		return vRetour;
	}
}
