/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobiweb.beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author CR
 */
@Named("category")
@SessionScoped
public class CategoryBean implements Serializable {

    private String main;
    private String sub;
    private String sel_main;
    private String sel_sub;
    private boolean renderSub = false;

    public String getSel_main() {
        return sel_main;
    }

    public void setSel_main(String sel_main) {
        this.sel_main = sel_main;
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

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    private Map<String, Map<String, String>> data = new HashMap<String, Map<String, String>>();
    private String country;
    private String city;
    private Map<String, String> countries;
    private Map<String, String> cities;

    @PostConstruct
    public void init() {
//        sel_main = getCategoryMessage();
//        sel_sub = getSubCategoryMessage();

        countries = new HashMap<String, String>();
        countries.put("USA", "USA");
        countries.put("Germany", "Germany");
        countries.put("Brazil", "Brazil");

        Map<String, String> map = new HashMap<String, String>();
        map.put("New York", "New York");
        map.put("San Francisco", "San Francisco");
        map.put("Denver", "Denver");
        data.put("USA", map);

        map = new HashMap<String, String>();
        map.put("Berlin", "Berlin");
        map.put("Munich", "Munich");
        map.put("Frankfurt", "Frankfurt");
        data.put("Germany", map);

        map = new HashMap<String, String>();
        map.put("Sao Paulo", "Sao Paulo");
        map.put("Rio de Janerio", "Rio de Janerio");
        map.put("Salvador", "Salvador");
        data.put("Brazil", map);
    }

    public Map<String, Map<String, String>> getData() {
        return data;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Map<String, String> getCountries() {
        return countries;
    }

    public Map<String, String> getCities() {
        return cities;
    }

    public void onCountryChange() {
        if (country != null && !country.equals("")) {
            cities = data.get(country);
            renderSub = true;
        } else {
            cities = new HashMap<String, String>();
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
