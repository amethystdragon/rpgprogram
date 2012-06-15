package characterCreation;

import java.io.File;

/**
 * @author eberta
 * @version 1.0
 * @created 14-Jun-2012 8:10:54 PM
 */
public class CharacterCreation {

	public static CharacterObject CreateCharacter(){
		return CreateCharacter("default.rpgtemp");
	}

	/**
	 * 
	 * @param String
	 */
	public static CharacterObject CreateCharacter(String templateName ){
		File file = ImportExport.ImportCharacter(templateName);
		CharacterObject co = Character.CreateCharacter(file);
		return co;
	}
}//end CharacterCreation