/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobiweb.beans;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Rodrigues
 */
@Named("locale")
@SessionScoped
public class LocaleChanger implements Serializable{
    
    private String language = "en";
    
    
    /////////////////////
    //GETTERS E SETTERS//
    /////////////////////

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
    
    
    
}
