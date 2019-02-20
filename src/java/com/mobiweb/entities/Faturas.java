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
@Table(name = "FATURAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Faturas.findAll", query = "SELECT f FROM Faturas f")
    , @NamedQuery(name = "Faturas.findById", query = "SELECT f FROM Faturas f WHERE f.id = :id")
    , @NamedQuery(name = "Faturas.findByCode", query = "SELECT f FROM Faturas f WHERE f.code = :code")
    , @NamedQuery(name = "Faturas.findByCreated", query = "SELECT f FROM Faturas f WHERE f.created = :created")
    , @NamedQuery(name = "Faturas.findByModified", query = "SELECT f FROM Faturas f WHERE f.modified = :modified")})
public class Faturas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 30)
    @Column(name = "CODE")
    private String code;
    @Column(name = "CREATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Column(name = "MODIFIED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modified;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fatId")
    private Collection<Linhasdefaturas> linhasdefaturasCollection;

    public Faturas() {
    }

    public Faturas(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    @XmlTransient
    public Collection<Linhasdefaturas> getLinhasdefaturasCollection() {
        return linhasdefaturasCollection;
    }

    public void setLinhasdefaturasCollection(Collection<Linhasdefaturas> linhasdefaturasCollection) {
        this.linhasdefaturasCollection = linhasdefaturasCollection;
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
        if (!(object instanceof Faturas)) {
            return false;
        }
        Faturas other = (Faturas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mobiweb.entities.Faturas[ id=" + id + " ]";
    }
    
}
