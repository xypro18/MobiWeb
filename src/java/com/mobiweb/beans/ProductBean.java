package com.mobiweb.beans;

import com.mobiweb.dao.GenericJpaDao;
import com.mobiweb.entities.Categoria;
import com.mobiweb.entities.Produto;
import com.mobiweb.entities.Subcategoria;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;

//Controlador dos produtos
@Named("product")
@SessionScoped
public class ProductBean implements Serializable {

    private int idCat;
    private int idSub;
    private String strCat;
    private String strSub;
    private String strProd;
    private double price;
    private List<Categoria> lcat = null;
    private List<Subcategoria> lsub = null;
    private List<Produto> lprod = null;

    //Objeto DAO que faz persistência e leitura da base de dados
    @Inject
    GenericJpaDao dao;
    
    //Injeta o bean do perfil para associar o utilizador ao produto
    @Inject
    ProfileBean profile;

    //Gera categorias após construção de bean, as subcategorias e produtos só 
    //aparecem após selecções sucessivas
    @PostConstruct
    public void init() {
        generateCategories();
    }
    
    //Adiciona Categoria, caso já exista (case insensitive) envia mensagem de erro 
   public void addCategory() {
        if (dao.hasName(Categoria.class, strCat)) {
            produceGrowlError("record_exists");
        } else {
            RequestContext.getCurrentInstance().execute("PF('dlg_cat').hide()");
            Categoria cat = new Categoria(strCat);
            dao.save(cat);
            generateCategories();
            idCat = cat.getId();
            strCat = "";
            generateSubcategories();
            onCategoryChange();
        }
    }

   //Adiciona Subcategoria, caso já exista (case insensitive) envia mensagem de erro 
    public void addSubCategory() {
        if (dao.hasName(Subcategoria.class, strSub, idCat)) {
            produceGrowlError("record_exists");
        } else {
            RequestContext.getCurrentInstance().execute("PF('dlg_sub').hide()");
            Subcategoria sub = new Subcategoria(strSub, getCategory());
            dao.save(sub);
            generateSubcategories();
            idSub = sub.getId();
            strSub = "";
            onSubCategoryChange();
        }
    }

    //Adiciona Produto, caso já exista (case insensitive) envia mensagem de erro 
    public void addProduct() {
        if (dao.hasName(Produto.class, strProd, idSub)) {
            produceGrowlError("record_exists");
        } else {
            RequestContext.getCurrentInstance().execute("PF('dlg_prod').hide()");
            Produto p = new Produto(strProd, price, getSubcategory(), profile.getEmp());
            dao.save(p);
            generateProducts();
            strProd = "";
            price = 0.0;
        }
    }

    //Altera Categoria, caso já exista (case insensitive) envia mensagem de erro 
    public void changeCategory() {
        if (dao.hasName(Categoria.class, strCat)) {
            produceGrowlError("record_exists");
        } else {
            RequestContext.getCurrentInstance().execute("PF('dlg_change_cat').hide()");
            Categoria cat = getCategory();
            cat.setName(strCat);
            dao.update(cat);
            strCat = "";
            generateCategories();
            generateSubcategories();
            generateProducts();
        }
    }

    //Apaga Categoria
    public void deleteCategory() {
        dao.deleteById(Categoria.class, idCat);
        generateCategories();
        idCat = 0;
        onCategoryChange();
    }

    //Altera Subcategoria, caso já exista (case insensitive) envia mensagem de erro 
    public void changeSubcategory() {
        if (dao.hasName(Subcategoria.class, strSub, idCat)) {
            produceGrowlError("record_exists");
        } else {
            RequestContext.getCurrentInstance().execute("PF('dlg_change_sub').hide()");
            Subcategoria sub = getSubcategory();
            sub.setName(strSub);
            dao.update(sub);
            strSub = "";
            generateSubcategories();
            generateProducts();
        }
    }

    //Apaga Subcategoria
    public void deleteSubcategory() {
        dao.deleteById(Subcategoria.class, idSub);
        generateSubcategories();
        idSub = 0;
        onSubCategoryChange();
    }

    //Leitura de todas as Categorias na base de dados
    private void generateCategories() {
        lcat = dao.findAll(Categoria.class);
    }
    
    //Leitura de todas as Subcategorias associadas a uma Categoria na base de dados
    private void generateSubcategories() {
        lsub = dao.findByCatId(Subcategoria.class, idCat);
    }
    
    //Leitura de todos os Produtos associados a uma Subcategoria na base de dados
    private void generateProducts() {
        lprod = dao.findBySubcatId(Produto.class, idSub);
    }
    
    //Atualiza Subcategoria se Categoria muda, método associado à View
    public void onCategoryChange() {
        if (idCat != 0) {
            generateSubcategories();
        }
        idSub = 0;
    }

    //Atualiza Produto se Subcategoria muda, método associado à View
    public void onSubCategoryChange() {
        if (idSub != 0) {
            generateProducts();
        }
    }

    //Obtém Categoria escolhida acedendo à base de dados 
    private Categoria getCategory() {
        return (Categoria) dao.findOne(Categoria.class, idCat);
    }

    //Obtém Subcategoria escolhida acedendo à base de dados 
    private Subcategoria getSubcategory() {
        return (Subcategoria) dao.findOne(Subcategoria.class, idSub);
    }

    //Método que edita tabela de Produtos, não permite repetição de nomes (case insensitive)
    public void onRowEdit(RowEditEvent event) {
        Produto p = (Produto) event.getObject();
        if (dao.hasName(Produto.class, p.getName(), idSub)) {
            produceGrowlError("record_exists");
            generateProducts();
        } else {
            dao.update(p);
            produceGrowlInfo("change_success");
        }
    }

    //Método que cancela edição da tabela de linhas de fatura
    public void onRowCancel(RowEditEvent event) {
        produceGrowlInfo("edit_cancel");
    }

    //Elemina produto e atualiza lista de produtos 
    public void removeProduct(int id) {
        dao.deleteById(Produto.class, id);
        generateProducts();
    }

    //TODO: Ver necessidade do método
    //Método associado a view da listagem global, que resulta seleção e geração
    //das listas de Categorias, Subcategorias e Produtos seleccionados
    public String acceptCategory() {
        generateCategories();
        generateSubcategories();
        generateProducts();
        return "product";
    }

    //Metodo que produz mensagens de informação
    private void produceGrowlInfo(String msg) {
        ResourceBundle rb = ResourceBundle.getBundle("com.mobiweb.resources.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, rb.getString("info"), rb.getString(msg)));
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
    public String getStrProd() {
        return strProd;
    }

    public void setStrProd(String strProd) {
        this.strProd = strProd;
    }

    public List<Produto> getLprod() {
        return lprod;
    }

    public void setLprod(List<Produto> lprod) {
        this.lprod = lprod;
    }

    public int getIdCat() {
        return idCat;
    }

    public void setIdCat(int idCat) {
        this.idCat = idCat;
    }

    public int getIdSub() {
        return idSub;
    }

    public void setIdSub(int idSub) {
        this.idSub = idSub;
    }

    public List<Categoria> getLcat() {
        return lcat;
    }

    public void setLcat(List<Categoria> lcat) {
        this.lcat = lcat;
    }

    public List<Subcategoria> getLsub() {
        return lsub;
    }

    public void setLsub(List<Subcategoria> lsub) {
        this.lsub = lsub;
    }

    public String getStrSub() {
        return strSub;
    }

    public void setStrSub(String strSub) {
        this.strSub = strSub;
    }

    public String getStrCat() {
        return strCat;
    }

    public void setStrCat(String strCat) {
        this.strCat = strCat;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }   

}
