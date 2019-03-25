package com.mobiweb.dao;

import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

//EJB que faz a leitura e persistência na base de dados
@Stateless
public class GenericJpaDao<T extends Serializable> {

    @PersistenceContext(unitName = "MobiWebPU")
    private EntityManager em;

    //Encontra registo com base no id
    public <T extends Object> T findOne(Class<T> clazz, int id) {
        return em.find(clazz, id);
    }

    //Encontra todos os registos de uma determinada classe
    public List<T> findAll(Class<T> clazz) {
        return em.createNamedQuery(clazz.getSimpleName() + ".findAll").getResultList();
    }

    //Encontra todos os registos associados a uma Categoria via NamedQuery que se encontra no modelo
    public List<T> findByCatId(Class<T> clazz, int id) {
        return em.createNamedQuery(clazz.getSimpleName() + ".findByCatId").setParameter("catId", id).getResultList();
    }

    //Encontra todos os registos associados a uma Subcategoria via NamedQuery que se encontra no modelo
    public List<T> findBySubcatId(Class<T> clazz, int id) {
        return em.createNamedQuery(clazz.getSimpleName() + ".findBySubcatId").setParameter("subcatId", id).getResultList();
    }

    //Encontra todos os registos associados a um Produto via NamedQuery que se encontra no modelo
    public List<T> findByProdId(Class<T> clazz, int id) {
        return em.createNamedQuery(clazz.getSimpleName() + ".findByProdId").setParameter("prodId", id).getResultList();
    }

    //Encontra todos os registos associados a uma Fatura via NamedQuery que se encontra no modelo
    public List<T> findByFatId(Class<T> clazz, int id) {
        return em.createNamedQuery(clazz.getSimpleName() + ".findByFatId").setParameter("fatId", id).getResultList();
    }

    //Encontra username
    public T findByUsername(Class<T> clazz, String user) {
        try {
            return (T) em.createNamedQuery(clazz.getSimpleName() + ".findByUsername").setParameter("username", user).getSingleResult();
        } catch (NoResultException e) {
            //Caso não exista utilizador na BD retorna null
            return null;
        }
    }

    //Método que faz a persistencia do registo
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
    
    //Método que atualiza registo
    public void update(T entity) {
        em.merge(entity);
    }
    
    //Método que apaga registo com base em id
    public void deleteById(Class<T> clazz, int entityId) {
        T entity = findOne(clazz, entityId);
        delete(entity);
    }

    //Método que apaga registo com base em objeto
    public void delete(T entity) {
        em.remove(entity);
    }

    //Método que verifica se já existe nome numa entidade
    public boolean hasName(Class<T> clazz, String name) {
        return !em.createNamedQuery(clazz.getSimpleName() + ".hasName").setParameter("name", name).setMaxResults(1).getResultList().isEmpty();
    }
    
    //Método que verifica se já existe nome numa entidade associada a outra entidade
    public boolean hasName(Class<T> clazz, String name, int parentID) {
        return !em.createNamedQuery(clazz.getSimpleName() + ".hasName").setParameter("name", name).setParameter("id", parentID).setMaxResults(1).getResultList().isEmpty();
    }
}
