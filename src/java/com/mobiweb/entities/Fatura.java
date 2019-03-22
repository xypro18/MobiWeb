/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobiweb.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author CR
 */
@Entity
@Table(name = "FATURA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fatura.findAll", query = "SELECT f FROM Fatura f")
    , @NamedQuery(name = "Fatura.findById", query = "SELECT f FROM Fatura f WHERE f.id = :id")
    , @NamedQuery(name = "Fatura.findByName", query = "SELECT f FROM Fatura f WHERE f.name = :name")
    , @NamedQuery(name = "Fatura.findByProdId", query = "SELECT f FROM Fatura f WHERE f.prodId.id = :prodId")
    , @NamedQuery(name = "Fatura.findByCreated", query = "SELECT f FROM Fatura f WHERE f.created = :created")
    , @NamedQuery(name = "Fatura.findByModified", query = "SELECT f FROM Fatura f WHERE f.modified = :modified")})
public class Fatura implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "NAME")
    private String name;
    @Column(name = "CREATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Column(name = "MODIFIED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modified;
    @JoinColumn(name = "PROD_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Produto prodId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fatId", fetch = FetchType.LAZY)
    private Collection<Linhasdefatura> linhasdefaturaCollection;

    public Fatura() {
    }

    public Fatura(Integer id) {
        this.id = id;
    }

    public Fatura(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Fatura(String name, Produto prod) {
        this.name = name;
        this.prodId = prod;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Produto getProdId() {
        return prodId;
    }

    public void setProdId(Produto prodId) {
        this.prodId = prodId;
    }

    @XmlTransient
    public Collection<Linhasdefatura> getLinhasdefaturaCollection() {
        return linhasdefaturaCollection;
    }

    public void setLinhasdefaturaCollection(Collection<Linhasdefatura> linhasdefaturaCollection) {
        this.linhasdefaturaCollection = linhasdefaturaCollection;
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
        if (!(object instanceof Fatura)) {
            return false;
        }
        Fatura other = (Fatura) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mobiweb.entities.Fatura[ id=" + id + ", name=" + name + " ]";
    }

}