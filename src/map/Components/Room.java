package map.Components;

import java.util.List;

/**
 * @author Williams, Jacob (williamsj@msoe.edu) (xp-dev:Exspes)
 * @version 1.0
 * @created 03-Jun-2011
 * 
 *         Room object that floors are composed of. Rooms are Composed of components and walls
 */
public class Room {

	/**
	 * The Components that make up the room
	 */
	private List<Component> components;
	
	/**
	 * The Walls that make up the room
	 */
	private List<Wall> walls;

	/**
	 * Creates a new Room composed of the passed components and walls
	 * @param components The components that the Room is to be composed of
	 * @param walls The walls that the Room is to be composed of
	 */
	public Room( List<Component> components, List<Wall> walls){

		this.components = components;
		
		this.walls = walls;
		
	}

	/**
	 * Getter for the Walls List
	 * @return The Walls that the room is composed of
	 */
	public List<Wall> getWalls(){
		return this.walls;
	}

	/**
	 * Getter for the Components List
	 * @return The Components that the room is composed of
	 */
	public List<Component> getComponents(){
		return this.components;
	}

}//end Room