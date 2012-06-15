package characterCreation;
import java.io.File;



/**
 * @author eberta
 * @version 1.0
 * @created 14-Jun-2012 8:10:56 PM
 */
public class CharacterImportExport {

	/**
	 * 
	 * @param Character
	 */
	public static boolean exportCharacter(Character Character){
		return false;
	}

	/**
	 * 
	 * @param String
	 */
	public static File importCharacter(String characterName){
		File file = null;
		file = new File(characterName);
		if(file.isFile()){
			return file;
		}
		return null;
	}
}//end Character Import/Export