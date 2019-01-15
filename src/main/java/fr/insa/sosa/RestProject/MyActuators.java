package fr.insa.sosa.RestProject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("myactuators/{id}")
public class MyActuators {
	Room room = new Room();
	
	@GET
    @Produces(MediaType.TEXT_PLAIN)
	public boolean getState(@PathParam("id")String id) {
		// get state of the actuator
		return room.getAct(id).getState();  
		//return false;
    }
	
}
