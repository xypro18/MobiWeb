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
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author CR
 */
@Named("product")
@SessionScoped
public class ProductBean implements Serializable {

    private int idCat;
    private int idSub;
    private String strCat;
    private String strSub;
    private String strProd;
    private List<Categoria> lcat = null;
    private List<Subcategoria> lsub = null;
    private List<Produto> lprod = null;

    @Inject
    GenericJpaDao dao;

    @Inject
    ProfileBean profile;

    @PostConstruct
    public void init() {
        generateCategories();
    }

    public void addCategory() {
        if (dao.hasName(Categoria.class, strCat)) {
            produceGrowlError("record_exists");
        } else {
            RequestContext.getCurrentInstance().execute("PF('dlg_cat').hide()");
            Categoria cat = new Categoria(strCat);
            dao.save(cat);
            generateCategories();
            idCat = cat.getId();
            strCat = "";
            generateSubcategories();
            onCategoryChange();
        }
    }

    public void addSubCategory() {
        if (dao.hasName(Subcategoria.class, strSub, idCat)) {
            produceGrowlError("record_exists");
        } else {
            RequestContext.getCurrentInstance().execute("PF('dlg_sub').hide()");
            Subcategoria sub = new Subcategoria(strSub, getCategory());
            dao.save(sub);
            generateSubcategories();
            idSub = sub.getId();
            strSub = "";
            onSubCategoryChange();
        }
    }

    public void addProduct() {
        if (dao.hasName(Produto.class, strProd, idSub)) {
            produceGrowlError("record_exists");
        } else {
            RequestContext.getCurrentInstance().execute("PF('dlg_prod').hide()");
            Produto p = new Produto(strProd, getSubcategory(), profile.getEmp());
            dao.save(p);
            generateProducts();
            strProd = "";
        }
    }

    public void changeCategory() {
        if (dao.hasName(Categoria.class, strCat)) {
            produceGrowlError("record_exists");
        } else {
            RequestContext.getCurrentInstance().execute("PF('dlg_change_cat').hide()");
            Categoria cat = getCategory();
            cat.setName(strCat);
            dao.update(cat);
            strCat = "";
            generateCategories();
            generateSubcategories();
            generateProducts();
        }
    }

    public void deleteCategory() {
        dao.deleteById(Categoria.class, idCat);
        generateCategories();
        idCat = 0;
        onCategoryChange();
    }

    public void changeSubcategory() {
        if (dao.hasName(Subcategoria.class, strSub, idCat)) {
            produceGrowlError("record_exists");
        } else {
            RequestContext.getCurrentInstance().execute("PF('dlg_change_sub').hide()");
            Subcategoria sub = getSubcategory();
            sub.setName(strSub);
            dao.update(sub);
            strSub = "";
            generateSubcategories();
            generateProducts();
        }
    }

    public void deleteSubcategory() {
        dao.deleteById(Subcategoria.class, idSub);
        generateSubcategories();
        idSub = 0;
        onSubCategoryChange();
    }

    private void generateCategories() {
        lcat = dao.findAll(Categoria.class);
    }

    private void generateSubcategories() {
        lsub = dao.findByCatId(Subcategoria.class, idCat);
    }

    private void generateProducts() {
        lprod = dao.findBySubcatId(Produto.class, idSub);
    }

    public void onCategoryChange() {
        if (idCat != 0) {
            generateSubcategories();
        }
        idSub = 0;
    }

    public void onSubCategoryChange() {
        if (idSub != 0) {
            generateProducts();
        }
    }

    private Categoria getCategory() {
        return (Categoria) dao.findOne(Categoria.class, idCat);
    }

    private Subcategoria getSubcategory() {
        return (Subcategoria) dao.findOne(Subcategoria.class, idSub);
    }

    public void onRowEdit(RowEditEvent event) {
        Produto p = (Produto) event.getObject();
        if (dao.hasName(Produto.class, p.getName(), idSub)) {
            produceGrowlError("record_exists");
            generateProducts();
        } else {
            dao.update(p);
            produceGrowlInfo("change_success");
        }
    }

    public void onRowCancel(RowEditEvent event) {
        produceGrowlInfo("edit_cancel");
    }

    public void removeProduct(int id) {
        dao.deleteById(Produto.class, id);
        generateProducts();
    }

    public String acceptCategory() {
        generateCategories();
        generateSubcategories();
        generateProducts();
        return "product";
    }

    private void produceGrowlInfo(String msg) {
        ResourceBundle rb = ResourceBundle.getBundle("com.mobiweb.resources.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, rb.getString("info"), rb.getString(msg)));
    }

    private void produceGrowlError(String msg) {
        ResourceBundle rb = ResourceBundle.getBundle("com.mobiweb.resources.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, rb.getString("error"), rb.getString(msg)));
        RequestContext.getCurrentInstance().update("growl");
    }

    /////////////////////
    //GETTERS E SETTERS//
    /////////////////////
    public String getStrProd() {
        return strProd;
    }

    public void setStrProd(String strProd) {
        this.strProd = strProd;
    }

    public List<Produto> getLprod() {
        return lprod;
    }

    public void setLprod(List<Produto> lprod) {
        this.lprod = lprod;
    }

    public int getIdCat() {
        return idCat;
    }

    public void setIdCat(int idCat) {
        this.idCat = idCat;
    }

    public int getIdSub() {
        return idSub;
    }

    public void setIdSub(int idSub) {
        this.idSub = idSub;
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

    public String getStrSub() {
        return strSub;
    }

    public void setStrSub(String strSub) {
        this.strSub = strSub;
    }

    public String getStrCat() {
        return strCat;
    }

    public void setStrCat(String strCat) {
        this.strCat = strCat;
    }

}
