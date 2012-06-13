package map.Components;

import java.awt.Image;
import java.awt.Point;

import chat.commands.Command;




/**
 * @author Jake
 * @version 1.0
 * @created 03-Jun-2011 9:18:48 AM
 */
public class Door extends Component implements Interative {



	/**
	 * 
	 * @param component_name
	 * @param component_image
	 * @param component_location
	 * @param component_description
	 */
	public Door(String component_name, Image component_image,
			Point component_location, String component_description) {
		super(component_name, component_image, component_location,
				component_description);
		// TODO Auto-generated constructor stub
	}

	public Command interact(){
		return null;
	}

	public String getDescription(){
		return "";
	}

	public Image getImage(){
		return null;
	}

	public Point getLocation(){
		return null;
	}

}//end Door