/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobiweb.beans;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author CR
 */
@Named("navigation")
@SessionScoped
public class NavBean implements Serializable {
    private String centerPage = "login";
    
    public NavBean() {
    }

    public String getCenterPage() {
        return centerPage;
    }

    public void setCenterPage(String centerPage) {
        this.centerPage = centerPage;
    }    
}
