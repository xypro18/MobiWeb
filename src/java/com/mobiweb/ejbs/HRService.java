/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobiweb.ejbs;

import com.mobiweb.entities.Categoria;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 *
 * @author cristianor
 */
@Stateless
public class HRService {
    @PersistenceContext(unitName = "MobiWebPU")
    private EntityManager em;
    
    public Categoria updateCategory(String name) {
        Categoria cat = em.find(Categoria.class, 1);
        cat.setName(name);
        //em.merge(cat);
//        try {            
//            Context ctx = new InitialContext();
//            UserTransaction utx = (UserTransaction) ctx.lookup("java:comp/UserTransaction");
//            utx.begin();
//            em.merge(cat);
//            utx.commit();
//        } catch (Exception ex) {
//            Logger.getLogger(HRService.class.getName()).log(Level.SEVERE, null, ex);
//        }
        em.getTransaction().begin();
        em.merge(cat);
        em.getTransaction().commit();
        
        return cat;
    }
}
