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
 * @author CR
 */
@Entity
@Table(name = "LINHASDEFATURAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Linhasdefaturas.findAll", query = "SELECT l FROM Linhasdefaturas l")
    , @NamedQuery(name = "Linhasdefaturas.findById", query = "SELECT l FROM Linhasdefaturas l WHERE l.id = :id")
    , @NamedQuery(name = "Linhasdefaturas.findByLine", query = "SELECT l FROM Linhasdefaturas l WHERE l.line = :line")
    , @NamedQuery(name = "Linhasdefaturas.findByCreated", query = "SELECT l FROM Linhasdefaturas l WHERE l.created = :created")
    , @NamedQuery(name = "Linhasdefaturas.findByModified", query = "SELECT l FROM Linhasdefaturas l WHERE l.modified = :modified")})
public class Linhasdefaturas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 30)
    @Column(name = "LINE")
    private String line;
    @Column(name = "CREATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Column(name = "MODIFIED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modified;
    @JoinColumn(name = "FAT_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Faturas fatId;

    public Linhasdefaturas() {
    }

    public Linhasdefaturas(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
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

    public Faturas getFatId() {
        return fatId;
    }

    public void setFatId(Faturas fatId) {
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
        if (!(object instanceof Linhasdefaturas)) {
            return false;
        }
        Linhasdefaturas other = (Linhasdefaturas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mobiweb.entities.Linhasdefaturas[ id=" + id + " ]";
    }
    
}
