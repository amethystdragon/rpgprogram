package characterCreation;

/**
 * @author eberta
 * @version 1.0
 * @created 14-Jun-2012 8:10:47 PM
 */
public class Armor {

	private String Name;
	private String Notes;

	public Armor(){

	}

	public void finalize() throws Throwable {

	}
	public String getName(){
		return Name;
	}

	public String getNotes(){
		return Notes;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setName(String newVal){
		Name = newVal;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setNotes(String newVal){
		Notes = newVal;
	}
}//end Armor