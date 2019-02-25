/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobiweb.dao;

import com.mobiweb.entities.Categoria;
import javax.ejb.Stateless;

/**
 *
 * @author Admin
 */
@Stateless
public class CategoriaDao extends GenericJpaDao<Categoria>{

    public CategoriaDao() {
        super(Categoria.class);
    }  
    
}
