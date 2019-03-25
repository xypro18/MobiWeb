/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobiweb.beans;

import com.mobiweb.dao.GenericJpaDao;
import com.mobiweb.entities.Empregado;
import com.mobiweb.resources.PBKDF2;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.primefaces.context.RequestContext;

/**
 *
 * @author CR
 */
@Named("profile")
@SessionScoped
public class ProfileBean implements Serializable {

    private String username;
    private String password;
    private Empregado emp;

    @Inject
    GenericJpaDao dao;

    public ProfileBean() {
    }

    @PostConstruct
    public void init() {
        username = null;
        password = null;
        emp = new Empregado();
        System.out.println("novo empregado");
    }

    //Valida username e password 
    public String validadeUserLogin() {
        username = username.toLowerCase();
        emp = (Empregado) dao.findByUsername(Empregado.class, username);
        if (emp != null && emp.getPassword().equals(PBKDF2.getHash(password.toCharArray(), username))) {
            return "product";
        } else {
            //Mensagem de erro para user/pass invalido
            produceGrowlError("worng_pass_user");
            return null;
        }
    }

    //Regista utilizador
    public String registerUser() {
        username = emp.getUsername().toLowerCase();
        if (dao.findByUsername(Empregado.class, username) == null) {
            emp.setUsername(username);
            String hash = PBKDF2.getHash(emp.getPassword().toCharArray(), username);
            emp.setPassword(hash);
            dao.save(emp);
            return "product";
        } else {
            //Mensagem de erro quando utilizador já existe
            produceGrowlError("user_exists");
            return null;
        }
    }

    //Edita perfil do utilizador
    public String saveChanges() {
        dao.update(emp);
        produceGrowlInfo("change_success");
        return "profile";
    }
    
    //Cancela alteração do perfil do utilizador
    public String cancelChanges() {
        produceGrowlInfo("edit_cancel");
        return "profile";
    }

    public String registration() {
        init();
        return "registration";
    }

    public String logout() {
        init();
        return "login";
    }

    private void produceGrowlInfo(String msg) {
        ResourceBundle rb = ResourceBundle.getBundle("com.mobiweb.resources.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, rb.getString("info"), rb.getString(msg)));
        RequestContext.getCurrentInstance().update("growl");
    }

    private void produceGrowlError(String msg) {
        ResourceBundle rb = ResourceBundle.getBundle("com.mobiweb.resources.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, rb.getString("error"), rb.getString(msg)));
        RequestContext.getCurrentInstance().update("growl");
    }

    /////////////////////
    //GETTERS E SETTERS//
    /////////////////////
    public Empregado getEmp() {
        return emp;
    }

    public void setEmp(Empregado emp) {
        this.emp = emp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
