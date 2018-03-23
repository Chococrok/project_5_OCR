package com.dummy.myerp.business.impl.manager;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import com.dummy.myerp.business.contrat.manager.ComptabiliteManager;
import com.dummy.myerp.business.impl.AbstractBusinessManager;
import com.dummy.myerp.business.impl.TransactionManager;
import com.dummy.myerp.consumer.dao.ComptabiliteDao;
import com.dummy.myerp.model.bean.comptabilite.*;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;

/**
 * Comptabilite manager implementation.
 */
public class ComptabiliteManagerImpl extends AbstractBusinessManager implements ComptabiliteManager {

	public ComptabiliteManagerImpl() {};
	
	public ComptabiliteManagerImpl(ComptabiliteDao comptabiliteDao, TransactionManager transactionManager) {
		super(comptabiliteDao, transactionManager);
	}

	@Override
	public EcritureComptable getEcritureComptable(int id) throws NotFoundException {
		return this.comptabiliteDao.getEcritureComptable(id);
	}
	
	@Override
	public List<CompteComptable> getListCompteComptable() {
		return this.comptabiliteDao.getListCompteComptable();
	}

	@Override
	public List<JournalComptable> getListJournalComptable() {
		return this.comptabiliteDao.getListJournalComptable();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<EcritureComptable> getListEcritureComptable() {
		return this.comptabiliteDao.getListEcritureComptable();
	}

	/**
	 * {@inheritDoc}
	 */
	// TODO à tester
	@Override
	public synchronized void addReference(EcritureComptable pEcritureComptable) {
		LOGGER.debug("adding Reference...");
		
		boolean newSequence = false;
		
		String journalCode = pEcritureComptable.getJournal().getCode();
		Date date = pEcritureComptable.getDate();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int annee = calendar.get(Calendar.YEAR);

		SequenceEcritureComptable sequenceEcritureComptable = this.comptabiliteDao
				.getSequenceEcritureComptableByJournalCodeAndByAnne(journalCode, annee);
		
		if (sequenceEcritureComptable == null) {
			LOGGER.debug("sequenceEcritureComptable not found, a new one will be added");
			
			sequenceEcritureComptable = new SequenceEcritureComptable(journalCode, annee, 0);
			newSequence = true;
		}
		
		int derniereValeur = sequenceEcritureComptable.getDerniereValeur();
		derniereValeur++;
		
		sequenceEcritureComptable.setDerniereValeur(derniereValeur);
		
		if (newSequence) {
			this.comptabiliteDao.insertSequenceEcritureComptable(sequenceEcritureComptable);
		} else {
			this.comptabiliteDao.updateSequenceEcritureComptable(sequenceEcritureComptable);
		}
		String ref = this.buildReference(journalCode, annee, derniereValeur);
		
		pEcritureComptable.setReference(ref);
		// TODO à implémenter
		// Bien se réferer à la JavaDoc de cette méthode !
		/*
		 * Le principe : 1. Remonter depuis la persitance la dernière valeur de la
		 * séquence du journal pour l'année de l'écriture (table
		 * sequence_ecriture_comptable) 2. * S'il n'y a aucun enregistrement pour le
		 * journal pour l'année concernée : 1. Utiliser le numéro 1. Sinon : 1. Utiliser
		 * la dernière valeur + 1 3. Mettre à jour la référence de l'écriture avec la
		 * référence calculée (RG_Compta_5) 4. Enregistrer (insert/update) la valeur de
		 * la séquence en persitance (table sequence_ecriture_comptable)
		 */

	}

	/**
	 * {@inheritDoc}
	 */
	// TODO à tester
	@Override
	public void checkEcritureComptable(EcritureComptable pEcritureComptable) throws FunctionalException {
		this.checkEcritureComptableUnit(pEcritureComptable);
		this.checkEcritureComptableContext(pEcritureComptable);
	}

	/**
	 * Vérifie que l'Ecriture comptable respecte les règles de gestion unitaires,
	 * c'est à dire indépendemment du contexte (unicité de la référence, exercie
	 * comptable non cloturé...)
	 *
	 * @param pEcritureComptable
	 *            -
	 * @throws FunctionalException
	 *             Si l'Ecriture comptable ne respecte pas les règles de gestion
	 */
	// TODO tests à compléter
	protected void checkEcritureComptableUnit(EcritureComptable pEcritureComptable) throws FunctionalException {
		// ===== Vérification des contraintes unitaires sur les attributs de l'écriture
		Set<ConstraintViolation<EcritureComptable>> vViolations = getConstraintValidator().validate(pEcritureComptable);
		if (!vViolations.isEmpty()) {
			throw new FunctionalException("L'écriture comptable ne respecte pas les règles de gestion.",
					new ConstraintViolationException(
							"L'écriture comptable ne respecte pas les contraintes de validation", vViolations));
		}

		// ===== RG_Compta_2 : Pour qu'une écriture comptable soit valide, elle doit
		// être équilibrée
		if (!pEcritureComptable.isEquilibree()) {
			throw new FunctionalException("L'écriture comptable n'est pas équilibrée.");
		}

		// ===== RG_Compta_3 : une écriture comptable doit avoir au moins 2 lignes
		// d'écriture (1 au débit, 1 au crédit)
		int vNbrCredit = 0;
		int vNbrDebit = 0;
		for (LigneEcritureComptable vLigneEcritureComptable : pEcritureComptable.getListLigneEcriture()) {
			if (BigDecimal.ZERO
					.compareTo(ObjectUtils.defaultIfNull(vLigneEcritureComptable.getCredit(), BigDecimal.ZERO)) != 0) {
				vNbrCredit++;
			}
			if (BigDecimal.ZERO
					.compareTo(ObjectUtils.defaultIfNull(vLigneEcritureComptable.getDebit(), BigDecimal.ZERO)) != 0) {
				vNbrDebit++;
			}
		}
		// On test le nombre de lignes car si l'écriture à une seule ligne
		// avec un montant au débit et un montant au crédit ce n'est pas valable
		if (pEcritureComptable.getListLigneEcriture().size() < 2 || vNbrCredit < 1 || vNbrDebit < 1) {
			throw new FunctionalException(
					"L'écriture comptable doit avoir au moins deux lignes : une ligne au débit et une ligne au crédit.");
		}

		// TODO ===== RG_Compta_5 : Format et contenu de la référence
		// vérifier que l'année dans la référence correspond bien à la date de
		// l'écriture, idem pour le code journal...
	}

	/**
	 * Vérifie que l'Ecriture comptable respecte les règles de gestion liées au
	 * contexte (unicité de la référence, année comptable non cloturé...)
	 *
	 * @param pEcritureComptable
	 *            -
	 * @throws FunctionalException
	 *             Si l'Ecriture comptable ne respecte pas les règles de gestion
	 */
	protected void checkEcritureComptableContext(EcritureComptable pEcritureComptable) throws FunctionalException {
		// ===== RG_Compta_6 : La référence d'une écriture comptable doit être unique
		if (StringUtils.isNoneEmpty(pEcritureComptable.getReference())) {
			try {
				EcritureComptable vECRef = this.comptabiliteDao
						.getEcritureComptableByRef(pEcritureComptable.getReference());

				// S'il s'agit d'une nouvelle écriture, où alors si l'écriture remontée
				// correspond à celle à vérifier...
				if (pEcritureComptable.getId() == null || !pEcritureComptable.getId().equals(vECRef.getId())) {
					throw new FunctionalException("Une autre écriture comptable existe déjà avec la même référence.");
				}
			} catch (NotFoundException vEx) {
				// Dans ce cas, c'est bon, ça veut dire qu'on n'a aucune autre écriture avec la
				// même référence.
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void insertEcritureComptable(EcritureComptable pEcritureComptable) throws FunctionalException {
		this.checkEcritureComptable(pEcritureComptable);
		TransactionStatus vTS = this.transactionManager.beginTransactionMyERP();
		try {
			this.comptabiliteDao.insertEcritureComptable(pEcritureComptable);
			this.transactionManager.commitMyERP(vTS);
			vTS = null;
		} finally {
			this.transactionManager.rollbackMyERP(vTS);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateEcritureComptable(EcritureComptable pEcritureComptable) throws FunctionalException {
		TransactionStatus vTS = this.transactionManager.beginTransactionMyERP();
		try {
			this.comptabiliteDao.updateEcritureComptable(pEcritureComptable);
			this.transactionManager.commitMyERP(vTS);
			vTS = null;
		} catch (TransactionException e) {
			LOGGER.error(e);
		}

		try {
			this.transactionManager.rollbackMyERP(vTS);
		} catch (TransactionException e) {
			LOGGER.error(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteEcritureComptable(Integer pId) {
		TransactionStatus vTS = this.transactionManager.beginTransactionMyERP();
		try {
			this.comptabiliteDao.deleteEcritureComptable(pId);
			this.transactionManager.commitMyERP(vTS);
			vTS = null;
		} catch (TransactionException e) {
			LOGGER.error(e);
		}

		try {
			this.transactionManager.rollbackMyERP(vTS);
		} catch (TransactionException e) {
			LOGGER.error(e);
		}
	}
	
	String buildReference(String journalCode, int annee, int derniereValeur) {
		StringBuilder referenceBuilder = new StringBuilder(journalCode);
		referenceBuilder.append("-");
		referenceBuilder.append(annee);
		referenceBuilder.append("/");
		referenceBuilder.append(derniereValeur);
		
		while (referenceBuilder.length() < 13) {
			referenceBuilder.insert(8, "0");
		}

		return referenceBuilder.toString();
	}
}
