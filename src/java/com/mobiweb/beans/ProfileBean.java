/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobiweb.beans;

import com.mobiweb.dao.EmpregadoDao;
import com.mobiweb.entities.Empregado;
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
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private Character sex;
    private Date dob;
    
    @Inject
    EmpregadoDao empDao;

    public ProfileBean() {
    }
    
    public String validateLogin() {
        Empregado emp = new Empregado(empDao.count()+1, firstname, lastname, username, password, sex, dob);
        empDao.save(emp);
        return "index";
    }
    
    /////////////////////
    //GETTERS E SETTERS//
    /////////////////////

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    public Character getSex() {
        return sex;
    }

    public void setSex(Character sex) {
        this.sex = sex;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    } 
    
}
