package Persistence;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class Dom_Parser {



	public static void main(String[] args) {
		
		
		setup();
		
	}
	public static void setup() {
		
		//makes a list which contains all the rooms
		List<RoomComponent> rooms = new ArrayList<>();
		
		//creates a room and sets all the details for it to be created into XML format.
		RoomComponent room  = new RoomComponent();
		room.setId(0);
		room.setLocX(0);
		room.setLocY(0);
		room.setHasPlayer(true);
		
		//sets the walls for the wall arraylist to be added to the specific room.
		List<Integer> walls = new ArrayList<>();
		//could add a walls component
		walls.add(1);
		walls.add(2);
		walls.add(3);
		
		List<ItemComponent> items = new ArrayList<>();
		//makes an item with an x and y position and a string of what the item is.
		items.add(new ItemComponent(0, 0, "Key"));
		
		//adds the walls to the room
		room.setWalls(walls);
		//adds the items to the room
		room.setItems(items);
		
		rooms.add(room);
		xmlRooms(room);
		
		//second room example // whats a better way of doing this? 
		//making a make a room method with the values needed ?
		RoomComponent room2  = new RoomComponent();
		room2.setId(1);
		room2.setLocX(0);
		room2.setLocY(0);
		room2.setHasPlayer(true);
		List<Integer> walls2 = new ArrayList<>();
		walls2.add(1);
		walls2.add(2);
		walls2.add(3);
		List<ItemComponent> items2 = new ArrayList<>();
		items2.add(new ItemComponent(0, 0, "null"));
		room2.setWalls(walls);
		room2.setItems(items);
		
		rooms.add(room2);
		//by doing this multiple times seems to create multiple xml files? 
		xmlRooms(room2);
	}
	
	
	
	
	public static void xmlRooms(RoomComponent room) {
		try {
			File file = new File("file.xml");
			//need to change this to take in an arraylist of objects so it adds them into one file.
			JAXBContext jaxbContext = JAXBContext.newInstance(RoomComponent.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			
			//output printed pretty
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			
			jaxbMarshaller.marshal(room, file);
			jaxbMarshaller.marshal(room, System.out);
			
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			RoomComponent loadRoom = (RoomComponent) jaxbUnmarshaller.unmarshal(file);
			System.out.println("id: " + loadRoom.getId() + " location:(" + loadRoom.getLocX() + "," + loadRoom.getLocY()
			+ ") Player: " + loadRoom.getHasPlayer());
			//example of code to start the connection between persistance and gameworld
			if(loadRoom.getHasPlayer() == true) {
				
			}
			
		 } catch (JAXBException e) {
				e.printStackTrace();
		 	}
	}
}