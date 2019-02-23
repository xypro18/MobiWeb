/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobiweb.beans;

import com.mobiweb.exceptions.ItemException;
import com.mobiweb.entities.Categoria;
import com.mobiweb.entities.Subcategoria;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author CR
 */
@Named("category")
@SessionScoped
public class CategoryBean implements Serializable {

    //Variáveis expostas ao xhtml
    private String sel_cat;
    private String sel_sub;
    private String add_cat;
    private String add_sub;
    private boolean renderSub = false;
    private Map<String, String> map_cat;
    private Map<String, String> map_sub;
    private Map<String, Categoria> map_test;
    
    //Variáveis internas ao bean
    private List<Categoria> lcat = null;
    private List<Subcategoria> lsub = null;
    private Categoria cat = null;
    private Subcategoria sub = null;
    private int id_cat;
    private int id_sub;

    @Inject
    GenericJpaDao dao;

    @PostConstruct
    public void init() {
        generateCategories();
    }

    public void addCategory() {
        cat = new Categoria(dao.count(Categoria.class) + 1, add_cat);
        dao.save(cat);
        generateCategories();
        sel_cat = add_cat;
        renderSub = true;
        add_cat = "";
        generateSubCategories();
    }
    
    public void addCategory(boolean reset) {
        if (reset)
            add_cat = "";
    }
    
    public void addSubCategory() {
        sub = new Subcategoria(dao.count(Subcategoria.class) + 1, add_sub, cat);
        dao.save(sub);
        generateSubCategories();
        sel_sub = add_sub;
        add_sub = "";        
    }
    

    private void generateCategories() {
        lcat = dao.findAll(Categoria.class);
        map_cat = new HashMap<String, String>();
        for (Categoria c : lcat) {
            map_cat.put(c.getName(), c.getName());
        }
       map_test = new TreeMap<String, Categoria>();
        for (Categoria c : lcat) {
            map_test.put(c.getName(), c);
        }
    }
    
    private void generateSubCategories() {
        lsub = dao.findAll(Subcategoria.class);
        map_sub = new HashMap<String, String>();
        for (Subcategoria s : lsub) {
            map_sub.put(s.getName(), s.getName());
        }
    }

    public Map<String, Categoria> getMap_test() {
        return map_test;
    }

    public void setMap_test(Map<String, Categoria> map_test) {
        this.map_test = map_test;
    }
    
    

    public Map<String, String> getMap_cat() {
        return map_cat;
    }

    public void setMap_cat(Map<String, String> map_cat) {
        this.map_cat = map_cat;
    }

    public Map<String, String> getMap_sub() {
        return map_sub;
    }

    public void setMap_sub(Map<String, String> map_sub) {
        this.map_sub = map_sub;
    }

    public String getAdd_sub() {
        return add_sub;
    }

    public void setAdd_sub(String add_sub) {
        this.add_sub = add_sub;
    }

    public String getSel_sub() {
        return sel_sub;
    }

    public void setSel_sub(String sel_sub) {
        this.sel_sub = sel_sub;
    }

    public boolean isRenderSub() {
        return renderSub;
    }

    public void setRenderSub(boolean renderSub) {
        this.renderSub = renderSub;
    }

    public String getSel_cat() {
        return sel_cat;
    }

    public void setSel_cat(String sel_cat) {
        this.sel_cat = sel_cat;
    }

    public String getAdd_cat() {
        return add_cat;
    }

    public void setAdd_cat(String add_cat) {
        this.add_cat = add_cat;
    }

    
    public void onSelectionChange() {
        if (sel_cat != null && !sel_cat.equals("")) {
//            cities = data.get(country);
            renderSub = true;
        } else {
//            cities = new HashMap<String, String>();
            renderSub = false;
        }

//        System.out.println(sel_main);
//        
//        renderSub = !sel_main.equals("");
    }

//    private String getMessage(String msg) {
//        ResourceBundle rb = ResourceBundle.getBundle("com.mobiweb.resources.messages");
//        return rb.getString(msg);
//    }
//
//    private String getCategoryMessage() {
//        return getMessage("select_category");
//    }
//
//    private String getSubCategoryMessage() {
//        return getMessage("select_subcategory");
//    }
}
