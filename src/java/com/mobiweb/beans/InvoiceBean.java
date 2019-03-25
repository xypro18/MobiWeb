package com.mobiweb.beans;

import com.mobiweb.dao.GenericJpaDao;
import com.mobiweb.entities.Fatura;
import com.mobiweb.entities.Linhasdefatura;
import com.mobiweb.entities.Produto;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;

//Classe controladora das Faturas
@Named("invoice")
@SessionScoped
public class InvoiceBean implements Serializable {

    //Atributos associados à view invoice.xhtml e à base de dados que representam a Fatura e Linhas de Fatura
    private int idInv;                  //Identificador da Fatura associado ao dropdown da view
    private double value;
    private double total;
    private String strInv;
    private String strLine;
    private Produto prod = null;        //A Fatura só faz sentido associada a um produto
    private List<Fatura> lfat = null;
    private List<Linhasdefatura> lline = null;

    //Objeto DAO que faz persistência e leitura da base de dados
    @Inject
    GenericJpaDao dao;

    //Adição de Fatura    
    public void addInvoice() {
        //Verifica por JPQL se já existe nome (case insensitive) associado a um produto
        if (dao.hasName(Fatura.class, strInv, prod.getId())) {
            produceGrowlError("record_exists");
        } else {
            //Se registo for unico fecha dialog e regista na base de dados
            RequestContext.getCurrentInstance().execute("PF('dlg_invoice').hide()");
            Fatura fat = new Fatura(strInv, prod);
            dao.save(fat);
            idInv = fat.getId();
            strInv = "";
            generateInvoices();
            generateLines();
        }
    }

    //Alteração do nome da Fatura
    public void changeInvoice() {
        //Verifica por JPQL se já existe nome (case insensitive) associado a um produto
        if (dao.hasName(Fatura.class, strInv, prod.getId())) {
            produceGrowlError("record_exists");
        } else {
            //Se registo for unico fecha dialog e regista na base de dados
            RequestContext.getCurrentInstance().execute("PF('dlg_change_inv').hide()");
            Fatura fat = getFatura();
            fat.setName(strInv);
            dao.update(fat);
            strInv = "";
            generateInvoices();
            generateLines();
        }
    }

    //Adição de Linha de Fatura, não é feita verificação de singularidade uma vez que é opcional
    public void addLine() {
        Linhasdefatura lf = new Linhasdefatura(strLine, value, getFatura());
        dao.save(lf);
        strLine = "";
        value = 0.0;
        generateLines();
    }

    //Leitura de todas as Faturas associadas a um Produto na base de dados
    private void generateInvoices() {
        lfat = dao.findByProdId(Fatura.class, prod.getId());
    }

    //Leitura de todas as Linhas de Fatura associadas a uma Fatura na base de dados
    private void generateLines() {
        lline = dao.findByFatId(Linhasdefatura.class, idInv);
    }

    //Atualiza Linhas de Fatura se Fatura muda, método associado à View
    public void onInvoiceChange() {
        if (idInv != 0) {
            generateLines();
        }
    }

    //Elemina Fatura, e atualiza lista de faturas
    public void deleteInvoice() {
        dao.deleteById(Fatura.class, idInv);
        idInv = 0;
        generateInvoices();
    }

    //Elemina e atualiza linhas de fatura
    public void removeLine(int id) {
        dao.deleteById(Linhasdefatura.class, id);
        generateLines();
    }

    //Utilizado pela view do menu, verifica se existe produto se não existir redirecciona para página de aviso
    public String navigate() {
        if (prod == null) {
            return "invalidInvoice";
        } else {
            return "invoice";
        }
    }

    //Aceita produto e gera Faturas e linhas associadas
    public String acceptProduct(int i) {
        idInv = i;
        generateInvoices();
        generateLines();
        return "invoice";
    }

    //Obtém fatura
    private Fatura getFatura() {
        return (Fatura) dao.findOne(Fatura.class, idInv);
    }

    //Método que edita tabela de linhas de fatura
    public void onRowEdit(RowEditEvent event) {
        Linhasdefatura l = (Linhasdefatura) event.getObject();
        dao.update(l);
        produceGrowlInfo("change_success");
    }

    //Método que cancela edição da tabela de linhas de fatura
    public void onRowCancel(RowEditEvent event) {
        produceGrowlInfo("edit_cancel");
    }

    //Metodo que produz mensagens de informação
    private void produceGrowlInfo(String msg) {
        ResourceBundle rb = ResourceBundle.getBundle("com.mobiweb.resources.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, rb.getString("info"), rb.getString(msg)));
    }

    //Método que produz mensagens de erro
    private void produceGrowlError(String msg) {
        ResourceBundle rb = ResourceBundle.getBundle("com.mobiweb.resources.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, rb.getString("error"), rb.getString(msg)));
        RequestContext.getCurrentInstance().update("growl");
    }

    //Calcula o total de todas as linhas de fatura
    public double getTotal() {
        total = 0;
        for (Linhasdefatura lf : lline) {
            total += lf.getValue();
        }
        return total;
    }
    
    /////////////////////
    //GETTERS E SETTERS//
    /////////////////////
    
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
