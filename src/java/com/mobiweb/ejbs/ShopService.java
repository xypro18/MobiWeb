/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobiweb.ejbs;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 *
 * @author cristianor
 */
@Stateless
@Transactional
public class ShopService implements Serializable{
    
    @Inject
    WarehouseService war;
    
//    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void Test() {
        war.reserve();
        war.sendConfirmation();
    }    
}
