package fr.insa.sosa.RestProject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;;

public class RoomOld {
	
	//List<Actuator> actuators;
	
	//public Room() {}
		/*// list of the acturators
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
	}*/
	
	// get list of id of actuators
	/*public List<String> getListAct () {
		List <String> l = null;
		for(Actuatoract: this.actuators) {
			l.add(act.getId());
		}
		return l;
	}*/
	
	
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
		List<Actuator> actList = getAllAct();
		for (Actuator act: actList) {	
			if (act.getId().equals(newAct.getId())) {
				// replace the origin actuator in the list using index
				actList.set(actList.indexOf(act), newAct);
				// update and save the new list of actuators
				saveActList(actList);
				return 1;// success
				}
			}
		return 0;// fail
	}
	
	// insert actuator with id with indicator
	public int insertAct(Actuator newAct) {
		List<Actuator> actList = getAllAct();
		boolean exist = false;
		for (Actuator act: actList) {
			// verify if the actuator already exists with the same id
			if (act.getId().equals(newAct.getId())) {
				exist = true;
				return 0; // fail
				}// if exist
			}// for in actList
		if (!exist) {
			actList.add(newAct);
			return 1;// success
		}
		return 0;// fail
	}
	
	// delete actuator by id with indicator
	public int deleteAct(String id) {
		List<Actuator> actList = getAllAct();
	      for (Actuator act: actList) { 
	         if (act.getId().equals(id)){
	            int index = actList.indexOf(act);			
	            actList.remove(index);
	            saveActList(actList);
	            return 1;// success  
	         }
	      }		
	      return 0;// fail
	}

	// create a file where data can be modified
	// for POST, PUT, DELETE
	 public List<Actuator> getAllAct(){
	      List<Actuator> actList = null;
	      try {	    
	    	  // find the file where stores the data of actuators
	         File file = new File("Actuators.dat");
	          // if the file is not created
	         if (!file.exists()) {
	        	// create the file with our own data
	        	// lights in the room
	     		Light light1 = new Light("light1");
	     		Light light2= new Light("light2");
	     		Light light3 = new Light("light3");
	     		Light light4 = new Light("light4");
	     		
	     		// create new list of actuators
	     		actList = new ArrayList<Actuator>();
	     		// add actuators to the list
	     		actList.add(light1);
	     		actList.add(light2);
	     		actList.add(light3);
	     		actList.add(light4);
	            saveActList(actList);		
	         }
	         else{
	        	 // read the file
	            FileInputStream fis = new FileInputStream(file);
	            ObjectInputStream ois = new ObjectInputStream(fis);
	            actList = (List<Actuator>) ois.readObject();
	            ois.close();
	         }
	      } catch (IOException e) {
	         e.printStackTrace();
	      } catch (ClassNotFoundException e) {
	         e.printStackTrace();
	      }		
	      return actList;
	   }

	 // update and save the file
	public void saveActList(List<Actuator> actList) {
		try {
	         File file = new File("Actuators.dat");
	         FileOutputStream fos;
	         fos = new FileOutputStream(file,true);
	         // write in file
	         ObjectOutputStream oos = new ObjectOutputStream(fos);		
	         oos.writeObject(actList);
	         oos.close();
	      } catch (FileNotFoundException e) {
	         e.printStackTrace();
	      } catch (IOException e) {
	         e.printStackTrace();
	      }
		
	}

}
