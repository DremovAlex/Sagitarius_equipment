package oriseus.Sagitarius_equipment.utilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FileHundler {
	
	//Открывает проводник для выбора изображения
	public static File chooseImage(Stage stage) {
	    
		FileChooser fileChooser = new FileChooser();
	    fileChooser.setTitle("Выберите изображение");

	    fileChooser.getExtensionFilters().add(
	        new FileChooser.ExtensionFilter(
	            "Изображения",
	            "*.png", "*.jpg", "*.jpeg", "*.bmp", "*.gif"
	        )
	    );

	    fileChooser.setInitialDirectory(
	        new File(System.getProperty("user.home"))
	    );

	    return fileChooser.showOpenDialog(stage);
	}
	
	//Копирует файл в папку image в корне программы и возвращает полный путь
	public static String copyFileToDataBase(File originalFile) {
		
		File path = new File(System.getProperty("user.dir"));
		File folder = new File(path.getParentFile() + "/images");
		
			if (!folder.exists()) {
				folder.mkdir();
			}
		
		Path source = Paths.get(originalFile.getAbsolutePath());
		Path target = Paths.get(folder + File.separator + originalFile.getName());
		
		System.out.println(target);
		
		try { 
			Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return target.toString();		
	}
	
	public static String getPathToImageFolder() {
		File path = new File(System.getProperty("user.dir"));
		File folder = new File(path.getParentFile() + "/images");
			
		if (!folder.exists()) {
			folder.mkdir();
		}
		
		return folder.getAbsolutePath();
	}
	
	public static Path getLogFile() {
		
		File path = new File(System.getProperty("user.dir"));
		File logFile = new File(path.getParentFile() + File.separator + "log.txt");
		
		if (!logFile.exists()) {
			try {
				logFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return logFile.toPath();
	}
}
