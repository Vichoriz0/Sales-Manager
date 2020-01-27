package utilities;

import java.io.File;
import javax.swing.JOptionPane;

public class FileUtilities {
	
	//Borra el archivo con oldFileName (si existe), y cambia el nombre del archivo con newFileName a oldFileName.
	public static void renameFile(String oldFileName, String newFileName) {
		boolean error = false;
		File oldFile = new File(oldFileName);
	    File newFile = new File(newFileName);
	    if(oldFile.exists())
	        if(!oldFile.delete())
	        	error = true;
	    if(error == false && newFile.renameTo(new File(oldFileName)))
	        return;
	    JOptionPane.showMessageDialog(null, "Error en escribir al archivo '" + System.getProperty("user.dir") + oldFileName + "'.\n"
	    		+ "\nPor favor contactar con soporte:\nNombre:   Vicente Barria Veas\nE-mail:   v.barriaveas@gmail.com");
	}
	
}