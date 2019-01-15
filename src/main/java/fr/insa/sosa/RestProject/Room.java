package fr.insa.sosa.RestProject;

import java.util.ArrayList;
import java.util.List;;

public class Room {
	
	List<Actuator> actuators;
	
	public Room() {
		// list of the acturators
		actuators = new ArrayList<Actuator>();
		
		// lights in the room
		Light light1 = new Light("light1");
		Light light2= new Light("light2");
		Light light3 = new Light("light3");
		Light light4 = new Light("light4");
		
		// add actuators to the list
		actuators.add(light1);
		actuators.add(light2);
		actuators.add(light3);
		actuators.add(light4);
	}
	// get the list of actuators in the room
	public List<Actuator> getListAct () {
		return this.actuators;
	}
	//get actuator by id
	public Actuator getAct(String id) {
		Actuator aux = null;
		for(Actuator act: actuators) {
			if (act.getId()==id) {aux = act;}
		}
		return aux;
	}
}
