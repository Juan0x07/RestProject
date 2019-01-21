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
import org.eclipse.om2m.commons.resource.Subscription;

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
	public String createAE( @PathParam("type")String type,
							@PathParam("id")String id) throws IOException{	
		if (type.equals("sensor") || type.equals("actuator")){
			// create new ae with mapper
			AE ae = new AE();
			ae.setName(id);
			ae.setAppID(id);
			ae.setRequestReachability(true);
			String representation = mapper.marshal(ae);
			// send request
			HttpRequest req = new HttpRequest();	
			req.postReq("http://127.0.0.1:8080/~/in-cse","2", representation);
			// create new cnt-DATA with mapper
			Container cnt = new Container();
			cnt.setName("DATA");
			representation = mapper.marshal(cnt);
			// send request	
			req.postReq("http://127.0.0.1:8080/~/in-cse/in-name/"+id,"3", representation);
			// create new cnt-TYPE with mapper
			cnt.setName("TYPE");
			representation = mapper.marshal(cnt);
			// send request	
			req.postReq("http://127.0.0.1:8080/~/in-cse/in-name/"+id,"3", representation);
			// create new cin with mapper
			ContentInstance cin = new ContentInstance();
			cin.setContent(type);
			representation = mapper.marshal(cin);
			// send request	
			req.postReq("http://127.0.0.1:8080/~/in-cse/in-name/"+id+"/TYPE","4", representation);
			if (type.equals("sensor")){
				/*Subscription sub = new Subscription();
				sub.setName("SUBSCRIPTION");
				sub.setSubscriberURI("http://localhost:1400/monitor");
				representation = mapper.marshal(sub);
				req.postReq("http://127.0.0.1:8080/~/in-cse/in-name/"+id+"/DATA","23", representation);
				*/return "sensor added";
			}
			return "actuator added";
		}
		return "bad type";
}
	
	// delete resources
	// delete AE 
	@DELETE
	@Path("/{type}/{id}")
	@Produces(MediaType.APPLICATION_XML)
	public String deleteAE(@PathParam("id")String id) throws IOException{
		// send request
		HttpRequest req = new HttpRequest();	
		return req.deleteReq("http://127.0.0.1:8080/~/in-cse/in-name/"+id);
	}
	
	// change sensor data
	// "only for simulation"
	@POST
	@Path("addData/{id}/{data}")
	@Produces(MediaType.APPLICATION_XML)
	public String changeOnOff(@PathParam("id")String id, @PathParam("data")String data) throws IOException{
	// create new cin with mapper
		ContentInstance cin = new ContentInstance();
		cin.setContent(data);
		String representation = mapper.marshal(cin);
		// send request
		HttpRequest req = new HttpRequest();	
		return req.postReq("http://127.0.0.1:8080/~/in-cse/in-name/"+id+"/DATA","4", representation);
	}

}
