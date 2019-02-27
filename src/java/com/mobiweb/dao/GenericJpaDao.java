package com.mobiweb.dao;

import com.mobiweb.exceptions.ItemException;
import com.mobiweb.entities.Categoria;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class GenericJpaDao<T extends Serializable> {

    @PersistenceContext(unitName = "MobiWebPU")
    private EntityManager em;

    public <T extends Object>  T findOne(Class<T> clazz, int id) {
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
    
    public T findByUsername(Class<T> clazz, String user) {
        return (T) em.createNamedQuery(clazz.getSimpleName() + ".findByUsername").setParameter("username", user).getSingleResult();
    }

    public void save(T entity) {
        em.persist(entity);
        em.flush();
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

    public boolean addCategory(Categoria item) throws ItemException {
        boolean success = true;
        try {
            System.out.println(" item details" + item.getId());
            em.persist(item);
            em.flush();
        } catch (EntityExistsException pe) {
            success = false;
            throw new ItemException("Item with id " + item.getId() + " exists.");
        }
        return success;
    }

//    public void deleteItem(int id) throws ItemException {
//       Item item= null;
//        item= em.find(Item.class, id);
//        if (item != null) {
//            em.remove(item);
//        } else {
//            throw new ItemException("Employee with id " + id + " does not exist.");
//        }
//    }
//
//    public void updateItem(Item item) {
//        em.merge(item);
//    }
//
//    public Item findItem(int id) {
//        return em.find(Item.class, id);
//    }
//
//    public List<Categoria> getAllItems() {
//        StringBuilder queryText = new StringBuilder("SELECT item FROM Categoria as item ORDER BY item.id");
//        TypedQuery<Categoria> query = em.createQuery(queryText.toString(), Categoria.class);
//        List<Categoria> allItems = query.getResultList();
//        return allItems;
//    }
    public int count(Class<T> clazz) {
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<Categoria> rt = cq.from(clazz);
        cq.select(em.getCriteriaBuilder().count(rt));
        javax.persistence.Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
//   public List<String> getGenres() {   
//      StringBuilder queryText = new StringBuilder("SELECT DISTINCT item.genre FROM Item as item ORDER BY item.genre");
//        TypedQuery<String> query = em.createQuery(queryText.toString(), String.class);
//       
//        List<String> allgenres = query.getResultList();
//        
//        return allgenres;
//    }  
}