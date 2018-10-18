package Persistence;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "door")
public class DoorComponent {
	

	int id; 
	String direction;
	boolean locked;
	
	
	public DoorComponent(int id, String direction, boolean locked) {
		this.id = id;
		this.direction = direction;
		this.locked = locked;
	}
	
	public DoorComponent(){	
	}
	
	public int getId() {
		return id;
	}
	@XmlAttribute
	public void setId(int id) {
		this.id = id;
	}
	public String getDoor() {
		return direction;
	}
	@XmlAttribute
	public void setDoor(String doors) {
		this.direction = doors;
	}
	public boolean isLocked() {
		return locked;
	}
	@XmlAttribute
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
}
