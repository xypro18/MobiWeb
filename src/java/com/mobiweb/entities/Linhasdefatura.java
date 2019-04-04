/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobiweb.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Rodrigues
 */
@Entity
@Table(name = "LINHASDEFATURA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Linhasdefatura.findAll", query = "SELECT l FROM Linhasdefatura l")
    , @NamedQuery(name = "Linhasdefatura.findById", query = "SELECT l FROM Linhasdefatura l WHERE l.id = :id")
    , @NamedQuery(name = "Linhasdefatura.findByFatId", query = "SELECT l FROM Linhasdefatura l WHERE l.fatId.id = :fatId")
    , @NamedQuery(name = "Linhasdefatura.findByProdId", query = "SELECT l FROM Linhasdefatura l WHERE l.prodId.id = :prodId")
    , @NamedQuery(name = "Linhasdefatura.findByCreated", query = "SELECT l FROM Linhasdefatura l WHERE l.created = :created")
    , @NamedQuery(name = "Linhasdefatura.findByModified", query = "SELECT l FROM Linhasdefatura l WHERE l.modified = :modified")})
public class Linhasdefatura implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "REP")
    private int rep;

    @JoinColumn(name = "PROD_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Produto prodId;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "CREATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Column(name = "MODIFIED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modified;
    @JoinColumn(name = "FAT_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Fatura fatId;

    public Linhasdefatura() {
    }

    public Linhasdefatura(Integer id) {
        this.id = id;
    }

    public Linhasdefatura(Fatura fat, Produto prod) {
        this.fatId = fat;
        this.prodId = prod;
        this.rep = 1;
    }
    
    public void incrementRep() {
        rep++;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public Fatura getFatId() {
        return fatId;
    }

    public void setFatId(Fatura fatId) {
        this.fatId = fatId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Linhasdefatura)) {
            return false;
        }
        Linhasdefatura other = (Linhasdefatura) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mobiweb.entities.Linhasdefatura[ id=" + id + " ]";
    }

    public Produto getProdId() {
        return prodId;
    }

    public void setProdId(Produto prodId) {
        this.prodId = prodId;
    }

    public int getRep() {
        return rep;
    }

    public void setRep(int rep) {
        this.rep = rep;
    }

}
