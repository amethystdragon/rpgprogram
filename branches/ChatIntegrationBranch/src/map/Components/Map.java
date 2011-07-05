package map.Components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

import javax.swing.JComponent;

/**
 * @author Williams, Jacob (williamsj@msoe.edu) (xp-dev:Exspes)
 * @version 1.0
 * @created 03-Jun-2011
 * 
 *          Map JComponent that contains all floors and rooms.
 */
public class Map extends JComponent {

	/**
	 * The Floors that compose the Map
	 */
	private List<Floor> floors;

	/**
	 * The Currently visible floor
	 */
	private Floor currentFloor;

	/**
	 * Constructor that takes the floors to compose the map, the starting floor
	 * to display and the starting room to display
	 * 
	 * @param floors
	 *            The Floors to Compose the map
	 * @param startingFloor
	 *            The Floor to be initially displayed
	 * @param startingRoom
	 *            The room to make the "starting" room
	 */
	public Map(List<Floor> floors, Floor startingFloor, Room startingRoom) {

		this.floors = floors;

		this.currentFloor = startingFloor;

		this.currentFloor.setCurrentRoom(startingRoom);

	}

	/**
	 * Getter for the private floor list
	 * 
	 * @return The private floor list
	 */
	public List<Floor> getFloors() {
		return this.floors;
	}

	/**
	 * Adds a new Floor to the map
	 * 
	 * @param newFloor
	 */
	public void addFloor(Floor newFloor) {

		this.floors.add(newFloor);

	}

	/**
	 * Getter for the private current Floor attribute
	 * 
	 * @return The provate currentFloor attribute
	 */
	public Floor getCurrentFloor() {
		return this.currentFloor;
	}

	/**
	 * Setter for the currentFloor, will only set of the passed floor is
	 * contained by the map
	 * 
	 * @param floor
	 *            The floor to set as the visible floor
	 */
	public void setCurrentFloor(Floor floor) {

		// Check to see if the floors list contains the passed floor, if it does
		// set the currentFloor to the passed floor
		if (this.floors.contains(floor)) {

			this.currentFloor = floor;

		}

	}

	@Override
	/**
	 * Paints the Map to the passed Graphic. The Current Room of the current floor is drawn in black while the other rooms of the current Floor are drawn in dark grey.
	 * @param g Graphics to draw the Map on
	 */
	public void paintComponents(Graphics g) {

		super.paintComponents(g);

		// get the current room from the current floor
		List<Room> rooms = this.currentFloor.getRooms();

		// get the rooms from the current Floor
		Room currentRoom = this.currentFloor.getCurrentRoom();

		// set the color to the "other" rooms color
		g.setColor(Color.LIGHT_GRAY);

		// iterate through each room and draw it
		for (Room r : rooms) {

			// check that the next floor to be drawn is not the current Room
			if (r != currentRoom) {

				// get the walls from the room
				for (Wall w : r.getWalls()) {

					// convert the walls endpoints from the grid system to absolute coordinates
					Point start = MapManager.getInstance().convertFromGrid(
							w.getStart());
					Point end = MapManager.getInstance().convertFromGrid(
							w.getEnd());

					// draw the wall
					g.drawLine(start.x, start.y, end.x, end.y);

				}

			}

		}

		// set the color to the curent room color
		g.setColor(Color.BLACK);

		// iterate through each wall and draw it
		for (Wall w : currentRoom.getWalls()) {

			// convert the walls endpoints from the grid system to absolute coordinates
			Point start = MapManager.getInstance()
					.convertFromGrid(w.getStart());
			Point end = MapManager.getInstance().convertFromGrid(w.getEnd());

			// draw the wall
			g.drawLine(start.x, start.y, end.x, end.y);

		}

	}
	
}// end Map