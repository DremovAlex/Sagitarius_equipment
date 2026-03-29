package oriseus.Sagitarius_equipment.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.smile.SmileFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import oriseus.Sagitarius_equipment.dto.DataBaseDTO;
import oriseus.Sagitarius_equipment.ports.FilterValue;
import oriseus.Sagitarius_equipment.utilities.ConfigHundler;
import oriseus.Sagitarius_equipment.utilities.DataBaseMapperHandler;
import oriseus.Sagitarius_equipment.utilities.IdGenerator;

public class DataBase {

	private static DataBase INSTANCE;
	private List<Frame> frameList;
	private List<Frame> archiveFrameList;
	private List<Manager> managerList;
	private List<TypeOfFrame> typeOfFrameList;
	private List<StatusOfFrame> statusOfFrameList;
	
	private User user;
	
	private boolean isActual;
	
	private final IdGenerator idGenerator;

	private DataBase() {
		frameList = FXCollections.observableArrayList();
		archiveFrameList = FXCollections.observableArrayList();
		managerList = FXCollections.observableArrayList();
		typeOfFrameList = FXCollections.observableArrayList();
		statusOfFrameList = FXCollections.observableArrayList();
	
		
		isActual = true;
		
		idGenerator = new IdGenerator();
		
		user = new User(idGenerator.next(User.class));	
		setFirstStatusOfFrames();
	}

	public static DataBase getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new DataBase();
	    }
		return INSTANCE;
	}

	//Возвращает актуальный лист или архивный, в зависимости от переменной
	public List<Frame> getFrameListByIsActual() {
		if(isActual) {
			return frameList;
		} else {
			return archiveFrameList;
		}
	}
	
	public List<Frame> getFrameList() {
		return frameList;
	}

	public void setFrameList(List<Frame> frameList) {
		this.frameList = frameList;
	}

	public List<Frame> getArchiveFrameList() {
		return archiveFrameList;
	}

	public void setArchiveFrameList(List<Frame> archiveFrameList) {
		this.archiveFrameList = archiveFrameList;
	}

	public boolean isActual() {
		return isActual;
	}

	public void setActual(boolean isActual) {
		this.isActual = isActual;
	}

//	public void setFrameList(List<Frame> frameList) {
//		this.frameList = frameList;
//	}

	public List<Manager> getManagerList() {
		return managerList;
	}

	public void setManagerList(List<Manager> managerList) {
		this.managerList = managerList;
	}

	public List<TypeOfFrame> getTypeOfFrameList() {
		return typeOfFrameList;
	}

	public void setTypeOfFrameList(List<TypeOfFrame> typeOfFrameList) {
		this.typeOfFrameList = typeOfFrameList;
	}

	public List<StatusOfFrame> getStatusOfFrameList() {
		return statusOfFrameList;
	}

	public void setStatusOfFrameList(List<StatusOfFrame> statusOfFrameList) {
		this.statusOfFrameList = statusOfFrameList;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public TypeOfFrame getTypeOfFrameByName(String type) {
		for (TypeOfFrame typeOfFrame : typeOfFrameList) {
			if (typeOfFrame.getName().equals(type)) {
				return typeOfFrame;
			}
		}
		return null;
	}
	
	public Frame getFrameByName(String name) {
		if (isActual) {
			for (Frame frame : frameList) {
				if (frame.getName().equals(name)) {
					return frame;
				}
			}
		} else {
			for (Frame frame : archiveFrameList) {
				if (frame.getName().equals(name)) {
					return frame;
				}
			}
		}
		return null;
	}
	
	public StatusOfFrame getStatusByName(String status) {
		for (StatusOfFrame statusOfFrame : statusOfFrameList) {
			if (statusOfFrame.getName().equals(status)) {
				return statusOfFrame;
			}
		}
		return null;
	}
	
	public Manager getManagerByName(String name) {
		for (Manager manager : managerList) {
			if (manager.getName().equals(name)) {
				return manager;
			}
		}
		return null;
	}

	//возвращает список всех компаний в базе
	public List<Company> getAllCompanyList() {
		List<Company> allCompanyList = FXCollections.observableArrayList();
		for (Manager manager : managerList) {
			allCompanyList.addAll(manager.getCompanyList());
		}
		return allCompanyList;
	}
	
	//возвращает список сеток по требуемому FilterValue
	public List<Frame> getFilteredByChoiceBoxList(FilterValue value) {
		List<Frame> filteredByChoiceBoxList = FXCollections.observableArrayList();
		for (Frame frame : getFrameListByIsActual()) {
			if (value instanceof Manager) {
				if (frame.getManager().getName().equals(value.getName())) {
					filteredByChoiceBoxList.add(frame);
				}
				continue;
			}
			if (value instanceof Company) {
				if (frame.getCompany().getName().equals(value.getName())) {
					filteredByChoiceBoxList.add(frame);
				}
				continue;
			}
			if (value instanceof TypeOfFrame) {
				if (frame.getTypeOfFrame().getName().equals(value.getName())) {
					filteredByChoiceBoxList.add(frame);
				}
				continue;
			}
			if (value instanceof StatusOfFrame) {
				if (frame.getStatusOfFrame().getName().equals(value.getName())) {
					filteredByChoiceBoxList.add(frame);
				}
				continue;
			}
		}
		return filteredByChoiceBoxList;
	}
	//возвращает список по требуемому тексту
	public List<Frame> getGetFilteredListByText(String tag, String userText) {
		List<Frame> filteredByTextList = FXCollections.observableArrayList();
		
		for (Frame frame : getFrameListByIsActual()) {
			
			switch (tag) {
				case "Показать все": 
					System.out.println("view all");
					if (frame.getDateOfLastWork().get() != null) {
						if (frame.getManager().getName().contains(userText) || frame.getManager().getName().contains(userText) ||
							frame.getCompany().getName().contains(userText) || frame.getTypeOfFrame().getName().contains(userText) || 
							frame.getStatusOfFrame().getName().contains(userText) || frame.getName().contains(userText) ||
							frame.getDateOfCreationFrame().get().toString().contains(userText) || frame.getNumberOfLayout().contains(userText) ||
							frame.getDateOfLastWork().get().toString().contains(userText)) {
							filteredByTextList.add(frame);
						} 
					} else {
							if (frame.getManager().getName().contains(userText) || frame.getManager().getName().contains(userText) ||
									frame.getCompany().getName().contains(userText) || frame.getTypeOfFrame().getName().contains(userText) || 
									frame.getStatusOfFrame().getName().contains(userText) || frame.getName().contains(userText) ||
									frame.getDateOfCreationFrame().get().toString().contains(userText) || frame.getNumberOfLayout().contains(userText)) {
									filteredByTextList.add(frame);
							}
					}
					
					break;
				case "Менеджерам": 
					if (frame.getManager().getName().contains(userText)) {
						filteredByTextList.add(frame);
					}
					break;
				case "Компаниям":
					if (frame.getCompany().getName().contains(userText)) {
						filteredByTextList.add(frame);
					}
					break;
				case "Типам сеток":
					if (frame.getTypeOfFrame().getName().contains(userText)) {
						filteredByTextList.add(frame);
					}
					break;
				case "Статусам сеток":
					if (frame.getStatusOfFrame().getName().contains(userText)) {
						filteredByTextList.add(frame);
					}
					break;
				case "Имени":
					if (frame.getName().contains(userText)) {
						filteredByTextList.add(frame);
					}
					break;
				case "По дате создания":
					if (frame.getDateOfCreationFrame().get().toString().contains(userText)) {
						filteredByTextList.add(frame);
					}
					break;
				case "По номеру оригинал макета":
					if (frame.getNumberOfLayout().contains(userText)) {
						filteredByTextList.add(frame);
					}
					break;	
				case "По дате последнего тиража":
					if (frame.getDateOfLastWork().get().toString().contains(userText)) {
						filteredByTextList.add(frame);
					}
					break;
				default:
					return filteredByTextList;

			}
		}
		
		

		return filteredByTextList;
	}
	
	public void setFrameToArchive(Frame frame) {
		frame.setDateOfSendToArchive(LocalDate.now());
		System.out.println(frame.getDateOfSendToArchive());
		archiveFrameList.add(frame);
		frameList.remove(frame);
	}
	
	public void setToActual(Frame frame) {
		frame.setDateOfSendToArchive(null);
		frameList.add(frame);
		archiveFrameList.remove(frame);
	}
	
	public void saveDataBase() throws IOException {
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
	    mapper.enable(SerializationFeature.INDENT_OUTPUT);
	    DataBaseDTO dto = DataBaseMapperHandler.toDto(INSTANCE);
//	    mapper.writeValue(new File("database.bin") , dto);
	   System.out.println(getPathToDB().toString());
	    mapper.writeValue(new File(getPathToDB().toString()) , dto);
	}
	
//	public void loadDataBase() throws StreamReadException, DatabindException, IOException {
//		ObjectMapper mapper = new ObjectMapper(new SmileFactory());
//		DataBaseDTO dto = mapper.readValue(new File("database.bin"), DataBaseDTO.class);
//		INSTANCE = DataBaseMapperHandler.fromDto(dto);
//	}
	
	public void loadDataBase() throws IOException {
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.registerModule(new JavaTimeModule()); // для LocalDate
	    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

	    // читаем DTO из JSON-файла
//	    DataBaseDTO dto = mapper.readValue(new File("database.bin"), DataBaseDTO.class);

	    DataBaseDTO dto = mapper.readValue(new File(getPathToDB().toString()), DataBaseDTO.class);
	    // заполняем существующий синглтон
	    DataBase db = DataBase.getInstance();


	    // используем маппер для переноса данных из DTO в Database
	    DataBaseMapperHandler.fromDto(dto);
	}
	
	private Path getPathToDB() {
		// Path appDir = Paths.get(System.getProperty("user.home"), ".sagitarius");

		// if (!Files.exists(appDir)) {
		//     try {
		// 		Files.createDirectories(appDir);
		// 	} catch (IOException e) {
		// 		// TODO Auto-generated catch block
		// 		e.printStackTrace();
		// 	}
		// }

		// return appDir.resolve("database.bin");

		Path pathToDataBase = Path.of(ConfigHundler.get("database.path", ""));
		return pathToDataBase;
	}
	
	//Возвращает long id переданного класса
    public long nextId(Class<?> type) {
        return idGenerator.next(type);
    }
 
/*	Под вопросом, нужен ли?	
	public void removeManagerByName(String name) {
		for (Manager manager : managerList) {
			if (manager.getName().equals(name)) {
				managerList.remove(manager);
				return;
			}
		}
	}
*/
	private void setFirstStatusOfFrames() {
		statusOfFrameList.add(new StatusOfFrame(idGenerator.next(StatusOfFrame.class), "В работе"));
		statusOfFrameList.add(new StatusOfFrame(idGenerator.next(StatusOfFrame.class), "На перетяжку"));
		statusOfFrameList.add(new StatusOfFrame(idGenerator.next(StatusOfFrame.class), "На смывку"));
		statusOfFrameList.add(new StatusOfFrame(idGenerator.next(StatusOfFrame.class), "Списана"));
	}

	public IdGenerator getIdGenerator() {
		return idGenerator;
	}
}
