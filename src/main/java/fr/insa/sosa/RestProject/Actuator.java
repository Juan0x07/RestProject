package fr.insa.sosa.RestProject;


public class Actuator extends Device{
	private boolean state; // actuator state : false & true
	
	public Actuator(String id, String type){
		super();
		this.setId(id);
		this.setType(type);
		this.state = false;
	}
	
	public boolean getState() {
		return state;
	}
	public void setState(boolean state) {
		this.state = state;
	}
	public void changeState() {
		this.setState(!this.getState());
	}
	
}
