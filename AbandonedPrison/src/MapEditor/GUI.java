package MapEditor;

import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import Persistence.LoadXml;
import Persistence.ConvertMapEditor;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.xml.bind.JAXBException;

public class GUI {
	private JButton west;
	private JButton east;
	private JButton north;
	private JButton south;
	private JButton rightroom;
	private JButton leftroom;
	private JButton toproom;
	private JButton downroom;
	private JButton leftdoor;
	private JButton rightdoor;
	private JButton topdoor;
	private JButton downdoor;
	private JButton key;
	private JButton magic;
	private JButton treasure;
	private JButton wall;
	private JButton rightwall;
	private JButton topwall;
	private JButton downwall;
	private JButton light;
	private JButton clear;
	boolean roomExist=false;
	boolean rightRoomCanBuild=true;
	boolean leftRoomCanBuild=true;
	boolean topRoomCanBuild=true;
	boolean downRoomCanBuild=true;
	private ArrayList<Room> rooms=new ArrayList<Room>();//rooms list
	private ArrayList<Door> doors=new ArrayList<Door>();//doors list
	private ArrayList<Wall> walls=new ArrayList<Wall>();//walls list
	private ArrayList<key> keys=new ArrayList<key>();//keys list
	private ArrayList<magic> magics=new ArrayList<magic>();//magic list
	private ArrayList<Treasure> treasures=new ArrayList<Treasure>();//treasure list
	private double mouseX=0;
	private double mouseY=0;

	/**
	 * Width of the canvas.
	 */
	public static final int CANVAS_WIDTH = 600;
	
	protected void onLoad(File file) {
	}
	/**
	 * Height of the canvas.
	 */
	public static final int CANVAS_HEIGHT = 600;

	private static JFrame frame;
	
	private static final Dimension DRAWING_SIZE = new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT);
	private static final Dimension CONTROLS_SIZE = new Dimension(150, 600);
	int x1=265;
	int y1=290;
	
	/**
	 * GUI constructor.
	 */
	public GUI() {
		initialise();
	}
	
	/**
	 * Get rooms for persistence
	 * @return ArrayList<Room>
	 */
	public ArrayList<Room> getRooms(){
		return rooms;
	}
	
	/**
	 * Is called every time the drawing canvas is drawn. 
	 */
	private void render(Graphics g,int x, int y,int width,int height) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
		for(Room room : rooms) {
			room.draw(g);
		}
		for(Door door : doors) {
			door.draw(g);
		}
		for(Wall wall : walls) {
			wall.draw(g);
		}
		for(key key1 : keys) {
			key1.draw(g);
		}
		for(magic m:magics) {
			m.draw(g);
		}
		for(Treasure t:treasures) {
			t.draw(g);
		}
	}
	
	/**
	 * Is called every time the user presses a key. This can be used for moving the
	 * camera around. It is passed a KeyEvent object, whose methods of interest are
	 * getKeyChar() and getKeyCode().
	 */
	private void onKeyPress(KeyEvent ev) {
		
	}

	private void initialise() {
		frame = new JFrame();
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.LINE_AXIS));
		frame.setSize(new Dimension(DRAWING_SIZE.width + CONTROLS_SIZE.width, DRAWING_SIZE.height));
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// set up the drawing canvas, hook it into the render() method, and give
		// it a nice default if render() returns null.
		JComponent drawing = new JComponent() {
			protected void paintComponent(Graphics g) {
				render(g,0,0,CANVAS_WIDTH, CANVAS_HEIGHT);
				redraw();
			}
		};
		
		drawing.setPreferredSize(DRAWING_SIZE);
		drawing.setMinimumSize(DRAWING_SIZE);
		drawing.setMaximumSize(DRAWING_SIZE);
		drawing.setVisible(true);
		
		//add room on right side;
		rightroom=new JButton("rightRoom");
		rightroom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				if(rightRoomCanBuild) {
					x1=x1+25;
					if(x1>CANVAS_WIDTH-25) {
						System.out.println("no empty space");
					}
					else {
						Room room1=new Room(x1,y1,10,10);
						for(Room room2:rooms) {
							if(room2.x==x1&&room2.y==y1) {
								System.out.println("There is already a room in the right side");
								rightRoomCanBuild=false;
							}
						}
						if(rightRoomCanBuild) {
							rooms.add(room1);
							topRoomCanBuild=true;
							downRoomCanBuild=true;
						}
						else {
							x1=x1-25;
						}
						
					}
				}
				
			}
		});
		
		//add room on left side;
		leftroom=new JButton("leftRoom");
		leftroom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				if(leftRoomCanBuild) {
					x1=x1-25;
					if(x1<0) {
						System.out.print("no empty space");
					}
					else {
						Room room1=new Room(x1,y1,10,10);
						for(Room room2:rooms) {
							if(room2.x==x1&&room2.y==y1) {
								System.out.println("There is already a room in the left side");
								leftRoomCanBuild=false;
							}
						}
						if(leftRoomCanBuild) {
							rooms.add(room1);
							topRoomCanBuild=true;
							downRoomCanBuild=true;
						}
						else {
							x1=x1+25;
						}
					}
				}
			}
		});
		
		//add room on top side;
		toproom=new JButton("topRoom");
		toproom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				if(topRoomCanBuild) {
					y1=y1-25;
					if(y1<0) {
						System.out.print("no empty space");
					}
					else {
						Room room1=new Room(x1,y1,10,10);
						for(Room room2:rooms) {
							if(room2.x==x1&&room2.y==y1) {
								System.out.println("There is already a room in the top side");
								topRoomCanBuild=false;
							}
						}
						if(topRoomCanBuild) {
							rooms.add(room1);
							rightRoomCanBuild=true;
							leftRoomCanBuild=true;
						}
						else {
							y1=y1+25;
						}
					}
				}
				
			}
		});
		
		//add room on south side;
		downroom=new JButton("downRoom");
		downroom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				if(downRoomCanBuild) {
					y1=y1+25;
					if(y1<0) {
						System.out.print("no empty space");
					}
					else {
						Room room1=new Room(x1,y1,10,10);
						for(Room room2:rooms) {
							if(room2.x==x1&&room2.y==y1) {
								System.out.println("There is already a room in the down side");
								downRoomCanBuild=false;
							}
						}
						if(downRoomCanBuild) {
							rooms.add(room1);
							rightRoomCanBuild=true;
							leftRoomCanBuild=true;
						}	
						else {
							y1=y1-25;
						}
					}
				}
			}
		});
		
		//add door on left;
		leftdoor=new JButton("leftDoor");
		leftdoor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				// add key to the current room
				Door door1=new Door(x1,y1,2,20);
				for(Room room:rooms) {
					if(room.contains(new Point(x1,y1))) {
						if(room.leftWall==false) {
							doors.add(door1);
							room.leftdoor();
						}	
					}
				}
			}
		});
		
		//add door on right side;
		rightdoor=new JButton("rightDoor");
		rightdoor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				// add key to the current room
				Door door1=new Door(x1+20,y1,2,20);
				for(Room room:rooms) {
					if(room.contains(new Point(x1,y1))) {
						if(room.rightWall==false) {
							doors.add(door1);
							room.rightdoor();
						}	
					}
				}
			}
		});
		
		//add door on top side;
		topdoor=new JButton("topDoor");
		topdoor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				// add key to the current room
				Door door1=new Door(x1,y1,20,2);
				for(Room room:rooms) {
					if(room.contains(new Point(x1,y1))) {
						if(room.topWall==false) {
							doors.add(door1);
							room.topdoor();
						}	
					}
				}
			}
		});
		
		//add door on south side;
		downdoor=new JButton("downDoor");
		downdoor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				// add key to the current room
				Door door1=new Door(x1,y1+20,20,2);
				for(Room room:rooms) {
					if(room.contains(new Point(x1,y1))) {
						if(room.downWall==false) {
							doors.add(door1);
							room.downdoor();
						}	
					}
				}
			}
		});
		
		//add wall on different sides;
		wall=new JButton("leftwall");
		wall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				// add key to the current room
				Wall wall1=new Wall(x1,y1,2,20);
				for(Room room:rooms) {
					if(room.contains(new Point(x1,y1))) {
						if(room.leftDoor==false) {
							walls.add(wall1);
							room.leftwall();
						}	
					}
				}
				
			}
		});
		rightwall=new JButton("rightwall");
		rightwall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				// add key to the current room
				Wall wall1=new Wall(x1+20,y1,2,20);
				for(Room room:rooms) {
					if(room.contains(new Point(x1,y1))) {
						if(room.rightDoor==false) {
							walls.add(wall1);
							room.rightwall();
						}	
					}
				}
			}
		});
		topwall=new JButton("topwall");
		topwall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				// add key to the current room
				Wall wall1=new Wall(x1,y1,20,2);
				for(Room room:rooms) {
					if(room.contains(new Point(x1,y1))) {
						if(room.topDoor==false) {
							walls.add(wall1);
							room.topwall();
						}	
					}
				}
			}
		});
		downwall=new JButton("downwall");
		downwall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				// add key to the current room
				Wall wall1=new Wall(x1,y1+20,20,2);
				for(Room room:rooms) {
					if(room.contains(new Point(x1,y1))) {
						if(room.downDoor==false) {
							walls.add(wall1);
							room.downwall();
						}	
					}
				}
			}
		});
		
		//add key on current room;
		key=new JButton("key");
		key.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				// add key to the current room
				key key1=new key(x1,y1);
				for(Room room:rooms) {
					if(room.key==false) {
						room.haveKey();
						keys.add(key1);
					}
				}
				
			}
		});
		
		//add magic on current room;
		magic=new JButton("magic");
		magic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				// add key to the current room
				magic m=new magic(x1,y1);
				for(Room room:rooms) {
					if(room.magic1==false) {
						room.magic11();
						magics.add(m);
					}
				}
				
			}
		});
		
		//add treasure on current room;
		treasure=new JButton("treasure");
		treasure.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				// add key to the current room
				Treasure t=new Treasure(x1,y1);
				for(Room room:rooms) {
					if(room.treasure1==false) {
						room.treasure11();
						treasures.add(t);
					}
				}
				
			}
		});
		
		//open the light;
		light=new JButton("light");
		light.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				// add key to the current room
				for(Room room:rooms) {
					if(room.contains(new Point(x1,y1))) {
						room.light();
					}
				}
				
			}
		});
		
		//clear all the items and restart the game;
		clear=new JButton("clear");
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				// add key to the current room
				rooms=new ArrayList<Room>();
				doors=new ArrayList<Door>();
				walls=new ArrayList<Wall>();
				keys=new ArrayList<key>();
				magics=new ArrayList<magic>();
				treasures=new ArrayList<Treasure>();
				mouseX=0;
				mouseY=0;
				x1=265;
				y1=290;
				
			}
		});
		
		JPanel roompanel = new JPanel(new BorderLayout());
		roompanel.setMaximumSize(new Dimension(1000, 25));
		roompanel.setPreferredSize(new Dimension(1000, 25));
		JPanel lightpanel = new JPanel(new BorderLayout());
		lightpanel.setMaximumSize(new Dimension(1000, 25));
		lightpanel.setPreferredSize(new Dimension(1000, 25));
		JPanel lroompanel = new JPanel(new BorderLayout());
		lroompanel.setMaximumSize(new Dimension(1000, 25));
		lroompanel.setPreferredSize(new Dimension(1000, 25));
		JPanel troompanel = new JPanel(new BorderLayout());
		troompanel.setMaximumSize(new Dimension(1000, 25));
		troompanel.setPreferredSize(new Dimension(1000, 25));
		JPanel droompanel = new JPanel(new BorderLayout());
		droompanel.setMaximumSize(new Dimension(1000, 25));
		droompanel.setPreferredSize(new Dimension(1000, 25));
		JPanel doorpanel = new JPanel(new BorderLayout());
		doorpanel.setMaximumSize(new Dimension(1000, 25));
		doorpanel.setPreferredSize(new Dimension(1000, 25));
		JPanel rdoorpanel = new JPanel(new BorderLayout());
		rdoorpanel.setMaximumSize(new Dimension(1000, 25));
		rdoorpanel.setPreferredSize(new Dimension(1000, 25));
		JPanel tdoorpanel = new JPanel(new BorderLayout());
		tdoorpanel.setMaximumSize(new Dimension(1000, 25));
		tdoorpanel.setPreferredSize(new Dimension(1000, 25));
		JPanel ddoorpanel = new JPanel(new BorderLayout());
		ddoorpanel.setMaximumSize(new Dimension(1000, 25));
		ddoorpanel.setPreferredSize(new Dimension(1000, 25));
		JPanel wallpanel = new JPanel(new BorderLayout());
		wallpanel.setMaximumSize(new Dimension(1000, 25));
		wallpanel.setPreferredSize(new Dimension(1000, 25));
		JPanel rwallpanel = new JPanel(new BorderLayout());
		rwallpanel.setMaximumSize(new Dimension(1000, 25));
		rwallpanel.setPreferredSize(new Dimension(1000, 25));
		JPanel twallpanel = new JPanel(new BorderLayout());
		twallpanel.setMaximumSize(new Dimension(1000, 25));
		twallpanel.setPreferredSize(new Dimension(1000, 25));
		JPanel dwallpanel = new JPanel(new BorderLayout());
		dwallpanel.setMaximumSize(new Dimension(1000, 25));
		dwallpanel.setPreferredSize(new Dimension(1000, 25));
		JPanel keypanel = new JPanel(new BorderLayout());
		keypanel.setMaximumSize(new Dimension(1000, 25));
		keypanel.setPreferredSize(new Dimension(1000, 25));
		JPanel magicpanel = new JPanel(new BorderLayout());
		magicpanel.setMaximumSize(new Dimension(1000, 25));
		magicpanel.setPreferredSize(new Dimension(1000, 25));
		JPanel Tpanel = new JPanel(new BorderLayout());
		Tpanel.setMaximumSize(new Dimension(1000, 25));
		Tpanel.setPreferredSize(new Dimension(1000, 25));
		JPanel clearpanel = new JPanel(new BorderLayout());
		clearpanel.setMaximumSize(new Dimension(1000, 25));
		clearpanel.setPreferredSize(new Dimension(1000, 25));
		roompanel.add(rightroom, BorderLayout.CENTER);
		clearpanel.add(clear, BorderLayout.CENTER);
		lroompanel.add(leftroom, BorderLayout.CENTER);
		troompanel.add(toproom, BorderLayout.CENTER);
		droompanel.add(downroom, BorderLayout.CENTER);
		wallpanel.add(wall, BorderLayout.CENTER);
		rwallpanel.add(rightwall, BorderLayout.CENTER);
		twallpanel.add(topwall, BorderLayout.CENTER);
		dwallpanel.add(downwall, BorderLayout.CENTER);
		keypanel.add(key, BorderLayout.CENTER);
		doorpanel.add(leftdoor, BorderLayout.CENTER);
		rdoorpanel.add(rightdoor, BorderLayout.CENTER);
		tdoorpanel.add(topdoor, BorderLayout.CENTER);
		ddoorpanel.add(downdoor, BorderLayout.CENTER);
		magicpanel.add(magic, BorderLayout.CENTER);
		Tpanel.add(treasure, BorderLayout.CENTER);
		lightpanel.add(light, BorderLayout.CENTER);
		
		//load files;
		final JFileChooser fileChooser = new JFileChooser();
		JButton load = new JButton("Load");
		load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				// set up the file chooser
				try {
					LoadXml.unMarshal();
				} catch (JAXBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		JPanel loadpanel = new JPanel(new BorderLayout());
		loadpanel.setMaximumSize(new Dimension(1000, 25));
		loadpanel.setPreferredSize(new Dimension(1000, 25));
		loadpanel.add(load, BorderLayout.CENTER);
		
		//save button for saving;
		JButton save = new JButton("Save");
		load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				// set up the file chooser
				new ConvertMapEditor(rooms);
			}
		});
		JPanel savepanel = new JPanel(new BorderLayout());
		savepanel.setMaximumSize(new Dimension(1000, 25));
		savepanel.setPreferredSize(new Dimension(1000, 25));
		savepanel.add(save, BorderLayout.CENTER);
		

		// make the panel on the right, fix its size, give it a border!
		JPanel controls = new JPanel();
		controls.setPreferredSize(CONTROLS_SIZE);
		controls.setMinimumSize(CONTROLS_SIZE);
		controls.setMaximumSize(CONTROLS_SIZE);
		controls.setLayout(new BoxLayout(controls, BoxLayout.PAGE_AXIS));
		Border edge = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		controls.setBorder(edge);
		controls.add(roompanel);
		controls.add(lroompanel);
		controls.add(troompanel);
		controls.add(droompanel);
		controls.add(doorpanel);
		controls.add(rdoorpanel);
		controls.add(tdoorpanel);
		controls.add(loadpanel);
		controls.add(ddoorpanel);
		controls.add(clearpanel);
		controls.add(wallpanel);
		controls.add(rwallpanel);
		controls.add(twallpanel);
		controls.add(dwallpanel);
		controls.add(keypanel);
		controls.add(magicpanel);
		controls.add(Tpanel);
		controls.add(lightpanel);
		controls.add(Box.createRigidArea(new Dimension(0, 15)));
		// if i were going to add more GUI components, i'd do it here.
		controls.add(Box.createVerticalGlue());

		// put it all together.
		frame.add(drawing);
		frame.add(controls);

		frame.pack();
		frame.setVisible(true);
		frame.addMouseListener(new mouse());
	}
	
	public void redraw() {
		frame.repaint();
	}
	
	public static void main(String[] args) {
		new GUI();
		
	}
	class mouse extends MouseAdapter{
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			mouseX=e.getX();
			mouseY=e.getY()-25;
			boolean isRoom=false;
			Point p=new Point(mouseX,mouseY);
			for(Room room:rooms) {
				if(room.contains(p)) {
					System.out.println("room exist");
					isRoom=true;
					x1=room.x;
					y1=room.y;
					room.message();
					break;
				}
			}
			if(!isRoom) {
				System.out.println("room does not exist");
			}
		}
	}
}


