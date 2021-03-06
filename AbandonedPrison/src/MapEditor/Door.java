/**
 * @author Pai Zhou
 * id: 300335146
 *
 */
package MapEditor;

import java.awt.Color;
import java.awt.Graphics;

public class Door {
	int x;
	int y;
	int width;
	int height;

	public Door(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	/**
	 * Getter method for getting room name
	 *
	 * @return room name
	 */
	public String getName() {
		return null;
	}

	/**
	 * Getting method for getting description of room
	 *
	 * @return room description
	 */
	public String getDescription() {
		return null;
	}

	/**
	 * Get room image
	 *
	 * @return room image
	 */
	public String getImage() {
		return null;
	}

	/**
	 * draw the door
	 */
	public void draw(Graphics g) {
		g.setColor(Color.red);
		g.drawRect(x, y, width, height);
	}

}
