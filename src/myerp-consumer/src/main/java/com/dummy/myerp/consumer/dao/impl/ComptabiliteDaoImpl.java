package com.dummy.myerp.consumer.dao.impl;

import java.sql.Types;
import java.util.List;
import java.util.Map;

import javax.inject.Named;
import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.dummy.myerp.consumer.DataSourcesEnum;
import com.dummy.myerp.consumer.dao.ComptabiliteDao;
import com.dummy.myerp.consumer.rowmapper.CompteComptableRM;
import com.dummy.myerp.consumer.rowmapper.EcritureComptableRM;
import com.dummy.myerp.consumer.rowmapper.JournalComptableRM;
import com.dummy.myerp.consumer.rowmapper.LigneEcritureComptableRM;
import com.dummy.myerp.consumer.rowmapper.SequenceEcritureComptableRM;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.SequenceEcritureComptable;
import com.dummy.myerp.technical.exception.NotFoundException;

/**
 * Implémentation de l'interface {@link ComptabiliteDao}
 */
public class ComptabiliteDaoImpl extends AbstractDao implements ComptabiliteDao {
	
	private RowMapper<CompteComptable> compteComptableRM;
	private RowMapper<EcritureComptable> ecritureComptableRM;
	private RowMapper<JournalComptable> journalComptableRM;
	private RowMapper<LigneEcritureComptable> ligneEcritureComptableRM;
	private RowMapper<SequenceEcritureComptable> sequenceEcritureComptableRM;
	
	public ComptabiliteDaoImpl(Map<DataSourcesEnum, DataSource> mapDataSource) {
		super(mapDataSource);
	};
	
	
	public void setCompteComptableRM(RowMapper<CompteComptable> compteComptableRM) {
		this.compteComptableRM = compteComptableRM;
	}

	public void setEcritureComptableRM(RowMapper<EcritureComptable> ecritureComptableRM) {
		this.ecritureComptableRM = ecritureComptableRM;
	}

	public void setJournalComptableRM(RowMapper<JournalComptable> journalComptableRM) {
		this.journalComptableRM = journalComptableRM;
	}

	public void setLigneEcritureComptableRM(RowMapper<LigneEcritureComptable> ligneEcritureComptableRM) {
		this.ligneEcritureComptableRM = ligneEcritureComptableRM;
	}

	public void setSequenceEcritureComptableRM(RowMapper<SequenceEcritureComptable> sequenceEcritureComptableRM) {
		this.sequenceEcritureComptableRM = sequenceEcritureComptableRM;
	}



	// ==================== Méthodes ====================
	/** SQLgetListCompteComptable */
	private static String SQLgetListCompteComptable;

	public void setSQLgetListCompteComptable(String pSQLgetListCompteComptable) {
		SQLgetListCompteComptable = pSQLgetListCompteComptable;
	}

	@Override
	public List<CompteComptable> getListCompteComptable() {
		LOGGER.info("performing getListCompteComptable...");
		JdbcTemplate vJdbcTemplate = new JdbcTemplate(this.getDataSource(DataSourcesEnum.MYERP));
		List<CompteComptable> vList = vJdbcTemplate.query(SQLgetListCompteComptable, this.compteComptableRM);
		return vList;
	}

	/** SQLgetListJournalComptable */
	private static String SQLgetListJournalComptable;

	public void setSQLgetListJournalComptable(String pSQLgetListJournalComptable) {
		SQLgetListJournalComptable = pSQLgetListJournalComptable;
	}

	@Override
	public List<JournalComptable> getListJournalComptable() {
		LOGGER.info("performing getListJournalComptable...");
		JdbcTemplate vJdbcTemplate = new JdbcTemplate(this.getDataSource(DataSourcesEnum.MYERP));
		List<JournalComptable> vList = vJdbcTemplate.query(SQLgetListJournalComptable, this.journalComptableRM);
		return vList;
	}

	// ==================== EcritureComptable - GET ====================

	/** SQLgetListEcritureComptable */
	private static String SQLgetListEcritureComptable;

	public void setSQLgetListEcritureComptable(String pSQLgetListEcritureComptable) {
		SQLgetListEcritureComptable = pSQLgetListEcritureComptable;
	}

	@Override
	public List<EcritureComptable> getListEcritureComptable() {
		LOGGER.info("performing getListEcritureComptable...");
		JdbcTemplate vJdbcTemplate = new JdbcTemplate(this.getDataSource(DataSourcesEnum.MYERP));
		List<EcritureComptable> vList = vJdbcTemplate.query(SQLgetListEcritureComptable, this.ecritureComptableRM);
		
		for (EcritureComptable vBean : vList) {
			
			// Chargement des lignes d'écriture
			this.loadListLigneEcriture(vBean);
		}
		return vList;
	}

	/** SQLgetEcritureComptable */
	private static String SQLgetEcritureComptable;

	public void setSQLgetEcritureComptable(String pSQLgetEcritureComptable) {
		SQLgetEcritureComptable = pSQLgetEcritureComptable;
	}

	@Override
	public EcritureComptable getEcritureComptable(Integer pId) throws NotFoundException {
		LOGGER.info("performing getEcritureComptable...");
		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
		MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
		vSqlParams.addValue("id", pId);
		EcritureComptable vBean;
		try {
			vBean = vJdbcTemplate.queryForObject(SQLgetEcritureComptable, vSqlParams, this.ecritureComptableRM);
			
			// Chargement des lignes d'écriture
			this.loadListLigneEcriture(vBean);
		} catch (EmptyResultDataAccessException vEx) {
			throw new NotFoundException("EcritureComptable non trouvée : id=" + pId);
		}
		return vBean;
	}

	/** SQLgetEcritureComptableByRef */
	private static String SQLgetEcritureComptableByRef;

	public void setSQLgetEcritureComptableByRef(String pSQLgetEcritureComptableByRef) {
		SQLgetEcritureComptableByRef = pSQLgetEcritureComptableByRef;
	}

	@Override
	public EcritureComptable getEcritureComptableByRef(String pReference) throws NotFoundException {
		LOGGER.info("performing getEcritureComptableByRef...");
		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
		MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
		vSqlParams.addValue("reference", pReference);
		EcritureComptable vBean;
		try {
			vBean = vJdbcTemplate.queryForObject(SQLgetEcritureComptableByRef, vSqlParams, this.ecritureComptableRM);
		
			// Chargement des lignes d'écriture
			this.loadListLigneEcriture(vBean);
		} catch (EmptyResultDataAccessException vEx) {
			throw new NotFoundException("EcritureComptable non trouvée : reference=" + pReference);
		}
		return vBean;
	}

	/** SQLloadListLigneEcriture */
	private static String SQLloadListLigneEcriture;

	public void setSQLloadListLigneEcriture(String pSQLloadListLigneEcriture) {
		SQLloadListLigneEcriture = pSQLloadListLigneEcriture;
	}

	@Override
	public void loadListLigneEcriture(EcritureComptable pEcritureComptable) {
		LOGGER.info("performing loadListLigneEcriture...");
		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
		MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
		vSqlParams.addValue("ecriture_id", pEcritureComptable.getId());
		List<LigneEcritureComptable> vList = vJdbcTemplate.query(SQLloadListLigneEcriture, vSqlParams, this.ligneEcritureComptableRM);
		pEcritureComptable.getListLigneEcriture().clear();
		pEcritureComptable.getListLigneEcriture().addAll(vList);
	}

	// ==================== EcritureComptable - INSERT ====================

	/** SQLinsertEcritureComptable */
	private static String SQLinsertEcritureComptable;

	public void setSQLinsertEcritureComptable(String pSQLinsertEcritureComptable) {
		SQLinsertEcritureComptable = pSQLinsertEcritureComptable;
	}

	@Override
	public void insertEcritureComptable(EcritureComptable pEcritureComptable) {
		LOGGER.info("performing insertEcritureComptable...");
		// ===== Ecriture Comptable
		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
		MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
		vSqlParams.addValue("journal_code", pEcritureComptable.getJournal().getCode());
		vSqlParams.addValue("reference", pEcritureComptable.getReference());
		vSqlParams.addValue("date", pEcritureComptable.getDate(), Types.DATE);
		vSqlParams.addValue("libelle", pEcritureComptable.getLibelle());

		vJdbcTemplate.update(SQLinsertEcritureComptable, vSqlParams);

		// ----- Récupération de l'id
		Integer vId = this.queryGetSequenceValuePostgreSQL(DataSourcesEnum.MYERP, "myerp.ecriture_comptable_id_seq",
				Integer.class);
		pEcritureComptable.setId(vId);

		// ===== Liste des lignes d'écriture
		this.insertListLigneEcritureComptable(pEcritureComptable);
	}

	/** SQLinsertListLigneEcritureComptable */
	private static String SQLinsertListLigneEcritureComptable;

	public void setSQLinsertListLigneEcritureComptable(String pSQLinsertListLigneEcritureComptable) {
		SQLinsertListLigneEcritureComptable = pSQLinsertListLigneEcritureComptable;
	}

	/**
	 * Insert les lignes d'écriture de l'écriture comptable
	 * 
	 * @param pEcritureComptable
	 *            l'écriture comptable
	 */
	protected void insertListLigneEcritureComptable(EcritureComptable pEcritureComptable) {
		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
		MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
		vSqlParams.addValue("ecriture_id", pEcritureComptable.getId());

		int vLigneId = 0;
		for (LigneEcritureComptable vLigne : pEcritureComptable.getListLigneEcriture()) {
			vLigneId++;
			vSqlParams.addValue("ligne_id", vLigneId);
			vSqlParams.addValue("compte_comptable_numero", vLigne.getCompteComptable().getNumero());
			vSqlParams.addValue("libelle", vLigne.getLibelle());
			vSqlParams.addValue("debit", vLigne.getDebit());

			vSqlParams.addValue("credit", vLigne.getCredit());

			vJdbcTemplate.update(SQLinsertListLigneEcritureComptable, vSqlParams);
		}
	}

	// ==================== EcritureComptable - UPDATE ====================

	/** SQLupdateEcritureComptable */
	private static String SQLupdateEcritureComptable;

	public void setSQLupdateEcritureComptable(String pSQLupdateEcritureComptable) {
		SQLupdateEcritureComptable = pSQLupdateEcritureComptable;
	}

	@Override
	public void updateEcritureComptable(EcritureComptable pEcritureComptable) {
		LOGGER.info("performing updateEcritureComptable...");
		// ===== Ecriture Comptable
		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
		MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
		vSqlParams.addValue("id", pEcritureComptable.getId());
		vSqlParams.addValue("journal_code", pEcritureComptable.getJournal().getCode());
		vSqlParams.addValue("reference", pEcritureComptable.getReference());
		vSqlParams.addValue("date", pEcritureComptable.getDate(), Types.DATE);
		vSqlParams.addValue("libelle", pEcritureComptable.getLibelle());

		vJdbcTemplate.update(SQLupdateEcritureComptable, vSqlParams);

		// ===== Liste des lignes d'écriture
		this.deleteListLigneEcritureComptable(pEcritureComptable.getId());
		this.insertListLigneEcritureComptable(pEcritureComptable);
	}

	// ==================== EcritureComptable - DELETE ====================

	/** SQLdeleteEcritureComptable */
	private static String SQLdeleteEcritureComptable;

	public void setSQLdeleteEcritureComptable(String pSQLdeleteEcritureComptable) {
		SQLdeleteEcritureComptable = pSQLdeleteEcritureComptable;
	}

	@Override
	public void deleteEcritureComptable(Integer pId) {
		LOGGER.info("performing deleteEcritureComptable...");
		// ===== Suppression des lignes d'écriture
		this.deleteListLigneEcritureComptable(pId);

		// ===== Suppression de l'écriture
		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
		MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
		vSqlParams.addValue("id", pId);
		vJdbcTemplate.update(SQLdeleteEcritureComptable, vSqlParams);
	}

	/** SQLdeleteListLigneEcritureComptable */
	private static String SQLdeleteListLigneEcritureComptable;

	public void setSQLdeleteListLigneEcritureComptable(String pSQLdeleteListLigneEcritureComptable) {
		SQLdeleteListLigneEcritureComptable = pSQLdeleteListLigneEcritureComptable;
	}

	/**
	 * Supprime les lignes d'écriture de l'écriture comptable d'id
	 * {@code pEcritureId}
	 * 
	 * @param pEcritureId
	 *            id de l'écriture comptable
	 */
	protected void deleteListLigneEcritureComptable(Integer pEcritureId) {
		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
		MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
		vSqlParams.addValue("ecriture_id", pEcritureId);
		vJdbcTemplate.update(SQLdeleteListLigneEcritureComptable, vSqlParams);
	}
	
//	New methods ====================
//	================================
	
	private String SQLgetsequenceEcritureComptableByJournalCodeAndByAnne;
	public void setSQLgetsequenceEcritureComptableByJournalCodeAndByAnne(String SQLgetsequenceEcritureComptableByJournalCodeAndByAnne) {
		this.SQLgetsequenceEcritureComptableByJournalCodeAndByAnne = SQLgetsequenceEcritureComptableByJournalCodeAndByAnne;
	}
	
	@Override
	public SequenceEcritureComptable getSequenceEcritureComptableByJournalCodeAndByAnne(String journalCode, int annee) {
		LOGGER.info("performing getSequenceEcritureComptableByJournalCodeAndByAnne...");
		
		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
		
		MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
		vSqlParams.addValue("journal_code", journalCode);
		vSqlParams.addValue("annee", annee);
		
		SequenceEcritureComptable vBean;
		
		try {
			vBean = vJdbcTemplate.queryForObject(SQLgetsequenceEcritureComptableByJournalCodeAndByAnne, vSqlParams, this.sequenceEcritureComptableRM);

		} catch (EmptyResultDataAccessException vEx) {
			return null;
		}
		return vBean;
	}
}
