package map.Components;

import java.util.List;

/**
 * @author Williams, Jacob (williamsj@msoe.edu) (xp-dev:Exspes)
 * @version 1.0
 * @created 03-Jun-2011
 * 
 *          Map JComponent that contains all floors and rooms.
 */
public class Floor {

	/**
	 * The rooms contained in this floor
	 */
	private List<Room> rooms;

	/**
	 * the current room for the Floor
	 */
	private Room currentRoom;

	/**
	 * Constructor for the Floor object. Sets the List of rooms and the current
	 * room if the passed room is contained in the passed rooms list, if it is
	 * not the current room attribute is set to null
	 * 
	 * @param rooms
	 *            The List of rooms the floor is to be composed of
	 * @param currentRoom
	 *            The room to set the current room to.
	 */
	public Floor(List<Room> rooms, Room currentRoom) {

		this.rooms = rooms;

		// check that the passed room is contained in the passed rooms list, if
		// it is set the currentRoom attribite to the passed room, otherwise
		// null
		if (this.getRooms().contains(currentRoom)) {

			this.currentRoom = currentRoom;

		} else {

			this.currentRoom = null;

		}

	}

	/**
	 * Getter for the Rooms list
	 * @return The List of the rooms contained by this floor
	 */
	public List<Room> getRooms() {
		return this.rooms;
	}

	/**
	 * Adds a room to the Floor, will not add a room tot the floor that the floor allready contains
	 * @param newRoom The room to be added to the floor
	 */
	public void addRoom( Room newRoom ){
		
		// Check to see if the floor already contains the room, if it doesn't add it to the floor
		if( !this.getRooms().contains(newRoom) ){
			
			this.rooms.add(newRoom);
			
		}
		
	}
	
	/**
	 * Getter for the Current Room
	 * @return
	 */
	public Room getCurrentRoom() {
		return this.currentRoom;
	}

	/**
	 * Setter for the Current room. Only will set the current room if the passed room is contained by the floor
	 * @param room
	 */
	public void setCurrentRoom(Room room) {

		// Check to see that the passed room is contained by the floor, if it is set the current room to the passed room
		if (this.getRooms().contains(room)) {

			this.currentRoom = room;

		}

	}

}// end Floor