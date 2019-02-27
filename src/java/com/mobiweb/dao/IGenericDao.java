/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobiweb.dao;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author CR
 */
public interface IGenericDao<T extends Serializable> {
 
   T findOne(final int id);
 
   List<T> findAll();
 
   void update(final T entity);
 
   void delete(final T entity);
 
   void deleteById(final int entityId);
}
