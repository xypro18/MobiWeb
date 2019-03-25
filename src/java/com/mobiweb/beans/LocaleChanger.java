package com.mobiweb.beans;

import java.io.Serializable;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;

@Named("locale")
@SessionScoped
public class LocaleChanger implements Serializable {

    private Locale currLocale = Locale.ENGLISH;
    private String language;

    public void langChanged(ValueChangeEvent e) {
        FacesContext context = FacesContext.getCurrentInstance();
        currLocale = new Locale(e.getNewValue().toString());
        context.getViewRoot().setLocale(currLocale);
    }

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
