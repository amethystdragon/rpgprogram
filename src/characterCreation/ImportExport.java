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
	public static void ExportCharacter(Character CharacterObject){
		
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