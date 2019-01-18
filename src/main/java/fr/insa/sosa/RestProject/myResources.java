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
	// Resource ID
	private String riRoom = "/mn-cse/CAE405435110";
	private String riLight1 = "/mn-cse/CAE700878880";
	private String riLight1data = "/mn-cse/cnt-626482413";
	private String riLight1datastate = "/mn-cse/cin-563855193";
	private String riLight2 = "/mn-cse/CAE461795889";
	private String riLight2data = "/mn-cse/cnt-996746539";
	private String riLight3 = "/mn-cse/CAE265107620";
	// store the OM2M id of the resource (to do)
	
		
	// get resources
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
	@Path("/services/{id}") //AE - application services
	@Produces(MediaType.APPLICATION_XML)
	public String getRes2(@PathParam("id")String id) throws IOException{
		HttpRequest req = new HttpRequest();	
		return req.getReq("http://127.0.0.1:8080/~/mn-cse?fu=1&api="+id);
	}
	
	// add resources
	// add AE 
	@POST
	@Path("/services/{id}")
	@Produces(MediaType.APPLICATION_XML)
	public String createAE(@PathParam("id")String id) throws IOException{
		// create new ae with mapper
		AE ae = new AE();
		//ae.setAEID(id);
		//ae.setResourceID(id);
		ae.setName(id);
		ae.setAppID(id);
		ae.setRequestReachability(true);
		String representation = mapper.marshal(ae);
		// send request
		HttpRequest req = new HttpRequest();	
		return req.postReq("http://127.0.0.1:8080/~/mn-cse","2", representation);
	}

	// add CNT data
	@POST
	@Path("/services/Light/data")
	@Produces(MediaType.APPLICATION_XML)
	public String createCntData() throws IOException{
		// create new cnt with mapper
		Container cnt = new Container();
		cnt.setName("DATA");
		String representation = mapper.marshal(cnt);
		// send request
		HttpRequest req = new HttpRequest();	
		return req.postReq("http://127.0.0.1:8080/~"+riLight2,"3", representation);
	}
	
	// add cin data
			@POST
			@Path("/services/Light/data/state/{state}")
			@Produces(MediaType.APPLICATION_XML)
			public String createCinData(@PathParam("state")String state) throws IOException{			
				// create new cin with mapper
				ContentInstance cin = new ContentInstance();
				cin.setContent(state);
				String representation = mapper.marshal(cin);
				// send request
				HttpRequest req = new HttpRequest();	
				return req.postReq("http://127.0.0.1:8080/~"+riLight1data,"4", representation);
			}

		
	// get state of light
		@GET
		@Path("/services/Light/data/state")
		@Produces(MediaType.APPLICATION_XML)
		public String getCinData() throws IOException{
			// send request
			HttpRequest req = new HttpRequest();	
			String representation= req.getReq("http://127.0.0.1:8080/~/mn-cse/mn-name/Light1/DATA/la");
			ContentInstance cin = (ContentInstance) mapper.unmarshal(representation);				
			return cin.getContent();
		}
	
	// turn on the light : change state of light
		@PUT
		@Path("/services/Light/data/on")
		@Produces(MediaType.APPLICATION_XML)
		public String turnOnLight() throws IOException{
			// send request to get current state of light
			HttpRequest req = new HttpRequest();	
			String representation= req.getReq("http://127.0.0.1:8080/~"+riLight1datastate);
			ContentInstance cin = (ContentInstance) mapper.unmarshal(representation);
			// turn on light
			if (!cin.getContent().equals("on")){cin.setContent("on");}
			representation = mapper.marshal(cin);
			// send put request		
			return req.putReq("http://127.0.0.1:8080/~"+riLight1datastate, representation);
		}
		// turn off the light : change state of light
			@PUT
			@Path("/services/Light/data/off")
			@Produces(MediaType.APPLICATION_XML)
			public String turnOffLight() throws IOException{
				// send request to get current state of light
				HttpRequest req = new HttpRequest();	
				String representation= req.getReq("http://127.0.0.1:8080/~"+riLight1datastate);
				ContentInstance cin = (ContentInstance) mapper.unmarshal(representation);
				// turn on light
				if (!cin.getContent().equals("off")){cin.setContent("off");}
				representation = mapper.marshal(cin);
				// send put request		
				return "hi";//req.putReq("http://127.0.0.1:8080/~"+riLight1datastate,"4", representation);
			}
	// delete resources
	// delete AE 
	@DELETE
	@Path("/services/{id}")
	@Produces(MediaType.APPLICATION_XML)
	public String deleteAE(@PathParam("id")String id) throws IOException{
		// send request
		HttpRequest req = new HttpRequest();	
		return req.deleteReq("http://127.0.0.1:8080/~/mn-cse/id");
	}
}
