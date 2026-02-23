package oriseus.Sagitarius_equipment.controllers;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Iterator;

import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Labeled;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import oriseus.Sagitarius_equipment.model.DataBase;
import oriseus.Sagitarius_equipment.model.Frame;
import oriseus.Sagitarius_equipment.model.Manager;
import oriseus.Sagitarius_equipment.model.StatusOfFrame;
import oriseus.Sagitarius_equipment.model.TypeOfFrame;
import oriseus.Sagitarius_equipment.ports.FilterValue;
import oriseus.Sagitarius_equipment.utilities.Converters;
import oriseus.Sagitarius_equipment.utilities.FileHundler;
import oriseus.Sagitarius_equipment.utilities.ThemeHundler;
import oriseus.Sagitarius_equipment.utilities.WindowManager;
import oriseus.Sagitarius_equipment.model.Company;

public class PrimaryController {
	
	private final String viewAll = "Показать все";
	private final String viewManagers = "Менеджерам";
	private final String viewCompanies = "Компаниям";
	private final String viewTypeOfFrames = "Типам сеток";
	private final String viewStatusOfFrames = "Статусам сеток";
	private final String viewName = "Имени";
	private final String viewDateOfCreation = "По дате создания";
	private final String viewNamberOfLayout = "По номеру оригинал макета";
	private final String viewDateOfLastWork = "По дате последнего тиража";
	
	private Manager currentManager;
	private Company currentCompany;
	private Frame currentFrame;
	private TypeOfFrame currentTypeOfFrame;
	private StatusOfFrame currentStatusOfFrame;
	
	private int indexOfImage;
	
	private String tag = "";
	
	@FXML
	private BorderPane mainPane;
	@FXML
	private TabPane frameTabPane;
	
	@FXML
	private ImageView imageView;
	@FXML
	private Button leftImageButton;
	@FXML
	private Button rightImageButton;
	
	@FXML
	private Menu frameTopMenu;
	@FXML
	private Menu managerTopMenu;
	@FXML
	private Menu companyTopMenu;
	@FXML
	private Menu settingsTopMenu;
	
	@FXML
	private MenuItem saveDataBaseMenuItem;
	@FXML
	private MenuItem loadDataBaseMenuItem;
	@FXML
	private MenuItem addNewFrameMenuItem;
	@FXML
	private MenuItem editFrameMenuItem;
	@FXML
	private MenuItem deleteFrameMenuItem;
	@FXML
	private MenuItem addNewManagerMenuItem;
	@FXML
	private MenuItem editManagerMenuItem;
	@FXML
	private MenuItem deleteManagerMenuItem;
	@FXML
	private MenuItem addNewCompanyMenuItem;
	@FXML
	private MenuItem editCompanyMenuItem;
	@FXML
	private MenuItem openSettingsMenuItem;
	@FXML
	private MenuItem setUserToSuperUserMenuItem;
	@FXML
	private MenuItem setSuperUserToUserMenuItem;
	
	@FXML
	private ToolBar optionViewToolBar;
	@FXML
	private ToolBar searchToolBar;
	
	@FXML
	private ChoiceBox<String> displayOptionChoiceBox;
	@FXML
	private ChoiceBox<FilterValue> displaysChoiceBox;
	@FXML
	private Button clearDispalysChoiceBoxesButton;
	
	@FXML
	private ChoiceBox<String> searchOptionChoiceBox;
	@FXML
	private TextField searchTextField;
	@FXML
	private Button clearButton;
	
	@FXML
	private TableView<Frame> frameTableView;
	@FXML
	private TableColumn<Frame, String> nameTableColumn;
	@FXML
	private TableColumn<Frame, String> managerTableColumn;
	@FXML
	private TableColumn<Frame, String> companyTableColumn;
	@FXML
	private TableColumn<Frame, LocalDate> frameCreateDateTableColumn;
	@FXML
	private TableColumn<Frame, String> numberOfLayoutTableColumn;
	@FXML
	private TableColumn<Frame, String> typeOfFrameTableColumn;
	@FXML
	private TableColumn<Frame, LocalDate> dateOfLastWorkTableColumn;
	@FXML
	private TableColumn<Frame, String> commentTableColumn;
	@FXML
	private TableColumn<Frame, String> statusOfFrameTableColumn;
	
	@FXML
	private Button archiveButton;
	
	@FXML
	private ContextMenu contextMenu;
	
	@FXML
	private MenuItem editMenuItem;
	
	@FXML
	private void initialize() {
		//автоматически растягивает все контейнеры и элементы вслед за окном
	    VBox.setVgrow(frameTabPane, Priority.ALWAYS);
	    frameTabPane.setMaxHeight(Double.MAX_VALUE);

	    frameTableView.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

	    AnchorPane.setTopAnchor(frameTableView, 0.0);
	    AnchorPane.setBottomAnchor(frameTableView, 0.0);
	    AnchorPane.setLeftAnchor(frameTableView, 0.0);
	    AnchorPane.setRightAnchor(frameTableView, 0.0);
		
	    if (DataBase.getInstance().isActual()) {
	    	archiveButton.setText("В архив");
	    } else {
	    	archiveButton.setText("В актуальные");
	    }
	    
	    setDisplayOptionsChoiceBox();
	    setSearchOptionChoiceBox();
	    initializeRightMouseClick();
	    
		//указываем колонкам что отображать
		nameTableColumn.setCellValueFactory(c -> c.getValue().frameNameProperty());		
		managerTableColumn.setCellValueFactory(c -> c.getValue().managerNameProperty());
		companyTableColumn.setCellValueFactory(c -> c.getValue().companyNameProperty());
		frameCreateDateTableColumn.setCellValueFactory(c -> c.getValue().createdDateProperty());	
		numberOfLayoutTableColumn.setCellValueFactory(c -> c.getValue().numberOfLayoutProperty());
		typeOfFrameTableColumn.setCellValueFactory(c -> c.getValue().typeOfFrameNameProperty());
		dateOfLastWorkTableColumn.setCellValueFactory(c -> c.getValue().dateOfLastWorkProperty());
		commentTableColumn.setCellValueFactory(c -> c.getValue().commentProperty());
		statusOfFrameTableColumn.setCellValueFactory(c -> c.getValue().nameStatusOfFrameProperty());
		
		//указываем таблице что отображать
		frameTableView.setItems((ObservableList<Frame>) DataBase.getInstance().getFrameListByIsActual());
		//добавляем слушателя таблице
		initTableSelectionListener();		

		//проверяем что юзер суперюзер и блокируем управление если это не так
		if (DataBase.getInstance().getUser().isSuperUser() == false) {
			setDisableEditing();
		}
		
		//блокируем клик правой кнопкой, если юзер не суперюзер
		frameTableView.addEventFilter(ContextMenuEvent.CONTEXT_MENU_REQUESTED, e -> {
		    if (!DataBase.getInstance().getUser().isSuperUser()) {
		        e.consume();
		    }
		});
		
//		new ThemeHundler().setCatppucinTheme(mainPane);

		setContextMenuToImageView();
	}
	
	@FXML
	private void toArchive() {
		if (DataBase.getInstance().isActual()) {
			archiveButton.setText("В актуальные");
			DataBase.getInstance().setActual(false);
			frameTableView.setItems((ObservableList<Frame>) DataBase.getInstance().getFrameListByIsActual());
		} else {
			archiveButton.setText("В архив");
			DataBase.getInstance().setActual(true);
			frameTableView.setItems((ObservableList<Frame>) DataBase.getInstance().getFrameListByIsActual());
		}
	}
	
	//Очищает поисковую строку
	@FXML
	private void searchFrameByOption() {
		searchTextField.setText("");
		frameTableView.setItems((ObservableList<Frame>) DataBase.getInstance().getFrameListByIsActual());
		searchOptionChoiceBox.getSelectionModel().select(0);
	}
	
	//Очищает choiceBoxes с критериями показа
	@FXML
	private void clearOptionsChoiceBoxes() {
		displayOptionChoiceBox.getSelectionModel().select(0);
	}
	
	private void addNewImageToFrame(Frame frame) {
		System.out.println("add new image");
		File imageFile = FileHundler.chooseImage((Stage) imageView.getScene().getWindow());
		
		String pathToImage = FileHundler.copyFileToDataBase(imageFile);
				
		frame.addImageToFrame(pathToImage);
		
		setDefaultImageToImageView(currentFrame);
		
	}
	
	private void deleteImageToFrame(Frame frame, int index) {
		frame.deleteImageFromFrame(index);
		setDefaultImageToImageView(currentFrame);
	}
	
	@FXML
	private void leftImageButtonPressed() {
		if(currentFrame == null) return;
		
		if(indexOfImage > 0) {
			--indexOfImage;
			setImageToImageView(currentFrame.getImageFrameList().get(indexOfImage));
		}		
	}
	
	@FXML
	private void rightImageButtonPressed() {		
		if(currentFrame == null) return;
		
		if(currentFrame.getImageFrameList().size() > indexOfImage + 1) {
			++indexOfImage;
			setImageToImageView(currentFrame.getImageFrameList().get(indexOfImage));
		}		
	}
	
	@FXML
	private void saveDataBase() {
		System.out.println("save database");
		try {
			DataBase.getInstance().saveDataBase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	private void loadDataBase() {
		System.out.println("load database");
		try {
			DataBase.getInstance().loadDataBase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	private void addNewFrame() {
	    WindowManager.openModalWindow("/oriseus/Sagitarius_equipment/addNewFrame.fxml", 
	    		"Добавление новой сетки", 
	    		frameTableView.getScene().getWindow());
	}
	
	@FXML
	private void editFrame() {
		EditFrameController controller = WindowManager.openModalWindowWithData(
	    		"/oriseus/Sagitarius_equipment/editFrame.fxml", "Редактирование сетки",
	    		frameTableView.getScene().getWindow(), c -> c.setData(currentManager, currentCompany, currentFrame));
	}
	
	@FXML
	private void deleteFrame() {
		if (currentFrame == null) return;		
		DataBase.getInstance().getFrameListByIsActual().remove(currentFrame);
	}
	
	@FXML
	private void addNewManager() throws IOException {	    
	    WindowManager.openModalWindow("/oriseus/Sagitarius_equipment/addNewManager.fxml", 
	    		"Добавление нового менеджера", 
	    		frameTableView.getScene().getWindow());
	}
	
	@FXML
	private void editManager() throws IOException {
		EditManagerController controller = WindowManager.openModalWindowWithData(
				"/oriseus/Sagitarius_equipment/editManager.fxml", "Редактирование менеджера",
				frameTableView.getScene().getWindow(), c -> c.setData(currentManager));
	}

	@FXML
	private void deleteManager() {
		//Узнать нужно ли, надо в цикле удалять все сетки, потом компании данного менеджера, потом его самого, по идее сетки должны уходить в архив
	}
	
	@FXML
	private void addNewCompany() {
	    WindowManager.openModalWindow("/oriseus/Sagitarius_equipment/addNewCompany.fxml", 
	    		"Добавление новой компании", 
	    		frameTableView.getScene().getWindow());
	}
	
	@FXML
	private void editCompany() throws IOException {
		EditCompanyController controller = WindowManager.openModalWindowWithData(
	    		"/oriseus/Sagitarius_equipment/editCompany.fxml", "Редактирование компании",
	    		frameTableView.getScene().getWindow(), c -> c.setData(currentManager, currentCompany));
	}
	
	@FXML
	private void openSettings() throws IOException {
	    WindowManager.openModalWindow("/oriseus/Sagitarius_equipment/settings.fxml", 
	    		"Настройки", 
	    		frameTableView.getScene().getWindow());
	}
	

	@FXML
	private void setUserToSuperUser() {
	    WindowManager.openModalWindow("/oriseus/Sagitarius_equipment/setUserToSuperUser.fxml", 
	    		"Настройки", 
	    		frameTableView.getScene().getWindow());
	    
	    if (DataBase.getInstance().getUser().isSuperUser()) {
	    	setEnableEditing();
	    }
	}
	
	@FXML
	private void setSuperUserToUser() {
		DataBase.getInstance().getUser().setSuperUser(false);
		setDisableEditing();
	}
	
	private void initTableSelectionListener() {
		frameTableView.getSelectionModel()
		.selectedItemProperty()
		.addListener((obs, oldFrame, newFrame) -> {
            
			if (newFrame != null) {
                currentManager = newFrame.getManager();
                currentCompany = newFrame.getCompany();
                currentFrame = newFrame;
                currentStatusOfFrame = newFrame.getStatusOfFrame();
                currentTypeOfFrame = newFrame.getTypeOfFrame();
                
                setDefaultImageToImageView(currentFrame);
            }
        });
	}
	
	//добавляет слушатели к choiceboxes и настраивает фильтрацию
	private void setDisplayOptionsChoiceBox() {
		displayOptionChoiceBox.setItems(FXCollections.observableArrayList(viewAll, viewManagers, viewCompanies,
																			viewTypeOfFrames, viewStatusOfFrames));
		displayOptionChoiceBox.getSelectionModel().select(0);
		
		displaysChoiceBox.setDisable(true);
		
		displayOptionChoiceBox.getSelectionModel()
		.selectedItemProperty()
		.addListener((obs, oldChoise, newChoise) -> {
            
			if (newChoise.equals(viewManagers)) {
				displaysChoiceBox.setDisable(false);
				displaysChoiceBox.getItems().clear();
				displaysChoiceBox.getItems().addAll(DataBase.getInstance().getManagerList());
				displaysChoiceBox.getSelectionModel().select(0);
            } else if (newChoise.equals(viewCompanies)) {
            	displaysChoiceBox.setDisable(false);
            	displaysChoiceBox.getItems().clear();
            	displaysChoiceBox.getItems().addAll(DataBase.getInstance().getAllCompanyList());
            	displaysChoiceBox.getSelectionModel().select(0);
            } else if (newChoise.equals(viewTypeOfFrames)) {
            	displaysChoiceBox.setDisable(false);
            	displaysChoiceBox.getItems().clear();
            	displaysChoiceBox.getItems().addAll(DataBase.getInstance().getTypeOfFrameList());
            	displaysChoiceBox.getSelectionModel().select(0);            	
            } else if (newChoise.equals(viewStatusOfFrames)) {
            	displaysChoiceBox.setDisable(false);
            	displaysChoiceBox.getItems().clear();
            	displaysChoiceBox.getItems().addAll(DataBase.getInstance().getStatusOfFrameList());
            	displaysChoiceBox.getSelectionModel().select(0);
            } else {
            	displaysChoiceBox.setDisable(true);
            	frameTableView.setItems((ObservableList<Frame>) DataBase.getInstance().getFrameListByIsActual());
            }
        });
		
		displaysChoiceBox.setConverter(Converters.displayNameConverter(FilterValue::getName));
		
		displaysChoiceBox.getSelectionModel()
		.selectedItemProperty()
		.addListener((obs, oldChoise, newChoise) -> {
			if (newChoise == null) {
				return;
	        }
			
			frameTableView.setItems((ObservableList<Frame>) DataBase.getInstance().getFilteredByChoiceBoxList(newChoise));           
        });
	}
	
	//ставит слушатели на choicebox и textfield
	private void setSearchOptionChoiceBox() {
		tag = viewAll;
		
		searchOptionChoiceBox.setItems(FXCollections.observableArrayList(viewAll,viewName, viewManagers, viewCompanies,
				viewDateOfCreation, viewNamberOfLayout, viewTypeOfFrames,viewDateOfLastWork, viewStatusOfFrames));
		searchOptionChoiceBox.getSelectionModel().select(0);
		
		searchOptionChoiceBox.getSelectionModel()
		.selectedItemProperty()
		.addListener((obs, oldChoise, newChoise) -> {
			tag = newChoise;
        });
		
		searchTextField.textProperty().addListener((obs, oldText, newText) -> {

		    if (newText != null && !newText.isBlank()) {
		    	frameTableView.setItems((ObservableList<Frame>) DataBase.getInstance().getGetFilteredListByText(tag, newText));
		    } else if (newText.equals("")) {
		    	frameTableView.setItems((ObservableList<Frame>) DataBase.getInstance().getFrameListByIsActual());
		    }
		});
	}
	
	private void initializeRightMouseClick() {
		frameTableView.setRowFactory(tableView -> {
		    TableRow<Frame> row = new TableRow<>();

		    ContextMenu menu = new ContextMenu();

		    MenuItem addNewFrame = new MenuItem("Добавить новую сетку");
		    MenuItem editFrame = new MenuItem("Редактировать сетку");
		    MenuItem deleteFrame = new MenuItem("Удалить сетку");
		    MenuItem addNewImage = new MenuItem("Добавить новое изображение");
		    
		    Menu changeStatusMenu = new Menu("Сменить статус сетки");

		    for (StatusOfFrame statusOfFrame : DataBase.getInstance().getStatusOfFrameList()) {
		        MenuItem item = new MenuItem(statusOfFrame.getName());
		        item.setOnAction(e -> changeStatusOfFrameByRightCkick(row.getItem(), statusOfFrame));
		        item.textProperty().bind(statusOfFrame.frameStatusProperty());
		        changeStatusMenu.getItems().add(item);
		    }
		    
		    menu.getItems().addAll(addNewFrame, editFrame, deleteFrame, changeStatusMenu, addNewImage);

		    addNewFrame.setOnAction(e -> addNewFrameByRightClick());
		    editFrame.setOnAction(e -> editFrameByRightClick(row.getItem()));
		    deleteFrame.setOnAction(e -> deleteFrameByRightClick(row.getItem()));
		    
		    addNewImage.setOnAction(e -> addNewImageToFrame(row.getItem()));
		    
		    row.contextMenuProperty().bind(
		        Bindings.when(row.emptyProperty())
		                .then((ContextMenu) null)
		                .otherwise(menu)
		    );

		    return row;
		});
	}
	
	//Добавляет новую сетку по правому клику
	private void addNewFrameByRightClick() {
		addNewFrame();
	}
	
	//вызывает окно редактирования сетки по правому клику
	private void editFrameByRightClick(Frame frame) {
		editFrame();
	}
	
	//удаляет сетку по правому клику
	private void deleteFrameByRightClick(Frame frame) {
		deleteFrame();
	}
	
	//меняет статус сетки по правому клику мыши
	//если статус списана, вызывается база и отправляет в архив
	//если статус в работе и показан архив, возвращается в актуальные
	private void changeStatusOfFrameByRightCkick(Frame frame, StatusOfFrame statusOfFrame) {
		frame.setStatusOfFrame(statusOfFrame);
		if(statusOfFrame.getName().equals(DataBase.getInstance().getStatusByName("Списана").getName()) && DataBase.getInstance().isActual()) {
			DataBase.getInstance().setFrameToArchive(frame);		
		}
		
		if(statusOfFrame.getName().equals(DataBase.getInstance().getStatusByName("В работе").getName()) && !DataBase.getInstance().isActual()) {	
			DataBase.getInstance().setToActual(frame);
		}
	}
	
	//Блокирует возможность редактирования
	private void setDisableEditing() {
		frameTopMenu.setDisable(true);
		managerTopMenu.setDisable(true);
		companyTopMenu.setDisable(true);
		settingsTopMenu.setDisable(true);
	}
	
	private void setEnableEditing() {
		frameTopMenu.setDisable(false);
		managerTopMenu.setDisable(false);
		companyTopMenu.setDisable(false);
		settingsTopMenu.setDisable(false);	
	}

	//Устанавливает первое изображение в списке сетки, если изображения нет, устанавливает пустой экран
	private void setDefaultImageToImageView(Frame frame) {
        
		if (frame.getImageFrameList().size() != 0) {
			String path = frame.getImageFrameList().get(0);
			Image image = new Image(Paths.get(path).toUri().toString(), 0, 0, true, true, true);
			imageView.setImage(image);
			
			indexOfImage = 0;
		} else {
			Image image = new Image(Paths.get("D:\\build\\Sagitarius-equipment-0.0.1\\images\\no-image.png").toUri().toString(), 0, 0, true, true, true);
			imageView.setImage(image);
		}
        
	}
	
	//Устанавливает изображение в imageView по переданному пути
	private void setImageToImageView(String pathToImage) {
		Image image = new Image(Paths.get(pathToImage).toUri().toString(), 0, 0, true, true, true);
		imageView.setImage(image);
	}
	
	private void openImageViewer(String path) {
		try {
			Desktop.getDesktop().open(new File(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Добавляет контекстное меню на imageview и вешает слушатель
	private void setContextMenuToImageView() {
		ContextMenu imageMenu = new ContextMenu();

		MenuItem addItem  = new MenuItem("Добавить изображение");
		MenuItem deleteItem = new MenuItem("Удалить изображение");

		imageMenu.getItems().addAll(addItem, deleteItem);
		
		imageView.setOnContextMenuRequested(e -> {
		    if (imageView.getImage() != null && DataBase.getInstance().getUser().isSuperUser()) {
		        imageMenu.show(imageView, e.getScreenX(), e.getScreenY());
		    }
		});
		
		imageView.setOnMouseClicked(e -> {
		    if (e.getClickCount() == 2 && imageView.getImage() != null) {
		        openImageViewer(currentFrame.getImageFrameList().get(indexOfImage));
		    }
		});
		
		addItem.setOnAction(e -> {
		    if (imageView.getImage() != null) {
		        addNewImageToFrame(currentFrame);
		    }
		});
		
		deleteItem.setOnAction(e -> {
		    if (imageView.getImage() != null) {
		        deleteImageToFrame(currentFrame, indexOfImage);
		    }
		});
	
	}
}
