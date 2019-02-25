/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobiweb.beans;

import com.mobiweb.dao.CategoriaDao;
import com.mobiweb.dao.ProdutoDao;
import com.mobiweb.dao.SubcategoriaDao;
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

    @Inject CategoriaDao daoCat;
    @Inject SubcategoriaDao daoSub;
    @Inject ProdutoDao daoProd;

    @PostConstruct
    public void init() {
        generateCategories();
    }

    public void info() {
        FacesContext.getCurrentInstance().addMessage("mid", new FacesMessage("Second Message", "Additional Message Detail"));
    }

    public void addCategory() {
        //TODO IMPLEMENTAR VERIFICAÇÃO DE REPETIÇÃO
        int id = daoCat.count()+1;
        cat = new Categoria(id, add_cat);
        daoCat.save(cat);
        generateCategories();
        id_cat = id;
        add_cat = "";
        generateSubCategories();
        onCategoryChange();
    }

    public void addCategory(boolean reset) {
        if (reset) {
            add_cat = "";
        }
    }

    public void addSubCategory() {
        //TODO IMPLEMENTAR VERIFICAÇÃO DE REPETIÇÃO
        int id = daoSub.count() + 1;
        Categoria c = daoCat.findOne(id_cat);
        sub = new Subcategoria(id, add_sub, c);
        daoSub.save(sub);
        generateSubCategories();
        id_sub = id;
        add_sub = "";
        onSubCategoryChange();
    }
    
    public void addProduct() {
        //TODO IMPLEMENTAR VERIFICAÇÃO DE REPETIÇÃO
        Produto p = new Produto(daoProd.count() + 1, add_prod);
        daoProd.save(p);
        add_prod = "";
    }

    private void generateCategories() {
        lcat = daoCat.findAll();
    }

    private void generateSubCategories() {
        lsub = daoSub.findByCatId(id_cat);
    }

    public void onCategoryChange() {
        if (id_cat != 0) {
            generateSubCategories();
            id_sub = 0;
            renderSub = true;
            renderProd = false;
        } else {
            renderSub = false;
            renderProd = false;
        }
    }

    public void onSubCategoryChange() {
        renderProd = (id_sub != 0);
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

    public Categoria getCat() {
        return cat;
    }

    public void setCat(Categoria cat) {
        this.cat = cat;
    }

    public Subcategoria getSub() {
        return sub;
    }

    public void setSub(Subcategoria sub) {
        this.sub = sub;
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
