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

    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private Character sex;
    private Date dob;
    private String role;
    private Empregado emp = new Empregado();

    @Inject
    GenericJpaDao dao;

    public ProfileBean() {
    }

    public String login() {
        username = username.toLowerCase();
        Empregado e = (Empregado) dao.findByUsername(Empregado.class, username);
        if (e != null && e.getPassword().equals(PBKDF2.getHash(password.toCharArray(), username))) {
            emp = e;
            firstname = e.getFirstname();
            lastname = e.getLastname();
            sex = e.getSex();
            dob = e.getDateofbirth();
            role = e.getRole();
            return "product";
        } else {
            return null;
        }
    }

    public String save() {
        username = username.toLowerCase();
        String hash = PBKDF2.getHash(password.toCharArray(), username);
        emp = new Empregado(dao.count(Empregado.class) + 1, firstname, lastname, username, hash, sex, dob, role);
        dao.save(emp);
        return "product";
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Empregado getEmp() {
        return emp;
    }

    public void setEmp(Empregado emp) {
        this.emp = emp;
    }
    
    

}
