package fr.insa.sosa.RestProject;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;;

public class Room {
	List<Actuator> actList;
	
	
	// get list of id of actuators
	public List<String> getListAct () {
		List <String> l = null;
		for(Actuator act: this.actList) {
			l.add(act.getId());
		}
		return l;
	}
	
	
	// get actuator by id
	public Actuator getAct(String id) {
		List<Actuator> actList = getAllAct();
		for (Actuator act: actList) {
			if (act.getId().equals(id)) {return act;}
		}
		return null;
	}
	
	// update actuator with id with indicator
	public int updateAct(Actuator newAct) {
		if (!this.actList.isEmpty()) {// list not null
			for (Actuator act: this.actList) {	
				if (act.getId().equals(newAct.getId())) {
					// replace the origin actuator in the list using index
					this.actList.set(this.actList.indexOf(act), newAct);
					return 1;// success
					}
				}
		}
		return 0;// fail
	}
	
	// insert actuator with id with indicator
	public int insertAct(Actuator newAct) {
		if (!this.actList.isEmpty()) {// list not null
			boolean exist = false;
			for (Actuator act: this.actList) {
				// verify if the actuator already exists with the same id
				if (act.getId().equals(newAct.getId())) {
					exist = true;
					return 0; // fail
					}// if exist
				}// for in actList
			if (!exist) {
				this.actList.add(newAct);
				return 1;// success
			}
		}
		return 0;// fail
	}
	
	// delete actuator by id with indicator
	public int deleteAct(String id) {
		if (!this.actList.isEmpty()) {// list not null
	      for (Actuator act: this.actList) { 
	         if (act.getId().equals(id)){// find with id
	            int index = actList.indexOf(act);			
	            this.actList.remove(index);
	            return 1;// success  
	         }
	      }
		}
	      return 0;// fail
	}

	// create a file where data can be modified
	// for POST, PUT, DELETE
	 public List<Actuator> getAllAct(){ 
          // if the list is null
         if (!this.actList.isEmpty()) {
        	// create the list with our own data
        	// lights in the room
     		Light light1 = new Light("light1");
     		Light light2= new Light("light2");
     		Light light3 = new Light("light3");
     		Light light4 = new Light("light4");    		
     		// add actuators to the list
     		this.actList.add(light1);
     		this.actList.add(light2);
     		this.actList.add(light3);
     		this.actList.add(light4);
         }// if list is null
         return this.actList;
      }	
	   


}

