package fr.insa.sosa.RestProject;

import java.util.ArrayList;

public class Actuator {
	private String id;
	private String type;
	private String position;
	private boolean state;
	private ArrayList <Link> links=new ArrayList<Link>();
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
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
	public void addLink(String uri, String rel, String methode) {
		Link newLink = new Link();
		newLink.setMethode("Get");
		newLink.setRel(rel);
		links.add(newLink);
	}
	public ArrayList<Link> getLinks() {
		return links;
	}
}
