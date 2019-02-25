/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobiweb.dao;

import com.mobiweb.entities.Empregado;
import javax.ejb.Stateless;

/**
 *
 * @author Admin
 */
@Stateless
public class EmpregadoDao extends GenericJpaDao<Empregado>{

    public EmpregadoDao() {
        super(Empregado.class);
    }  
    
}
