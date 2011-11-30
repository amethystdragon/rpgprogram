package map.Components;

import java.awt.Image;
import java.awt.Point;

/**
 * @author Williams, Jacob (williamsj@msoe.edu) (xp-dev:Exspes)
 * @version 1.0
 * @created 03-Jun-2011
 * 
 *          Provides a Component object that can represent a multitude of
 *          different things on a map from obsticles to terrain
 */
public class Component {

	/**
	 * The Name of the Component
	 */
	private String component_name;
	
	/**
	 * A Description of the component
	 */
	private String component_description;
	
	/**
	 * The Image that will be shown on the map representing this component
	 */
	private Image component_image;
	
	/**
	 * The Location of the Component on the map
	 */
	private Point component_location;

	/**
	 * Constructor for the Component that takes all the attributes as parameters
	 * @param component_name The name of the Component
	 * @param component_image The image to represent the component on the map
	 * @param component_location The location of the component on the map
	 * @param component_description The description of the component
	 */
	public Component(String component_name, Image component_image, Point component_location,
			String component_description) {
		
		this.component_name = component_name;
		
		this.component_image = component_image;
		
		this.component_location = component_location;
		
		this.component_description = component_description; 

	}

	/**
	 * Getter for the Components Description
	 * @return The components description
	 */
	public String getDescription() {

		return this.component_description;

	}

	/**
	 * Getter for the Components Image
	 * @return The Image to represent the component on the Map
	 */
	public Image getImage() {

		return this.component_image;

	}

	/**
	 * Getter for the components location
	 * @return The location of the component on the map
	 */
	public Point getLocation() {

		return this.component_location;

	}

}// end Component