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

@Named("list")
@SessionScoped
public class ListBean implements Serializable {

    private List<Produto> lprod = null;
    private List<Produto> filteredProd = null;
    private Produto prod = null;    

    @Inject
    GenericJpaDao dao;

    @PostConstruct
    public void init() {
        generateProducts();
    }



    public String navigate() {
        generateProducts();
        return "list";
    }

    public void generateProducts() {
        lprod = dao.findAll(Produto.class);
        //Manter bidirecionalidade da relação OneToMany 
        for (Produto p : lprod) {
            p.setFaturaCollection(dao.findByProdId(Fatura.class, p.getId()));
        }
    }
    
    public String createInvoiceList(Collection<Fatura> lfat) {
        StringBuilder sb = new StringBuilder();
        for (Fatura f : lfat) {
            sb.append(f.getName());
            sb.append(" ");
        }
        return sb.toString();
    }

    /////////////////////
    //GETTERS E SETTERS//
    /////////////////////

    public List<Produto> getLprod() {
        return lprod;
    }

    public void setLprod(List<Produto> lprod) {
        this.lprod = lprod;
    }

    public Produto getProd() {
        return prod;
    }

    public void setProd(Produto prod) {
        this.prod = prod;
    }

    public List<Produto> getFilteredProd() {
        return filteredProd;
    }

    public void setFilteredProd(List<Produto> filteredProd) {
        this.filteredProd = filteredProd;
    }   
    
}
