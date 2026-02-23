package oriseus.Sagitarius_equipment.utilities;

import java.io.File;
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
	
	// ВНИМАНИЕ ВРЕМЕННЫЙ ПУТЬ ДЛЯ КОПИРОВАНИЯ. ЗАМЕНИТЬ В ПРОДЕ НА РЕАЛЬНЫЙ
	//Копирует файл в папку image в корне программы и возвращает полный путь
	public static String copyFileToDataBase(File originalFile) {
		Path source = Paths.get(originalFile.getAbsolutePath());
		Path target = Paths.get("D:\\build\\Sagitarius-equipment-0.0.1\\images\\" + originalFile.getName());

		try { 
			Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return target.toString();		
	}
}
