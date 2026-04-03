package oriseus.Sagitarius_equipment.controllers;

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Optional;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import oriseus.Sagitarius_equipment.model.DataBase;
import oriseus.Sagitarius_equipment.model.Frame;
import oriseus.Sagitarius_equipment.model.LogEntity;
import oriseus.Sagitarius_equipment.model.Manager;
import oriseus.Sagitarius_equipment.model.StatusOfFrame;
import oriseus.Sagitarius_equipment.model.TypeOfFrame;
import oriseus.Sagitarius_equipment.ports.FilterValue;
import oriseus.Sagitarius_equipment.ports.LogLevel;
import oriseus.Sagitarius_equipment.utilities.Converters;
import oriseus.Sagitarius_equipment.utilities.FileHundler;
import oriseus.Sagitarius_equipment.utilities.LogHundler;
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
	private ImageView pdfImageView;
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
	private TableColumn<Frame, LocalDate> dateOfSendToArchive;
	
	@FXML
	private Button archiveButton;
	
	@FXML
	private ContextMenu contextMenu;
	
	@FXML
	private MenuItem editMenuItem;
	
	@FXML
	private ScrollPane scrollPane;
	@FXML
	private TextFlow textFlow;
	
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
		dateOfSendToArchive.setCellValueFactory(c -> c.getValue().getDateOfSendToArchive());
		
		//указываем таблице что отображать
		frameTableView.setItems((ObservableList<Frame>) DataBase.getInstance().getFrameListByIsActual());
		//добавляем слушателя таблице
		initTableSelectionListener();
		//Устанавлиываем колонку даты списания невидимой
		dateOfSendToArchive.setVisible(false);

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

		setListenerToPdfImageView();
		setContextMenuToImageView();
		
		scrollPane.layout();
		scrollPane.setVvalue(1.0);

		LogHundler.writeLogingMessage(new LogEntity(LogLevel.INFO, 
			"Приложение запущенно"));
	}
	
	//Переключает отображение актуальных сеток и архива
	@FXML
	private void toArchive() {
		if (DataBase.getInstance().isActual()) {
			archiveButton.setText("В актуальные");
			DataBase.getInstance().setActual(false);
			frameTableView.setItems((ObservableList<Frame>) DataBase.getInstance().getFrameListByIsActual());
			dateOfSendToArchive.setVisible(true);

			LogHundler.writeLogingMessage(new LogEntity(LogLevel.INFO, 
			"Нажата кнопка в актульные"));
		} else {
			archiveButton.setText("В архив");
			DataBase.getInstance().setActual(true);
			frameTableView.setItems((ObservableList<Frame>) DataBase.getInstance().getFrameListByIsActual());
			dateOfSendToArchive.setVisible(false);

			LogHundler.writeLogingMessage(new LogEntity(LogLevel.INFO, 
			"Нажата кнопка в архив"));
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
	
	private void addNewPdfToFrame(Frame frame) {
		File pdfFile = FileHundler.chosePdfFile((Stage) pdfImageView.getScene().getWindow());
		String pathToPdfFile = FileHundler.copyPdfFFileToDataBase(pdfFile);
		frame.setPathToPdf(pathToPdfFile);
		
		setPdfFileToPreview(currentFrame);

		logging(LogLevel.INFO, "Добавлен ПДФ файл");
	}
	//Удаляет макет с сетки
	//Забивает стринг налом, потом слеать при иницализации пустую строку и проверять по ней
	private void deletePdfToFrame(Frame frame) {
		frame.setPathToPdf(null);
		logging(LogLevel.INFO, "Удален ПДФ файл");
	}

	private void addNewImageToFrame(Frame frame) {
		System.out.println("add new image");
		File imageFile = FileHundler.chooseImage((Stage) imageView.getScene().getWindow());
		
		String pathToImage = FileHundler.copyFileToDataBase(imageFile);
				
		frame.addImageToFrame(pathToImage);
		
		setDefaultImageToImageView(currentFrame);

		logging(LogLevel.INFO, "Изображение добавлено");
		
	}
	
	private void deleteImageToFrame(Frame frame, int index) {
		frame.deleteImageFromFrame(index);
		setDefaultImageToImageView(currentFrame);

		logging(LogLevel.INFO, "Изображение удалено");
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
	
	//Сохраняет базу данных, пишет об этом в лог
	@FXML
	private void saveDataBase() {

		Optional<ButtonType> result = WindowManager.showConfirmationWindow("Вы уверенны что хотите сохранить базу данных?", "");

		if (result.isPresent() && result.get() == ButtonType.OK) {
			try {
				DataBase.getInstance().saveDataBase();
				logging(LogLevel.INFO, "База данных сохранена");
			} catch (IOException e) {
				textFlow.getChildren().add(new Text(e.getMessage()));
				logging(LogLevel.WARNING, e.getMessage());
//				e.printStackTrace();
			}
		} else {
			logging(LogLevel.INFO, "Отмена сохранения базы данных");
		}
	}
	
	//Загружает базу данных, пишет в лог
	@FXML
	private void loadDataBase() {
		
		Optional<ButtonType> result = WindowManager.showConfirmationWindow("Вы уверенны что хотите загрузить базу данных?", "");

		if (result.isPresent() && result.get() == ButtonType.OK) {
			try {
				DataBase.getInstance().loadDataBase();
				logging(LogLevel.INFO, "База данных загружена");
			} catch (IOException e) {
				logging(LogLevel.WARNING, e.getMessage());
//				e.printStackTrace();
			}
		} else {
			logging(LogLevel.INFO, "Отмена загрузки базы данных");
		}

	}
	
	@FXML
	private void addNewFrame() {
	    WindowManager.openModalWindow("/oriseus/Sagitarius_equipment/addNewFrame.fxml", 
	    		"Добавление новой сетки", 
	    		frameTableView.getScene().getWindow());
				
				logging(LogLevel.INFO, "Добавлена новая сетка");
	}
	
	@FXML
	private void editFrame() {
		EditFrameController controller = WindowManager.openModalWindowWithData(
	    		"/oriseus/Sagitarius_equipment/editFrame.fxml", "Редактирование сетки",
	    		frameTableView.getScene().getWindow(), c -> c.setData(currentManager, currentCompany, currentFrame));
	
				logging(LogLevel.INFO, "Редактирование сетки");
	}
	
	@FXML
	private void deleteFrame() {
		if (currentFrame == null) return;		
		DataBase.getInstance().getFrameListByIsActual().remove(currentFrame);
		
		logging(LogLevel.INFO, "Удалена сетка");
	}
	
	@FXML
	private void addNewManager() throws IOException {	    
	    WindowManager.openModalWindow("/oriseus/Sagitarius_equipment/addNewManager.fxml", 
	    		"Добавление нового менеджера", 
	    		frameTableView.getScene().getWindow());

		logging(LogLevel.INFO, "Добавлен менеджер");
	}
	
	@FXML
	private void editManager() throws IOException {
		EditManagerController controller = WindowManager.openModalWindowWithData(
				"/oriseus/Sagitarius_equipment/editManager.fxml", "Редактирование менеджера",
				frameTableView.getScene().getWindow(), c -> c.setData(currentManager));
	
		logging(LogLevel.INFO, "Редактирование менеджера");	
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

		logging(LogLevel.INFO, "Добавлена новая компания");
	}
	
	@FXML
	private void editCompany() throws IOException {
		EditCompanyController controller = WindowManager.openModalWindowWithData(
	    		"/oriseus/Sagitarius_equipment/editCompany.fxml", "Редактирование компании",
	    		frameTableView.getScene().getWindow(), c -> c.setData(currentManager, currentCompany));
	
		logging(LogLevel.INFO, "редактирование компании");
	}
	
	@FXML
	private void openSettings() throws IOException {
	    WindowManager.openModalWindow("/oriseus/Sagitarius_equipment/settings.fxml", 
	    		"Настройки", 
	    		frameTableView.getScene().getWindow());

		logging(LogLevel.INFO, "Открыты настройки");
	}
	

	@FXML
	private void setUserToSuperUser() {
	    WindowManager.openModalWindow("/oriseus/Sagitarius_equipment/setUserToSuperUser.fxml", 
	    		"Настройки", 
	    		frameTableView.getScene().getWindow());
	    
	    if (DataBase.getInstance().getUser().isSuperUser()) {
	    	setEnableEditing();

			logging(LogLevel.INFO, "Добавлены првелегии суперпользователя");
	    }
	}
	
	@FXML
	private void setSuperUserToUser() {
		DataBase.getInstance().getUser().setSuperUser(false);
		setDisableEditing();

		logging(LogLevel.INFO, "Убраны привелегии суперпользователя");
	}
	
	//Добавляет слушателя на таблицу
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
                
				setPdfFileToPreview(currentFrame);
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
	
	//подключает контекстное меню по правому щелчку мыши
	private void initializeRightMouseClick() {
		frameTableView.setRowFactory(tableView -> {
		    TableRow<Frame> row = new TableRow<>();

		    ContextMenu menu = new ContextMenu();

		    MenuItem addNewFrame = new MenuItem("Добавить новую сетку");
		    MenuItem editFrame = new MenuItem("Редактировать сетку");
		    MenuItem deleteFrame = new MenuItem("Удалить сетку");
		    MenuItem addNewPdf = new MenuItem("Добавить оригинал макет");
			MenuItem addNewImage = new MenuItem("Добавить новое изображение");
		    
		    Menu changeStatusMenu = new Menu("Сменить статус сетки");

		    for (StatusOfFrame statusOfFrame : DataBase.getInstance().getStatusOfFrameList()) {
		        MenuItem item = new MenuItem(statusOfFrame.getName());
		        item.setOnAction(e -> changeStatusOfFrameByRightCkick(row.getItem(), statusOfFrame));
		        item.textProperty().bind(statusOfFrame.frameStatusProperty());
		        changeStatusMenu.getItems().add(item);
		    }
		    
		    menu.getItems().addAll(addNewFrame, editFrame, deleteFrame, changeStatusMenu,addNewPdf, addNewImage);

		    addNewFrame.setOnAction(e -> addNewFrameByRightClick());
		    editFrame.setOnAction(e -> editFrameByRightClick(row.getItem()));
		    deleteFrame.setOnAction(e -> deleteFrameByRightClick(row.getItem()));
		    
			addNewPdf.setOnAction(e -> addNewPdfToFrame(row.getItem()));
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
	
	//открывает возможность редактирования
	private void setEnableEditing() {
		frameTopMenu.setDisable(false);
		managerTopMenu.setDisable(false);
		companyTopMenu.setDisable(false);
		settingsTopMenu.setDisable(false);	
	}

	//устанавливает превью из pdf файла, иначе показывает дефолтное изображение
	private void setPdfFileToPreview(Frame frame) {
		if (frame.getPathToPdf() != null) {
			File pdfFile = new File(frame.getPathToPdf());

			Image image;
			try {
				image = generatePreview(pdfFile);
				pdfImageView.setImage(image); 
			} catch (IOException e) {
				e.printStackTrace();
				logging(LogLevel.ERROR, e.getMessage());
			}
		} else {
			Image image = new Image(Paths.get(FileHundler.getPathToImageFolder() + File.separator + "ImageNotFound.png").toUri().toString(), 0, 0, true, true, true);
			pdfImageView.setImage(image);
		}
	}

	//Устанавливает первое изображение в списке сетки, если изображения нет, устанавливает пустой экран
	private void setDefaultImageToImageView(Frame frame) {
        
		if (frame.getImageFrameList().size() != 0) {
			String path = frame.getImageFrameList().get(0);
			Image image = new Image(Paths.get(path).toUri().toString(), 0, 0, true, true, true);
			imageView.setImage(image);
			
			indexOfImage = 0;
		} else {
			System.out.println(FileHundler.getPathToImageFolder() + "ImageNotFound.png");
			Image image = new Image(Paths.get(FileHundler.getPathToImageFolder() + File.separator + "ImageNotFound.png").toUri().toString(), 0, 0, true, true, true);
			imageView.setImage(image);
		}
        
	}
	
	//Устанавливает изображение в imageView по переданному пути
	private void setImageToImageView(String pathToImage) {
		Image image = new Image(Paths.get(pathToImage).toUri().toString(), 0, 0, true, true, true);
		imageView.setImage(image);
	}
	
	//Открывает средствами ОС файл
	private void openImageViewer(String path) {
		try {
			Desktop.getDesktop().open(new File(path));

			logging(LogLevel.INFO, "Открыт файл");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logging(LogLevel.ERROR, e.getMessage());
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

	//Добавляет слушателя к окну превью пдф
	private void setListenerToPdfImageView() {
		ContextMenu imageMenu = new ContextMenu();

		MenuItem addItem  = new MenuItem("Добавить оригинал-макет");
		MenuItem deleteItem = new MenuItem("Удалить оригинал-макет");

		imageMenu.getItems().addAll(addItem, deleteItem);
		
		pdfImageView.setOnContextMenuRequested(e -> {
		    if (pdfImageView.getImage() != null && DataBase.getInstance().getUser().isSuperUser()) {
		        imageMenu.show(pdfImageView, e.getScreenX(), e.getScreenY());
		    }
		});

		pdfImageView.setOnMouseClicked(e -> {
		    if (e.getClickCount() == 2 && imageView.getImage() != null) {
		        openImageViewer(currentFrame.getPathToPdf());
		    }
		});

				addItem.setOnAction(e -> {
		    if (imageView.getImage() != null) {
		        addNewPdfToFrame(currentFrame);
		    }
		});
		
		deleteItem.setOnAction(e -> {
		    if (imageView.getImage() != null) {
		        deletePdfToFrame(currentFrame);
		    }
		});
	}
	
	//Добавляет данные в логи и textFlow
	private void logging(LogLevel logLevel, String text) {	
		LogEntity logEntity = new LogEntity(logLevel, text);
		LogHundler.writeLogingMessage(logEntity);
		textFlow.getChildren().add(new Text(logEntity.getFullLogMessage() + "\n"));
	}

	//Превращает пдф в изображение для сохдания превью
	public Image generatePreview(File file) throws IOException {
    try (PDDocument document = PDDocument.load(file)) {
        PDFRenderer renderer = new PDFRenderer(document);

        BufferedImage image = renderer.renderImageWithDPI(0, 72); // низкий DPI для превью
        return SwingFXUtils.toFXImage(image, null);
    }
}
}
