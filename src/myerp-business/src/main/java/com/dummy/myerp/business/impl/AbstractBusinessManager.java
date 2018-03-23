package com.dummy.myerp.business.impl;

import javax.inject.Inject;
import javax.validation.Configuration;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dummy.myerp.consumer.dao.ComptabiliteDao;

/**
 * <p>Classe mère des Managers</p>
 */
public abstract class AbstractBusinessManager {
	
	/** Logger Log4j pour la classe */
	protected static final Logger LOGGER = LogManager.getLogger(AbstractBusinessManager.class);

    protected ComptabiliteDao comptabiliteDao;
    
    protected TransactionManager transactionManager;
    
    public AbstractBusinessManager() {};

	public AbstractBusinessManager(ComptabiliteDao comptabiliteDao, TransactionManager transactionManager) {
		super();
		this.comptabiliteDao = comptabiliteDao;
		this.transactionManager = transactionManager;
	}

	/**
     * Renvoie un {@link Validator} de contraintes
     *
     * @return Validator
     */
    protected Validator getConstraintValidator() {
        Configuration<?> vConfiguration = Validation.byDefaultProvider().configure();
        ValidatorFactory vFactory = vConfiguration.buildValidatorFactory();
        Validator vValidator = vFactory.getValidator();
        return vValidator;
    }
}
