package com.dummy.myerp.business.impl;

import javax.inject.Inject;
import javax.validation.Configuration;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.dummy.myerp.consumer.dao.ComptabiliteDao;

/**
 * <p>Classe mère des Managers</p>
 */
public abstract class AbstractBusinessManager {

    @Inject
    protected ComptabiliteDao comptabiliteDao;
    
    @Inject
    protected TransactionManager transactionManager;

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
