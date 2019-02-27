package com.mobiweb.dao;

import com.mobiweb.exceptions.ItemException;
import com.mobiweb.entities.Categoria;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class AbstractJpaDao<T extends Serializable> implements IGenericDao<T>{
    protected Class< T > clazz;
    
    @PersistenceContext(unitName = "MobiWebPU")
    private EntityManager em;
    
    public AbstractJpaDao(Class< T > clazzToSet) {
        this.clazz = clazzToSet;
    }
    
    public T findOne(int id) {
        return em.find(clazz, id);
    }

    public List<T> findAll() {
        return em.createNamedQuery(clazz.getSimpleName() + ".findAll").getResultList();
    }

    public List<T> findByCatId(int fk) {
        return em.createNamedQuery(clazz.getSimpleName() + ".findByCatId").setParameter("catId", fk).getResultList();
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

    public void deleteById(int entityId) {
        T entity = findOne(entityId);
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
    public int count() {
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
