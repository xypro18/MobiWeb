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
@Table(name = "EMPREGADO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Empregado.findAll", query = "SELECT e FROM Empregado e")
    , @NamedQuery(name = "Empregado.findById", query = "SELECT e FROM Empregado e WHERE e.id = :id")
    , @NamedQuery(name = "Empregado.findByFirstname", query = "SELECT e FROM Empregado e WHERE e.firstname = :firstname")
    , @NamedQuery(name = "Empregado.findByLastname", query = "SELECT e FROM Empregado e WHERE e.lastname = :lastname")
    , @NamedQuery(name = "Empregado.findByUsername", query = "SELECT e FROM Empregado e WHERE e.username = :username")
    , @NamedQuery(name = "Empregado.findByPassword", query = "SELECT e FROM Empregado e WHERE e.password = :password")
    , @NamedQuery(name = "Empregado.findBySex", query = "SELECT e FROM Empregado e WHERE e.sex = :sex")
    , @NamedQuery(name = "Empregado.findByDateofbirth", query = "SELECT e FROM Empregado e WHERE e.dateofbirth = :dateofbirth")
    , @NamedQuery(name = "Empregado.findByCreated", query = "SELECT e FROM Empregado e WHERE e.created = :created")
    , @NamedQuery(name = "Empregado.findByModified", query = "SELECT e FROM Empregado e WHERE e.modified = :modified")})
public class Empregado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "FIRSTNAME")
    private String firstname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "LASTNAME")
    private String lastname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "USERNAME")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "PASSWORD")
    private String password;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SEX")
    private Character sex;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DATEOFBIRTH")
    @Temporal(TemporalType.DATE)
    private Date dateofbirth;
    @Column(name = "CREATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Column(name = "MODIFIED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modified;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "empId")
    private Collection<Produto> produtoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "empId")
    private Collection<Faturas> faturasCollection;

    public Empregado() {
    }

    public Empregado(Integer id) {
        this.id = id;
    }

    public Empregado(Integer id, String firstname, String lastname, String username, String password, Character sex, Date dateofbirth) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.sex = sex;
        this.dateofbirth = dateofbirth;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Character getSex() {
        return sex;
    }

    public void setSex(Character sex) {
        this.sex = sex;
    }

    public Date getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(Date dateofbirth) {
        this.dateofbirth = dateofbirth;
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
    public Collection<Produto> getProdutoCollection() {
        return produtoCollection;
    }

    public void setProdutoCollection(Collection<Produto> produtoCollection) {
        this.produtoCollection = produtoCollection;
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
        if (!(object instanceof Empregado)) {
            return false;
        }
        Empregado other = (Empregado) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mobiweb.entities.Empregado[ id=" + id + " ]";
    }
    
}
