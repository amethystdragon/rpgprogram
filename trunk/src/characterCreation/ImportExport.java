package characterCreation;

import java.io.File;

/**
 * @author eberta
 * @version 1.0
 * @created 14-Jun-2012 8:10:52 PM
 */
public class ImportExport {

	/**
	 * 
	 * @param CharacterObject
	 */
	public static boolean ExportCharacter(Character CharacterObject, String object){
		boolean success = false;
		if(object.equals("Template")){
			success = TemplateImportExport.exportTemplate(CharacterObject);
		}else if(object.equals("Character")){
			success = CharacterImportExport.exportCharacter(CharacterObject);
		}
		return success;
	}

	/**
	 * 
	 * @param String
	 */
	public static File ImportCharacter(String templateName){
		File file = null;
		if(templateName.contains(".rpgtemp")){
			file = TemplateImportExport.importTemplate(templateName);
		}else if(templateName.contains(".rpgchar")){
			file = CharacterImportExport.importCharacter(templateName);
		}
		return file;
	}
}//end ImportExport