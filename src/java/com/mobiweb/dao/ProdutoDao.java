/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobiweb.dao;

import com.mobiweb.entities.Produto;
import javax.ejb.Stateless;

/**
 *
 * @author Admin
 */
@Stateless
public class ProdutoDao extends GenericJpaDao<Produto>{

    public ProdutoDao() {
        super(Produto.class);
    }  
    
}
