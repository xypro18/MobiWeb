package com.mobiweb.beans;

import java.io.Serializable;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;

//Controlador da internacionalização da Applicação
@Named("locale")
@SessionScoped
public class LocaleChanger implements Serializable {
    
    //Atributo associado à View do template geral: masterLayout
    private Locale currLocale = Locale.ENGLISH;
    //Atributo associado à seleção do dropdown
    private String language;
    
    //Verifica linguagem preferida do browser, define locale como pt se for o caso,
    //inglês caso contrário. 
   @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        String lang = context.getViewRoot().getLocale().getLanguage();
        if (lang.equals("pt")) {
            language = "pt";
        } else {
            language = "en";
        }
        currLocale = new Locale(language);
        context.getViewRoot().setLocale(currLocale);
    }
    
    //Método associado à alteração do dropdwon da escolha da lingua e aplicação de novo Locale
    public void langChanged(ValueChangeEvent e) {
        FacesContext context = FacesContext.getCurrentInstance();
        currLocale = new Locale(e.getNewValue().toString());
        context.getViewRoot().setLocale(currLocale);
    }

    /////////////////////
    //GETTERS E SETTERS//
    /////////////////////
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Locale getCurrLocale() {
        return currLocale;
    }

}
