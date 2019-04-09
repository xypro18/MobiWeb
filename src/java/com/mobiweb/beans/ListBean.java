package com.mobiweb.beans;

import com.mobiweb.dao.GenericJpaDao;
import com.mobiweb.entities.Fatura;
import com.mobiweb.entities.Produto;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

//Controlador da listagem global
@Named("list")
@SessionScoped
public class ListBean implements Serializable {
    
    //Atributos associados ao produto que são utilizados para gerar a tabela
    private List<Produto> lprod = null;
    //Atributo que permite fazer filtragem na tabela
    private List<Produto> filteredProd = null;
    //TODO: Ver necessidade do produto
    //private Produto prod = null;

    //Atributos associados à Fatura que são utilizados para gerar a tabela
    private List<Fatura> lfat = null;
    //Atributo que permite fazer filtragem na tabela
    private List<Fatura> filteredFat = null;

    //Objeto DAO que faz persistência e leitura da base de dados
    @Inject
    GenericJpaDao dao;
    
    //Gera produtos após construção de bean
    @PostConstruct
    public void init() {
        generateProducts();
    }
    
    //Método invocado pela View do Menu, atualiza a lista de Produtos e retorna a View da Listagem Global
    public String navigateProduct() {
        generateProducts();
        return "listProduct";
    }
    
    //Método invocado pela View do Menu, atualiza a lista de Faturas e retorna a View da Listagem Global
    public String navigateInvoice() {
        generateInvoices();
        return "listInvoice";
    }
    
    //Método que obtém produtos atualizados da base de dados
    public void generateProducts() {
        lprod = dao.findAll(Produto.class);
        //Manter bidirecionalidade da relação OneToMany 
        //TODO: Verificar necessidade do seguinte código
//        for (Produto p : lprod) {
//            p.setFaturaCollection(dao.findByProdId(Fatura.class, p.getId()));
//        }
    }
    
    //Método que obtém Faturas atualizadas da base de dados
    public void generateInvoices() {
        lprod = dao.findAll(Fatura.class);
    }
    
    //Metodo usado para filtragem da coluna de faturas, uma vez que se trata de 
    //uma lista dentro de uma lista, obtém todos os itens numa unica string.
    //TODO: Ver necessidade do seguinte método
//    public String createInvoiceList(Collection<Fatura> lfat) {
//        StringBuilder sb = new StringBuilder();
//        for (Fatura f : lfat) {
//            sb.append(f.getName());
//            sb.append(" ");
//        }
//        return sb.toString();
//    }

    /////////////////////
    //GETTERS E SETTERS//
    /////////////////////

    public List<Produto> getLprod() {
        return lprod;
    }

    public void setLprod(List<Produto> lprod) {
        this.lprod = lprod;
    }

//    public Produto getProd() {
//        return prod;
//    }
//
//    public void setProd(Produto prod) {
//        this.prod = prod;
//    }

    public List<Produto> getFilteredProd() {
        return filteredProd;
    }

    public void setFilteredProd(List<Produto> filteredProd) {
        this.filteredProd = filteredProd;
    }   

    public List<Fatura> getLfat() {
        return lfat;
    }

    public void setLfat(List<Fatura> lfat) {
        this.lfat = lfat;
    }

    public List<Fatura> getFilteredFat() {
        return filteredFat;
    }

    public void setFilteredFat(List<Fatura> filteredFat) {
        this.filteredFat = filteredFat;
    }
}
