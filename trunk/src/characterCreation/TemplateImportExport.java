package characterCreation;
import java.io.File;



/**
 * @author eberta
 * @version 1.0
 * @created 14-Jun-2012 8:10:55 PM
 */
public class TemplateImportExport {

	/**
	 * 
	 * @param Character
	 */
	public static boolean exportTemplate(Character characterTemplate ){
		return false;
	}

	/**
	 * 
	 * @param String
	 */
	public static File importTemplate(String templateName){
		File file = null;
		file = new File(templateName);
		if(file.isFile()){
			return file;
		}
		return null;
	}
}//end Template Import/Export