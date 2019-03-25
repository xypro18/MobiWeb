package com.mobiweb.dao;

import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

@Stateless
public class GenericJpaDao<T extends Serializable> {

    @PersistenceContext(unitName = "MobiWebPU")
    private EntityManager em;

    public <T extends Object> T findOne(Class<T> clazz, int id) {
        return em.find(clazz, id);
    }

    public List<T> findAll(Class<T> clazz) {
        return em.createNamedQuery(clazz.getSimpleName() + ".findAll").getResultList();
    }

    public List<T> findByCatId(Class<T> clazz, int id) {
        return em.createNamedQuery(clazz.getSimpleName() + ".findByCatId").setParameter("catId", id).getResultList();
    }

    public List<T> findBySubcatId(Class<T> clazz, int id) {
        return em.createNamedQuery(clazz.getSimpleName() + ".findBySubcatId").setParameter("subcatId", id).getResultList();
    }

    public List<T> findByProdId(Class<T> clazz, int id) {
        return em.createNamedQuery(clazz.getSimpleName() + ".findByProdId").setParameter("prodId", id).getResultList();
    }

    public List<T> findByFatId(Class<T> clazz, int id) {
        return em.createNamedQuery(clazz.getSimpleName() + ".findByFatId").setParameter("fatId", id).getResultList();
    }

    public T findByUsername(Class<T> clazz, String user) {
        try {
            return (T) em.createNamedQuery(clazz.getSimpleName() + ".findByUsername").setParameter("username", user).getSingleResult();
        } catch (NoResultException e) {
            //Caso não exista utilizador na BD retorna null
            return null;
        }
    }

    public void save(T entity) {
        try {
            em.persist(entity);
            em.flush();
        } catch (ConstraintViolationException e) {
            for (ConstraintViolation<?> cve : e.getConstraintViolations()) {
                System.out.println(e.toString());
            }
        }
    }

    public void update(T entity) {
        em.merge(entity);
    }

    public void delete(T entity) {
        em.remove(entity);
    }

    public void deleteById(Class<T> clazz, int entityId) {
        T entity = findOne(clazz, entityId);
        delete(entity);
    }

    public boolean hasName(Class<T> clazz, String name) {
        return !em.createNamedQuery(clazz.getSimpleName() + ".hasName").setParameter("name", name).setMaxResults(1).getResultList().isEmpty();
    }
    
    public boolean hasName(Class<T> clazz, String name, int parentID) {
        return !em.createNamedQuery(clazz.getSimpleName() + ".hasName").setParameter("name", name).setParameter("id", parentID).setMaxResults(1).getResultList().isEmpty();
    }
}
