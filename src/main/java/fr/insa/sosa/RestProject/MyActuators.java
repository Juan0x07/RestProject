package fr.insa.sosa.RestProject;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("actuatorservices")
public class MyActuators {
	Room room = new Room();
	private static final String SUCCESS_RESULT="success";
	private static final String FAILURE_RESULT="failure";
	// get list of id of actuators
	// http://localhost:8888/RestProject/webapi/actuatorservices/myactuators
	/*@GET
	@Path("/myactuators")
    @Produces(MediaType.TEXT_PLAIN)
	public List<String> getAcuators() {
		return room.getListAct(); 
    }*/
	
	// get actuator with id
	// http://localhost:8888/RestProject/webapi/actuatorservices/myactuators/id
	@GET
	@Path("/myactuators/{id}")
    @Produces(MediaType.TEXT_PLAIN)
	public boolean getState(@PathParam("id")String id) {
		// get state of the actuator
		
		return room.getAct(id).getState(); 
    }
	
	// update actuator with id
	// http://localhost:8888/RestProject/webapi/actuatorservices/myactuators/id
	@PUT
	@Path("/myactuators/{id}")
    @Produces(MediaType.TEXT_PLAIN)
	public String changeState(@PathParam("id")String id) {
		// change state of the actuator
		room.getAct(id).changeState();  
		return SUCCESS_RESULT;
    }
		
	// insert actuator with id
	// http://localhost:8888/RestProject/webapi/actuatorservices/myactuators/id
	@POST
	@Path("/myactuators/{id}")
    @Produces(MediaType.TEXT_PLAIN)
	public String insertAct(@PathParam("id")String id) {
		// add new actuator with id
		//room.getAct(id).changeState();  
		return SUCCESS_RESULT;
    }
	
	//delete actuator with id
	// http://localhost:8888/RestProject/webapi/actuatorservices/myactuators/id
	@DELETE
	@Path("/myactuators/{id}")
    @Produces(MediaType.TEXT_PLAIN)
	public String deleteAct(@PathParam("id")String id) {
		// get state of the actuator
		room.deleteAct(id);  
		return SUCCESS_RESULT;
    }
	
}
