package fr.insa.sosa.RestProject;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.om2m.commons.resource.AE;
import org.eclipse.om2m.commons.resource.ContentInstance;

import fr.insat.om2m.tp2.mapper.Mapper;
import fr.insat.om2m.tp2.mapper.MapperInterface;
import httpRequest.HttpRequest;

@Path("lightservice")
public class lightResources {
	// mapper for creation of resource
	private	MapperInterface mapper = new Mapper();
	// Resource ID
	private String riLight1 = "/mn-cse/cnt-856960258";
	private String riLight2 = "/mn-cse/cnt-237724306";
	private String riLight3 = "/mn-cse/CAE92770507";
	private String riLight4 = "/mn-cse/CAE92770507";
	
	// list of the lights
	
	// change light state by id
	
	// add Light
		@POST
		@Path("/lights/{id}")
		@Produces(MediaType.APPLICATION_XML)
		public String createLight(@PathParam("id")String id) throws IOException{
			// create new ae with mapper
			AE ae = new AE();
			//ae.setAEID(id);
			ae.setName(id);
			ae.setAppID(id);
			ae.setRequestReachability(true);
			String representation = mapper.marshal(ae);
			// send request
			HttpRequest req = new HttpRequest();	
			return req.postReq("http://127.0.0.1:8080/~/mn-cse","2", representation);
		}
		
	
	// add devices to containers
		@POST
		@Path("/Lights/{id}")
		@Produces(MediaType.APPLICATION_XML)
		public String createDevice(@PathParam("id")String id) throws IOException{
			// create new device with mapper
			ContentInstance cin = new ContentInstance();
			
			String representation = mapper.marshal(cin);
			// send request
			HttpRequest req = new HttpRequest();	
			return req.postReq("http://127.0.0.1:8080/~","2", representation);
		}
		
	// 
}
