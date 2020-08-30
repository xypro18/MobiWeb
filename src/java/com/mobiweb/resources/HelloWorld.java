package com.mobiweb.resources;

import com.mobiweb.entities.Categoria;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
 
/**
 * Root resource (exposed at "helloworld" path)
 */
@Path("helloworld")
public class HelloWorld {
    @Context
    private UriInfo context;
 
    /** Creates a new instance of HelloWorld */
    public HelloWorld() {
    }
 
    /**
     * Retrieves representation of an instance of helloWorld.HelloWorld
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getHtml() {
        return "<html lang=\"en\"><body><h1>Hello, World!! Yesss Final!!</h1></body></html>";
    }
    
    @Path("categoria")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Categoria getCategoria() {
        System.out.println("getCategoria invoked");
        Categoria categoria = new Categoria();
        categoria.setId(2);
        categoria.setName("Bancos");
        return categoria;
    }
}