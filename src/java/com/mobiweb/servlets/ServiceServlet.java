/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobiweb.servlets;

import com.mobiweb.ejbs.ShopService;
import com.mobiweb.entities.Produto;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author cristianor
 */
@WebServlet(name = "Service", urlPatterns = {"/service"})
public class ServiceServlet extends HttpServlet {
    
    @Inject ShopService shopService;
    
    @PersistenceContext(unitName = "MobiWebPU")
    private EntityManager em;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        out.println("Service method called");
        shopService.Test();
//        Query query = em.createQuery("SELECT p.name, p.price FROM Produto p WHERE p.price BETWEEN 500 AND 750"); // C
//        Query query = em.createQuery("SELECT p.name, p.price FROM Produto p WHERE MIN(price)>= 500 AND MAX(price)<= 750"); // A
//        Query query = em.createQuery("SELECT p.name, p.price FROM Produto p WHERE NOT (p.price<= 500 OR p.price>= 750)"); //B
        Query query = em.createQuery("SELECT p.name, p.price FROM Produto p WHERE p.price>= 500 AND p.price<= 750"); //D
        List<Object[]> results = query.getResultList();
        for (Object[] entry : results)
            out.println(Arrays.deepToString(entry));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        out.print("GET method called");
    }

}
