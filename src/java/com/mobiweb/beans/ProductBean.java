/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobiweb.beans;

import com.mobiweb.dao.GenericJpaDao;
import com.mobiweb.entities.Categoria;
import com.mobiweb.entities.Produto;
import com.mobiweb.entities.Subcategoria;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author CR
 */
@Named("product")
@SessionScoped
public class ProductBean implements Serializable {

    //Variáveis expostas ao xhtml
    private int id_cat;
    private int id_sub;
    private String add_cat;
    private String add_sub;
    private String add_prod;
    private boolean renderSub = false;
    private boolean renderProd = false;
    private List<Categoria> lcat = null;
    private List<Subcategoria> lsub = null;
    private List<Produto> lprod = null;

    //Variáveis internas ao bean
    private Categoria cat = null;
    private Subcategoria sub = null;

    @Inject
    GenericJpaDao dao;

    @Inject
    ProfileBean profile;

    @PostConstruct
    public void init() {
        generateCategories();
    }

    public void info() {
        FacesContext.getCurrentInstance().addMessage("mid", new FacesMessage("Second Message", "Additional Message Detail"));
    }

    public void addCategory() {
        //TODO IMPLEMENTAR VERIFICAÇÃO DE REPETIÇÃO
        int id = dao.count(Categoria.class) + 1;
        cat = new Categoria(id, add_cat);
        dao.save(cat);
        generateCategories();
        id_cat = id;
        add_cat = "";
        generateSubcategories();
        onCategoryChange();
    }

    public void addSubCategory() {
        //TODO IMPLEMENTAR VERIFICAÇÃO DE REPETIÇÃO
        int id = dao.count(Subcategoria.class) + 1;
        sub = new Subcategoria(id, add_sub, cat);
        dao.save(sub);
        generateSubcategories();
        id_sub = id;
        add_sub = "";
        onSubCategoryChange();
    }

    public void addProduct() {
        //TODO IMPLEMENTAR VERIFICAÇÃO DE REPETIÇÃO
        Produto p = new Produto(dao.count(Produto.class) + 1, add_prod, cat, sub, profile.getEmp());
        dao.save(p);
        generateProducts();
        add_prod = "";

    }

    private void generateCategories() {
        lcat = dao.findAll(Categoria.class);
    }

    private void generateSubcategories() {
        lsub = dao.findByCatId(Subcategoria.class, id_cat);
    }

    private void generateProducts() {
        lprod = dao.findBySubcatId(Produto.class, id_sub);
    }

    public void onCategoryChange() {
        if (id_cat != 0) {
            updateCategory();
            generateSubcategories();
            sub = null;
            id_sub = 0;
            renderSub = true;
            renderProd = false;
        } else {
            renderSub = false;
            renderProd = false;
        }
    }

    public void onSubCategoryChange() {
        updateSubcategory();
        renderProd = (id_sub != 0);
        generateProducts();
    }

    private void updateCategory() {
        cat = (Categoria) dao.findOne(Categoria.class, id_cat);
    }

    private void updateSubcategory() {
        sub = (Subcategoria) dao.findOne(Subcategoria.class, id_sub);
    }

    public void onRowEdit(RowEditEvent event) {
        Produto p = (Produto) event.getObject();
        dao.update(p);
        FacesMessage msg = new FacesMessage("Car Edited", p.getId().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /////////////////////
    //GETTERS E SETTERS//
    /////////////////////
    public String getAdd_prod() {
        return add_prod;
    }

    public void setAdd_prod(String add_prod) {
        this.add_prod = add_prod;
    }

    public boolean isRenderProd() {
        return renderProd;
    }

    public void setRenderProd(boolean renderProd) {
        this.renderProd = renderProd;
    }

    public List<Produto> getLprod() {
        return lprod;
    }

    public void setLprod(List<Produto> lprod) {
        this.lprod = lprod;
    }

    public int getId_cat() {
        return id_cat;
    }

    public void setId_cat(int id_cat) {
        this.id_cat = id_cat;
    }

    public int getId_sub() {
        return id_sub;
    }

    public void setId_sub(int id_sub) {
        this.id_sub = id_sub;
    }

    public List<Categoria> getLcat() {
        return lcat;
    }

    public void setLcat(List<Categoria> lcat) {
        this.lcat = lcat;
    }

    public List<Subcategoria> getLsub() {
        return lsub;
    }

    public void setLsub(List<Subcategoria> lsub) {
        this.lsub = lsub;
    }

    public String getAdd_sub() {
        return add_sub;
    }

    public void setAdd_sub(String add_sub) {
        this.add_sub = add_sub;
    }

    public boolean isRenderSub() {
        return renderSub;
    }

    public void setRenderSub(boolean renderSub) {
        this.renderSub = renderSub;
    }

    public String getAdd_cat() {
        return add_cat;
    }

    public void setAdd_cat(String add_cat) {
        this.add_cat = add_cat;
    }
}
