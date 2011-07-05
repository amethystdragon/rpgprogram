package map.Components;

import java.awt.Point;

/**
 * @author Williams, Jacob (williamsj@msoe.edu) (xp-dev:Exspes)
 * @version 1.0
 * @created 03-Jun-2011
 * 
 *          Singleton object that keeps track of the current properties of the
 *          map to allow for attributes to be stored independent of the Map
 *          object or the viewing client which both need the information.
 */
public class MapManager {

	/**
	 * Unique instance of the MapManager to keep it singleton
	 */
	private static MapManager uniqueInstance = null;

	/**
	 * The "size" of the grid
	 */
	private int map_grid_size;

	/**
	 * The offset in the x direction that the graphic is to be drawn from
	 */
	private int map_x_offset;

	/**
	 * The offset in the y direction that the graphic is to be drawn from
	 */
	private int map_y_offset;

	/**
	 * Private Constructor that initializes the attributes to default settings
	 */
	private MapManager() {

		map_grid_size = 10;

		map_x_offset = 0;

		map_y_offset = 0;

	}

	/**
	 * Static method to retrieve the unique instance of the MapManager
	 * 
	 * @return The unique instance of the MapManager
	 */
	public static MapManager getInstance() {

		// check to see if the MapManager has been initialized
		if (MapManager.uniqueInstance == null) {

			// eneter a synchronized block to ensure that the MapManager is only
			// initialized once, uses the MapManager class object as the the
			// guard object
			synchronized (MapManager.class) {

				// check again to see if the MapManager has been initialized
				if (MapManager.uniqueInstance == null) {

					// Initialize the MapManager
					MapManager.uniqueInstance = new MapManager();

				}

			}

		}

		// Return the unique instance of the MapManager
		return MapManager.uniqueInstance;

	}

	/**
	 * Getter method for the map_grid_size attribute
	 * 
	 * @return The "size" of the grid
	 */
	public int getGridSize() {

		return this.map_grid_size;

	}

	/**
	 * Setter method for the map_grid_size attribute
	 * 
	 * @param grid_size
	 *            the new value for the map_grid_size attribute
	 */
	public void setGridSize(int grid_size) {

		this.map_grid_size = grid_size;

	}

	/**
	 * Getter method for the map_x_offset attribute
	 * 
	 * @return How far the map display is currently offset on the x direction
	 */
	public int getXOffset() {

		return this.map_x_offset;

	}

	/**
	 * Setter method for the map_x_offset attribute
	 * 
	 * @param x_offset
	 *            the new value for the map_x_offset attribute
	 */
	public void setXOffset(int x_offset) {

		this.map_x_offset = x_offset;

	}

	/**
	 * Getter for the map_y_offset attribute
	 * 
	 * @return How far the map display is currently offset in the y direction
	 */
	public int getYOffset() {

		return this.map_y_offset;

	}

	/**
	 * Setter for the map_y_offset attribute
	 * 
	 * @param y_offset
	 *            the new value for the map_y_offset attribute
	 */
	public void setYOffset(int y_offset) {

		this.map_y_offset = y_offset;

	}

	/**
	 * Convert a Point from the "Grid" system to the absolute Coordinates on the
	 * Graphic
	 * 
	 * @param p
	 *            The point to be converted
	 * @return The Converted Point
	 */
	public Point convertFromGrid(Point p) {

		return new Point(
				(int) (p.getX() * this.getGridSize() - this.getXOffset()),
				(int) (p.getY() * this.getGridSize() - this.getYOffset()));

	}

}
