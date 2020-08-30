/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobiweb.ejbs;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.transaction.TransactionSynchronizationRegistry;

/**
 *
 * @author cristianor
 */
@Stateless
//@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class WarehouseService {        
    @Resource
    TransactionSynchronizationRegistry tsr;
    
    public void sendConfirmation() {
        System.out.println("sendConfirmation: " + tsr.getTransactionKey());
    }
    
    public void reserve() {
        System.out.println("reserve: " + tsr.getTransactionKey());
    }    
}
