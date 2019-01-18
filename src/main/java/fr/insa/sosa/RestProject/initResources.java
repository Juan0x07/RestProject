package fr.insa.sosa.RestProject;

import java.io.IOException;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.om2m.commons.resource.AE;
import org.eclipse.om2m.commons.resource.Container;
import org.eclipse.om2m.commons.resource.ContentInstance;

import fr.insat.om2m.tp2.mapper.Mapper;
import fr.insat.om2m.tp2.mapper.MapperInterface;
import httpRequest.HttpRequest;

@Path("init")
public class initResources {
	// mapper for creation of resource
	private	MapperInterface mapper = new Mapper();
	
	// add resources
	// add AE and CNT data
	@POST
	@Path("/{type}/{id}")
	@Produces(MediaType.APPLICATION_XML)
	public String createAE(@PathParam("id")String id) throws IOException{
		// create new ae with mapper
		AE ae = new AE();
		ae.setName(id);
		ae.setAppID(id);
		ae.setRequestReachability(true);
		String representation = mapper.marshal(ae);
		// send request
		HttpRequest req = new HttpRequest();	
		req.postReq("http://127.0.0.1:8080/~/mn-cse","2", representation);
		// create new cnt with mapper
		Container cnt = new Container();
		cnt.setName("DATA");
		representation = mapper.marshal(cnt);
		// send request	
		return req.postReq("http://127.0.0.1:8080/~/mn-cse/mn-name/"+id,"3", representation);
	}
	
	// delete resources
	// delete AE 
	@DELETE
	@Path("/{type}/{id}")
	@Produces(MediaType.APPLICATION_XML)
	public String deleteAE(@PathParam("id")String id) throws IOException{
		// send request
		HttpRequest req = new HttpRequest();	
		return req.deleteReq("http://127.0.0.1:8080/~/mn-cse/mn-name/"+id);
	}

}
