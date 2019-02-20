/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobiweb.beans;

import java.util.ResourceBundle;

/**
 *
 * @author CR
 */
public class NewClass {
    
    public static void main(String ...args) {
        String msg = "select_category";
        ResourceBundle rb = ResourceBundle.getBundle("com.mobiweb.resources.messages");
        System.out.println(rb.getString(msg));
                
    }
    
}
