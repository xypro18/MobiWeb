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
@Table(name = "PRODUTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Produto.findAll", query = "SELECT p FROM Produto p")
    , @NamedQuery(name = "Produto.findById", query = "SELECT p FROM Produto p WHERE p.id = :id")
    , @NamedQuery(name = "Produto.findByName", query = "SELECT p FROM Produto p WHERE p.name = :name")
    , @NamedQuery(name = "Produto.findByCreated", query = "SELECT p FROM Produto p WHERE p.created = :created")
    , @NamedQuery(name = "Produto.findByModified", query = "SELECT p FROM Produto p WHERE p.modified = :modified")})
public class Produto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
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
    @JoinColumn(name = "CAT_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Categoria catId;
    @JoinColumn(name = "EMP_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Empregado empId;
    @JoinColumn(name = "SUBCAT_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Subcategoria subcatId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "prodId")
    private Collection<Faturas> faturasCollection;

    public Produto() {
    }

    public Produto(Integer id) {
        this.id = id;
    }

    public Produto(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public Produto(Integer id, String name, Categoria cat, Subcategoria sub, Empregado emp) {
        this.id = id;
        this.name = name;
        this.catId = cat;
        this.subcatId = sub;
        this.empId = emp;
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

    public Categoria getCatId() {
        return catId;
    }

    public void setCatId(Categoria catId) {
        this.catId = catId;
    }

    public Empregado getEmpId() {
        return empId;
    }

    public void setEmpId(Empregado empId) {
        this.empId = empId;
    }

    public Subcategoria getSubcatId() {
        return subcatId;
    }

    public void setSubcatId(Subcategoria subcatId) {
        this.subcatId = subcatId;
    }

    @XmlTransient
    public Collection<Faturas> getFaturasCollection() {
        return faturasCollection;
    }

    public void setFaturasCollection(Collection<Faturas> faturasCollection) {
        this.faturasCollection = faturasCollection;
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
        if (!(object instanceof Produto)) {
            return false;
        }
        Produto other = (Produto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mobiweb.entities.Produto[ id=" + id + " ]";
    }
    
}
