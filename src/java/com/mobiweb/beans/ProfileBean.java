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
    private Empregado emp;

    @Inject
    GenericJpaDao dao;

    public ProfileBean() {
    }

    public String login() {
        username = username.toLowerCase();
        emp = (Empregado) dao.findByUsername(Empregado.class, username);
        if (emp != null && emp.getPassword().equals(PBKDF2.getHash(password.toCharArray(), username))) {
            return "product";
        } else {
            return null;
        }
    }

    public String save() {
        emp.setUsername(emp.getUsername().toLowerCase());
        emp.setId(dao.count(Empregado.class) + 1);
        String hash = PBKDF2.getHash(emp.getPassword().toCharArray(), emp.getUsername());
        dao.save(emp);
        return "product";
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
