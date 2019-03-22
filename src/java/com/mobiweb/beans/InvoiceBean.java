/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobiweb.beans;

import com.mobiweb.dao.GenericJpaDao;
import com.mobiweb.entities.Fatura;
import com.mobiweb.entities.Linhasdefatura;
import com.mobiweb.entities.Produto;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author CR
 */
@Named("invoice")
@SessionScoped
public class InvoiceBean implements Serializable {

    private int idInv;
    private double value;
    private double total;
    private String strInv;
    private String strLine;
    private Produto prod = null;
    private List<Fatura> lfat = null;
    private List<Linhasdefatura> lline = null;

    @Inject
    GenericJpaDao dao;

    public void addInvoice() {
        Fatura fat = new Fatura(strInv, prod);
        dao.save(fat);
        idInv = fat.getId();
        strInv = "";
        generateInvoices();
        generateLines();
    }

    public void changeInvoice() {
        //TODO IMPLEMENTAR VERIFICAÇÃO DE REPETIÇÃO
        Fatura fat = getFatura();
        fat.setName(strInv);
        dao.update(fat);
        strInv = "";
        generateInvoices();
        generateLines();
    }

    public void addLine() {
        Linhasdefatura lf = new Linhasdefatura(strLine, value, getFatura());
        dao.save(lf);
        strLine = "";
        value = 0.0;
        generateLines();
    }

    private void generateInvoices() {
        lfat = dao.findByProdId(Fatura.class, prod.getId());
    }

    private void generateLines() {
        lline = dao.findByFatId(Linhasdefatura.class, idInv);
    }

    public void onInvoiceChange() {
        if (idInv != 0) {
            generateLines();
        }
    }

    public void deleteInvoice() {
        dao.deleteById(Fatura.class, idInv);
        idInv = 0;
        generateInvoices();
    }

    public void removeLine(int id) {
        dao.deleteById(Linhasdefatura.class, id);
        generateLines();
    }

    public String navigate() {
        if (prod == null) {
            return "invalidInvoice";
        } else {
            return "invoice";
        }
    }

    public String acceptProduct(int i) {
        idInv = i;
        generateInvoices();
        generateLines();
        return "invoice";
    }

    public void onRowEdit(RowEditEvent event) {
        Linhasdefatura l = (Linhasdefatura) event.getObject();
        dao.update(l);
        //TODO Get messages
        FacesMessage msg = new FacesMessage("Change successfull");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        //TODO get messages
        FacesMessage msg = new FacesMessage("Edit Canceled");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    private Fatura getFatura() {
        return (Fatura) dao.findOne(Fatura.class, idInv);
    }

    /////////////////////
    //GETTERS E SETTERS//
    /////////////////////
    public double getTotal() {
        total = 0;
        for (Linhasdefatura lf : lline) {
            total += lf.getValue();
        }
        return total;
    }

//    public void setTotal(double total) {
//        this.total = total;
//    }
    public String getStrLine() {
        return strLine;
    }

    public void setStrLine(String strLine) {
        this.strLine = strLine;
    }

    public Produto getProd() {
        return prod;
    }

    public void setProd(Produto prod) {
        this.prod = prod;
    }

    public List<Fatura> getLfat() {
        return lfat;
    }

    public void setLfat(List<Fatura> lfat) {
        this.lfat = lfat;
    }

    public int getIdInv() {
        return idInv;
    }

    public void setIdInv(int idInv) {
        this.idInv = idInv;
    }

    public String getStrInv() {
        return strInv;
    }

    public void setStrInv(String strInv) {
        this.strInv = strInv;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public List<Linhasdefatura> getLline() {
        return lline;
    }

    public void setLline(List<Linhasdefatura> lline) {
        this.lline = lline;
    }

}
