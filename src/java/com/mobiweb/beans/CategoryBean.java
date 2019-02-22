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

    private String sel_cat;
    private String sel_sub;
    private String add_cat;
    private String add_sub;
    private boolean renderSub = false;
    private List<Categoria> lcat = null;
    private List<Subcategoria> lsub = null;
    private Categoria cat = null;
    private Subcategoria sub = null;

    private Map<String, Map<String, String>> data = new HashMap<String, Map<String, String>>();
//    private String country;
//    private String city;
    private Map<String, String> map_cat;
    private Map<String, String> map_sub;

    @Inject
    GenericJpaDao itembean;

    @PostConstruct
    public void init() {
        generateCategories();
    }

    public void addCategory() {
        cat = new Categoria(itembean.count(Categoria.class) + 1, add_cat);
        itembean.save(cat);
        generateCategories();
        sel_cat = add_cat;
        add_cat = "";
        generateSubCategories();
    }
    
    public void addSubCategory() {
        sub = new Subcategoria(itembean.count(Subcategoria.class) + 1, add_sub, cat.getId());
        itembean.save(sub);
        generateSubCategories();
        sel_sub = add_sub;
        add_sub = "";        
    }
    

    private void generateCategories() {
        lcat = itembean.findAll(Categoria.class);
        map_cat = new HashMap<String, String>();
        for (Categoria c : lcat) {
            map_cat.put(c.getName(), c.getName());
        }
    }
    
    private void generateSubCategories() {
        lsub = itembean.findAll(Subcategoria.class);
        map_sub = new HashMap<String, String>();
        for (Subcategoria s : lsub) {
            map_sub.put(s.getName(), s.getName());
        }
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

//    public Map<String, Map<String, String>> getData() {
//        return data;
//    }
//    public String getCountry() {
//        return country;
//    }
//
//    public void setCountry(String country) {
//        this.country = country;
//    }
//
//    public String getCity() {
//        return city;
//    }
//
//    public void setCity(String city) {
//        this.city = city;
//    }
//    public Map<String, String> getCountries() {
//        return countries;
//    }
//
//    public Map<String, String> getCities() {
//        return cities;
//    }
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
