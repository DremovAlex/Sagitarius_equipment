package oriseus.Sagitarius_equipment.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import oriseus.Sagitarius_equipment.model.Company;
import oriseus.Sagitarius_equipment.model.DataBase;
import oriseus.Sagitarius_equipment.model.Frame;
import oriseus.Sagitarius_equipment.model.LogEntity;
import oriseus.Sagitarius_equipment.model.Manager;
import oriseus.Sagitarius_equipment.model.TypeOfFrame;
import oriseus.Sagitarius_equipment.ports.LogLevel;
import oriseus.Sagitarius_equipment.utilities.Converters;
import oriseus.Sagitarius_equipment.utilities.LogHundler;
import oriseus.Sagitarius_equipment.utilities.ThemeHundler;
import oriseus.Sagitarius_equipment.utilities.WindowManager;

public class AddNewFrameController {

	private Manager manager;
	private Company company;
	private TypeOfFrame typeOfFrame;

	@FXML
	private VBox mainVBox;
	@FXML
	private HBox nameHBox;
	@FXML
	private HBox managerHBox;
	@FXML
	private HBox companyHBox;
	@FXML
	private HBox numberOfLayoutHBox;
	@FXML
	private HBox typeOfFrameHBox;
	@FXML
	private HBox buttonsHBox;
	
	@FXML
	private TextField frameNameTextField;
	@FXML
	private TextField numberOfLayoutTextField;
	
	@FXML
	private ChoiceBox<Manager> managerChoiceBox;
	@FXML
	private ChoiceBox<Company> companyChoiceBox;
	@FXML
	private ChoiceBox<TypeOfFrame> typeOfFrameChoiceBox;
	
	@FXML
	private Button okButton;
	@FXML
	private Button cancelButton;
	
	@FXML
	private void initialize() {		
		managerChoiceBox.getItems().addAll(DataBase.getInstance().getManagerList());
		managerChoiceBox.setConverter(Converters.simpleConverter(Manager::getName));
		
		companyChoiceBox.setDisable(true);
				
		managerChoiceBox.getSelectionModel()
	    .selectedItemProperty()
	    .addListener((obs, oldManager, newManager) -> {

	        if (newManager == null) {
	        	companyChoiceBox.getItems().clear();
	        	companyChoiceBox.setDisable(true);
	            return;
	        }

	        companyChoiceBox.setDisable(false);
	        
	        manager = newManager;

	        companyChoiceBox.setItems((ObservableList<Company>) newManager.getCompanyList());
	        companyChoiceBox.getSelectionModel().clearSelection();
	    });
		
		companyChoiceBox.valueProperty().addListener((obs, oldComapny, newCompany) -> {company = newCompany;});	
		
		companyChoiceBox.setConverter(Converters.simpleConverter(Company::getName));
			
		typeOfFrameChoiceBox.getItems().addAll(DataBase.getInstance().getTypeOfFrameList());
		typeOfFrameChoiceBox.valueProperty().addListener((obs, oldTypeOfFrame, newTypeOfFrame) -> {typeOfFrame = newTypeOfFrame;});	
		typeOfFrameChoiceBox.setConverter(Converters.simpleConverter(TypeOfFrame::getName));
	
		mainVBox.setAlignment(Pos.CENTER);
		nameHBox.setAlignment(Pos.CENTER);
		managerHBox.setAlignment(Pos.CENTER);
		companyHBox.setAlignment(Pos.CENTER);
		numberOfLayoutHBox.setAlignment(Pos.CENTER);
		typeOfFrameHBox.setAlignment(Pos.CENTER);
		buttonsHBox.setAlignment(Pos.CENTER);

		new ThemeHundler().setTheme(mainVBox);

		LogHundler.writeLogingMessage(new LogEntity(LogLevel.INFO,
			 "Открыто окно добавления новой сетки"));
	}
	
	@FXML
	private void okButtonPressed() {
		if (!frameNameTextField.getText().isBlank() && !numberOfLayoutTextField.getText().isBlank() &&
				manager != null && company != null && typeOfFrame != null) {
			Frame frame = new Frame(DataBase.getInstance().getIdGenerator().next(Frame.class), frameNameTextField.getText(), manager, company,
					numberOfLayoutTextField.getText(), typeOfFrame, DataBase.getInstance().getStatusOfFrameList().get(0));
			DataBase.getInstance().getFrameListByIsActual().add(frame);
			
			LogHundler.writeLogingMessage(new LogEntity(LogLevel.INFO,
				"Добавлена новая сетка - " + frame.getName()));

			WindowManager.closeWindow((Stage) okButton.getScene().getWindow());
		}
	}
	
	@FXML
	private void cancelButtonPressed() {
		LogHundler.writeLogingMessage(new LogEntity(LogLevel.INFO, 
			"Отмена добавления новой сетки"));
		WindowManager.closeWindow((Stage) cancelButton.getScene().getWindow());
	}
	
}
