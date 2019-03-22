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
import java.util.Date;
import javax.inject.Inject;

/**
 *
 * @author CR
 */
@Named("profile")
@SessionScoped
public class ProfileBean implements Serializable {
    private String username;
    private String password;
    private Empregado emp = new Empregado();

    @Inject
    GenericJpaDao dao;

    public ProfileBean() {
    }
        
    //Valida username e password 
    public String validadeUserLogin() {
        username = username.toLowerCase();
        emp = (Empregado) dao.findByUsername(Empregado.class, username);
        if (emp != null && emp.getPassword().equals(PBKDF2.getHash(password.toCharArray(), username))) {
            return "product";
        } else {
            return null;
        }
    }
    
    //Regista utilizador
    public String registerUser() {
        username = emp.getUsername().toLowerCase();
        emp.setUsername(username);
        String hash = PBKDF2.getHash(emp.getPassword().toCharArray(), username);
        emp.setPassword(hash);
        dao.save(emp);
        return "product";
    }
    
    public String logout() {
        username = null;
        password = null;
        emp = null;
        return "login";
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
