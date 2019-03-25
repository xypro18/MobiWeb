package com.mobiweb.beans;

import com.mobiweb.dao.GenericJpaDao;
import com.mobiweb.entities.Empregado;
import com.mobiweb.resources.PBKDF2;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.primefaces.context.RequestContext;

//Controlador da autenticação e gestão de Perfil
@Named("profile")
@SessionScoped
public class ProfileBean implements Serializable {

    private String username;
    private String password;
    private Empregado emp;

    //Objeto DAO que faz persistência e leitura da base de dados
    @Inject
    GenericJpaDao dao;
    
    //Reinicia campos de utilizador após construção de bean
    @PostConstruct
    public void init() {
        username = null;
        password = null;
        emp = new Empregado();
    }

    //Valida username e password 
    public String validadeUserLogin() {
        username = username.toLowerCase();
        emp = (Empregado) dao.findByUsername(Empregado.class, username);
        if (emp != null && emp.getPassword().equals(PBKDF2.getHash(password.toCharArray(), username))) {
            return "product";
        } else {
            //Mensagem de erro para user/pass invalido
            produceGrowlError("worng_pass_user");
            return null;
        }
    }

    //Regista utilizador
    public String registerUser() {
        username = emp.getUsername().toLowerCase();
        if (dao.findByUsername(Empregado.class, username) == null) {
            emp.setUsername(username);
            String hash = PBKDF2.getHash(emp.getPassword().toCharArray(), username);
            emp.setPassword(hash);
            dao.save(emp);
            return "product";
        } else {
            //Mensagem de erro quando utilizador já existe
            produceGrowlError("user_exists");
            return null;
        }
    }

    //Edita perfil do utilizador
    public String saveChanges() {
        dao.update(emp);
        produceGrowlInfo("change_success");
        return "profile";
    }
    
    //Cancela alteração do perfil do utilizador
    public String cancelChanges() {
        produceGrowlInfo("edit_cancel");
        return "profile";
    }

    //Método usado pela View para redirecionar para o registo
    public String registration() {
        init();
        return "registration";
    }

    //Método usado pela View para encerrar sessão
    public String logout() {
        init();
        return "login";
    }

    //Metodo que produz mensagens de informação
    private void produceGrowlInfo(String msg) {
        ResourceBundle rb = ResourceBundle.getBundle("com.mobiweb.resources.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, rb.getString("info"), rb.getString(msg)));
        RequestContext.getCurrentInstance().update("growl");
    }

    //Método que produz mensagens de erro
    private void produceGrowlError(String msg) {
        ResourceBundle rb = ResourceBundle.getBundle("com.mobiweb.resources.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, rb.getString("error"), rb.getString(msg)));
        RequestContext.getCurrentInstance().update("growl");
    }

    /////////////////////
    //GETTERS E SETTERS//
    /////////////////////
    public Empregado getEmp() {
        return emp;
    }

    public void setEmp(Empregado emp) {
        this.emp = emp;
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

}
