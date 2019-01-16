package fr.insa.sosa.RestProject;

import java.io.IOException;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.om2m.commons.resource.AE;

import fr.insat.om2m.tp2.mapper.Mapper;
import fr.insat.om2m.tp2.mapper.MapperInterface;
import httpRequest.HttpRequest;

@Path("myressources")
public class myRessources {
	// mapper for creation of ressource
		MapperInterface mapper = new Mapper();
		
	// get ressources
	// get base
	@GET
	@Path("/in-cse")
	@Produces(MediaType.APPLICATION_XML)
	public String getRes1() throws IOException{
		HttpRequest req = new HttpRequest();	
		return req.getReq("http://127.0.0.1:8080/~/in-cse");
	}
	// get AE application
	@GET
	@Path("/in-cse/{id}") //AE - application services
	@Produces(MediaType.APPLICATION_XML)
	public String getRes2(@PathParam("id")String id) throws IOException{
		HttpRequest req = new HttpRequest();	
		return req.getReq("http://127.0.0.1:8080/~/in-cse?fu=1&api="+id);
	}
	
	// add ressources
	// add AE application
	@POST
	@Path("/in-cse/{id}")
	@Produces(MediaType.APPLICATION_XML)
	public String createAE(@PathParam("id")String id) throws IOException{
		// create ne ae with mapper
		AE ae = new AE();
		ae.setAppID(id);
		ae.setRequestReachability(true);
		String representation = mapper.marshal(ae);
		// send request
		HttpRequest req = new HttpRequest();	
		return req.postReq("http://127.0.0.1:8080/~/in-cse/","2", representation);
	}
	
}
