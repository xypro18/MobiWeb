/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobiweb.ejbs;

import com.mobiweb.dao.GenericJpaDao;
import com.mobiweb.entities.Categoria;
import com.mobiweb.exceptions.AppException;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

/**
 *
 * @author cristianor
 */
@Stateful
public class CustomerService {
    @Inject
    GenericJpaDao dao;
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Categoria addCategory(String name) throws AppException {
        Categoria cat = new Categoria(name);
        dao.save(cat);
        if (cat.getId() > 0) {
            throw new AppException();
        }
        return cat;
    }     
}
