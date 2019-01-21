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
import org.eclipse.om2m.commons.resource.Container;
import org.eclipse.om2m.commons.resource.ContentInstance;

import fr.insat.om2m.tp2.mapper.Mapper;
import fr.insat.om2m.tp2.mapper.MapperInterface;
import httpRequest.HttpRequest;

@Path("myresources")
public class myResources {
	// mapper for creation of resource
	private	MapperInterface mapper = new Mapper();
			
	// get data 
	@GET
	@Path("/{type}/{id}")
	@Produces(MediaType.APPLICATION_XML)
	public String getCntData(@PathParam("id")String id) throws IOException{
		// send request
		HttpRequest req = new HttpRequest();	
		String representation = req.getReq(
				"http://127.0.0.1:8080/~/in-cse/in-name/"+id+"/DATA/la");
		ContentInstance cin = (ContentInstance) mapper.unmarshal(representation);
		return cin.getContent();
	}
	
	// delete data
	@DELETE
	@Path("/{type}/{id}/{data}")
	@Produces(MediaType.APPLICATION_XML)
	public String deleteCntData(@PathParam("id")String id,
								@PathParam("data")String data) throws IOException{			
		HttpRequest req = new HttpRequest();	
		return req.deleteReq("http://127.0.0.1:8080/~/in-cse/in-name/"+id+"/"+data+"/ol");
	}

	// change light and alarm and heating state
	@POST
	@Path("/{type:light|alarm|heating}/{id}/{data}")
	@Produces(MediaType.APPLICATION_XML)
	public String changeOnOff(@PathParam("id")String id, @PathParam("data")String data) throws IOException{
		// create new cin with mapper
		if (data.equals("on") || data.equals("off")){
			ContentInstance cin = new ContentInstance();
			cin.setContent(data);
			String representation = mapper.marshal(cin);
			// send request
			HttpRequest req = new HttpRequest();	
			return req.postReq("http://127.0.0.1:8080/~/in-cse/in-name/"+id+"/DATA","4", representation);
		}
		return "bad parameter";
	}
		
	// change window and door state
	@POST
	@Path("/{type:window|door}/{id}/{data}")
	@Produces(MediaType.APPLICATION_XML)
	public String changeWindow(@PathParam("id")String id, @PathParam("data")String data) throws IOException{
		// create new cin with mapper
		if (data.equals("open") || data.equals("closed")){
			ContentInstance cin = new ContentInstance();
			cin.setContent(data);
			String representation = mapper.marshal(cin);
			// send request
			HttpRequest req = new HttpRequest();	
			return req.postReq("http://127.0.0.1:8080/~/in-cse/in-name/"+id+"/DATA","4", representation);
		}
		return "bad parameter";
	}
	
	
}
