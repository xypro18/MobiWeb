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
    , @NamedQuery(name = "Linhasdefatura.findByLine", query = "SELECT l FROM Linhasdefatura l WHERE l.line = :line")
    , @NamedQuery(name = "Linhasdefatura.findByValue", query = "SELECT l FROM Linhasdefatura l WHERE l.value = :value")
    , @NamedQuery(name = "Linhasdefatura.findByFatId", query = "SELECT l FROM Linhasdefatura l WHERE l.fatId.id = :fatId")
    , @NamedQuery(name = "Linhasdefatura.findByCreated", query = "SELECT l FROM Linhasdefatura l WHERE l.created = :created")
    , @NamedQuery(name = "Linhasdefatura.findByModified", query = "SELECT l FROM Linhasdefatura l WHERE l.modified = :modified")})
public class Linhasdefatura implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 30)
    @Column(name = "LINE")
    private String line;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VALUE")
    private double value;
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

    public Linhasdefatura(Integer id, int value) {
        this.id = id;
        this.value = value;
    }

    public Linhasdefatura(String line, double value, Fatura fat) {
        this.line = line;
        this.value = value;
        this.fatId = fat;
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

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
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

}
