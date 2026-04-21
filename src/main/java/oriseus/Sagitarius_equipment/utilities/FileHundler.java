package oriseus.Sagitarius_equipment.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import oriseus.Sagitarius_equipment.model.Company;
import oriseus.Sagitarius_equipment.model.DataBase;
import oriseus.Sagitarius_equipment.model.Frame;
import oriseus.Sagitarius_equipment.model.Manager;
import oriseus.Sagitarius_equipment.model.StatusOfFrame;
import oriseus.Sagitarius_equipment.model.TypeOfFrame;

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

	//открывает проводник для выбора pdf файла
	public static File chosePdfFile(Stage stage) {
		FileChooser fileChooser = new FileChooser();
	    fileChooser.setTitle("Выберите изображение");

	    fileChooser.getExtensionFilters().add(
	        new FileChooser.ExtensionFilter(
	            "PDF файлы",
	            "*.pdf"
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
	
	//копирует пдф файл в папку
	public static String copyPdfFFileToDataBase(File originalFile) {
		File path = new File(System.getProperty("user.dir"));
		File folder = new File(path.getParentFile() + "/pdf");
		
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

	public static String getPathToPDFFilesFolder() {
		File path = new File(System.getProperty("user.dir"));
		File folder = new File(path.getParentFile() + "/pdf");
			
		if (!folder.exists()) {
			folder.mkdir();
		}
		
		return folder.getAbsolutePath();
	}
	
	public static Path getLogFile() {
		
		File path = new File(System.getProperty("user.dir"));		
		File folder = new File(path.getParentFile() + "/log");
			
		if (!folder.exists()) {
			folder.mkdir();
		}
		
		File logFile = new File(folder + File.separator + "log.txt");		

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

	//Возвращает путь к корневой папке приложения
	public static File getPathToParentFolder() {
		return new File(System.getProperty("user.dir"));
	}

	//открывает проводник для выбора xlsx файла для импорта базы данных
	public static File choseXlsxFile(Stage stage) {
		FileChooser fileChooser = new FileChooser();
	    fileChooser.setTitle("Выберите XLSX файл");

	    fileChooser.getExtensionFilters().add(
	        new FileChooser.ExtensionFilter(
	            "Xlsx файлы",
	            "*.xlsx"
	        )
	    );

	    fileChooser.setInitialDirectory(
	        new File(System.getProperty("user.home"))
	    );

	    return fileChooser.showOpenDialog(stage);
	}

	public static void importDataBaseFromXlsx(File file) {
        FileInputStream fis = null;
		try {
			fis = new FileInputStream(file.getAbsolutePath());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Workbook workbook = null;
		try {
			workbook = new XSSFWorkbook(fis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		FormulaEvaluator evaluator = workbook
            .getCreationHelper()
            .createFormulaEvaluator();

    	evaluator.evaluateAll(); // пересчитываем все формулы
        
		Sheet sheet = workbook.getSheetAt(3);

        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue; // пропускаем заголовок
			if (row.getRowNum() == 1) continue; // пропускаем заголовок

			getFrameFromRow(row, evaluator);
        }

        try {
			workbook.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
	}

	//Достает сетку из строки xlsx таблицы и помещает в базу
	private static void getFrameFromRow(Row row, FormulaEvaluator evaluator) {
		LocalDate dateOfCreation;
		Manager manager;
		Company company;
		String nameOfFrame;
		String numberOfLayout;
		TypeOfFrame typeOfFrame;
		LocalDate dateOfLastWork;
		String comment;
		StatusOfFrame statusOfFrame;
		LocalDate dateOfSendToArchive;
		
		//Дата создания сетки, если возвращает не дату, ставим 9999 год
		dateOfCreation = getLocalDateFromRow(row.getCell(1));
		//Менеджер, если возвращает что то не то, создаем нового менеджера с именем - не определено
		if (DataBase.getInstance().getManagerByName(getStringFromCell(row.getCell(3), evaluator).trim()) != null) {
			manager = DataBase.getInstance().getManagerByName(getStringFromCell(row.getCell(3), evaluator).trim());
		} else if (!getStringFromCell(row.getCell(3), evaluator).equals("Не определено")) {
			manager = new Manager(DataBase.getInstance().nextId(Manager.class), getStringFromCell(row.getCell(3), evaluator).trim());
			DataBase.getInstance().getManagerList().add(manager);
		} else {
			manager = new Manager(DataBase.getInstance().nextId(Manager.class), "Не определено");
			DataBase.getInstance().getManagerList().add(manager);
		}
		//Компания, если вернуло не то, создаем новую компанию с именем - не определено
		if (DataBase.getInstance().getManagerByName(manager.getName()).getCompanyByName(getStringFromCell(row.getCell(2), evaluator).trim()) != null) {
			company = DataBase.getInstance().getManagerByName(manager.getName()).getCompanyByName(getStringFromCell(row.getCell(2), evaluator).trim());
		} else if(!getStringFromCell(row.getCell(2), evaluator).equals("Не определено")) {
			company = new Company(DataBase.getInstance().nextId(Company.class), getStringFromCell(row.getCell(2), evaluator).trim(), manager);
			DataBase.getInstance().getManagerByName(manager.getName()).getCompanyList().add(company);
			DataBase.getInstance().getAllCompanyList().add(company);
		} else {
			company = new Company(DataBase.getInstance().nextId(Company.class), "Не определено", manager);
			DataBase.getInstance().getManagerByName(manager.getName()).getCompanyList().add(company);
			DataBase.getInstance().getAllCompanyList().add(company);
		}
		//Тип сетки, если возвращает не то, создаем новый тип - не определено
		if (DataBase.getInstance().getTypeOfFrameByName(getStringFromCell(row.getCell(6), evaluator).trim()) != null) {
			typeOfFrame = DataBase.getInstance().getTypeOfFrameByName(getStringFromCell(row.getCell(6), evaluator).trim());
		} else if(!getStringFromCell(row.getCell(6), evaluator).equals("Не определено")) {
			typeOfFrame = new TypeOfFrame(DataBase.getInstance().nextId(TypeOfFrame.class), getStringFromCell(row.getCell(6), evaluator).trim());
			DataBase.getInstance().getTypeOfFrameList().add(typeOfFrame);
		} else {
			typeOfFrame = new TypeOfFrame(DataBase.getInstance().nextId(TypeOfFrame.class), "Не определено");
			DataBase.getInstance().getTypeOfFrameList().add(typeOfFrame);
		}

		if (DataBase.getInstance().getStatusByName(getStringFromCell(row.getCell(7), evaluator).trim()) != null) {
			statusOfFrame = DataBase.getInstance().getStatusByName(getStringFromCell(row.getCell(7), evaluator));
		} else if (!getStringFromCell(row.getCell(7), evaluator).trim().equals("Не определено")) {
			statusOfFrame = new StatusOfFrame(DataBase.getInstance().nextId(StatusOfFrame.class), getStringFromCell(row.getCell(7), evaluator).trim());
			DataBase.getInstance().getStatusOfFrameList().add(statusOfFrame);
		} else {
			statusOfFrame = new StatusOfFrame(DataBase.getInstance().nextId(StatusOfFrame.class), "Не определено");
			DataBase.getInstance().getStatusOfFrameList().add(statusOfFrame);
		}
		nameOfFrame = getStringFromCell(row.getCell(4), evaluator);
		numberOfLayout = getStringFromCell(row.getCell(5), evaluator);
		comment = getStringFromCell(row.getCell(10), evaluator);
		dateOfLastWork = getLocalDateFromRow(row.getCell(8));
		dateOfSendToArchive = getLocalDateFromRow(row.getCell(9));

		Frame frame = new Frame(DataBase.getInstance().nextId(Frame.class), nameOfFrame, manager, company, numberOfLayout, typeOfFrame, statusOfFrame);
		frame.setDateOfCreation(dateOfCreation);
		frame.setDateOfLastWork(dateOfLastWork);
		frame.setDateOfSendToArchive(dateOfSendToArchive);
		frame.setComment(comment);
		DataBase.getInstance().getFrameList().add(frame);
	}

	//Возвращает дату из ячейки xlsx таблицы
	private static LocalDate getLocalDateFromRow(Cell cell) {
		if(cell == null) return LocalDate.of(9999, 1, 1);
		
		if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
        	Date date = cell.getDateCellValue();
        	
			return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    	} else {
			return LocalDate.of(9999, 1, 1);
		}
	}

	//Возвращает строку из ячейки xslx таблицы
	private static String getStringFromCell(Cell cell, FormulaEvaluator evaluator) {
		if (cell == null) return "Не определено";

		DataFormatter formatter = new DataFormatter();
		String line = formatter.formatCellValue(cell, evaluator);
		
		if (line == null || line.isBlank() || line.isEmpty()) {
			return "Не определено";
		} else {
			return line;
		}
	}

}
