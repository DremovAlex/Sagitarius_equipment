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
import oriseus.Sagitarius_equipment.model.LogEntity;
import oriseus.Sagitarius_equipment.model.Manager;
import oriseus.Sagitarius_equipment.ports.LogLevel;
import oriseus.Sagitarius_equipment.utilities.Converters;
import oriseus.Sagitarius_equipment.utilities.LogHundler;
import oriseus.Sagitarius_equipment.utilities.ThemeHundler;
import oriseus.Sagitarius_equipment.utilities.WindowManager;

public class EditCompanyController {

	private Manager manager;
	private Company company;
	
	@FXML
	private VBox mainVBox;
	@FXML
	private HBox chouseManagerHBox;
	@FXML
	private HBox chouseCompanyHBox;
	@FXML
	private HBox newNameCompaneHBox;
	@FXML
	private HBox buttonsHBox;

	@FXML
	private ChoiceBox<Manager> managerChoiceBox;
	@FXML
	private ChoiceBox<Company> companyChoiceBox;
	
	@FXML
	private TextField companyNameTextField;
	
	@FXML
	private Button okButton;
	@FXML
	private Button cancelButton;
	
	@FXML
	private void initialize() {
		managerChoiceBox.getItems().addAll(DataBase.getInstance().getManagerList());
		initManagerAndCompanyChoiceBoxListener();
		managerChoiceBox.setConverter(Converters.simpleConverter(Manager::getName));
		
		companyChoiceBox.setConverter(Converters.simpleConverter(Company::getName));
	
		mainVBox.setAlignment(Pos.CENTER);
		chouseManagerHBox.setAlignment(Pos.CENTER);
		chouseCompanyHBox.setAlignment(Pos.CENTER);
		newNameCompaneHBox.setAlignment(Pos.CENTER);
		buttonsHBox.setAlignment(Pos.CENTER);

		new ThemeHundler().setTheme(mainVBox);
	}
	
	@FXML
	private void okButtonPressed() {
		if (manager != null && company != null && !companyNameTextField.getText().isBlank()) {
			DataBase.getInstance().getManagerByName(manager.getName()).getCompanyByName(company.getName()).setName(companyNameTextField.getText());
			
			LogHundler.writeLogingMessage(new LogEntity(LogLevel.INFO, 
				"Изменена компания - " + company.getName()));
			
			WindowManager.closeWindow((Stage) cancelButton.getScene().getWindow());
		}
	}
	
	@FXML
	private void cancelButtonPressed() {
		LogHundler.writeLogingMessage(new LogEntity(LogLevel.INFO, 
			"Отмена изменения компании - " + company.getName()));

		WindowManager.closeWindow((Stage) cancelButton.getScene().getWindow());
	}
	
	public void setData(Manager manager, Company company) {
		if (manager != null) {
			this.manager = manager;
			this.company = company;
			managerChoiceBox.getSelectionModel().select(DataBase.getInstance().getManagerByName(manager.getName()));
			companyChoiceBox.getSelectionModel().select(DataBase.getInstance().getManagerByName(manager.getName()).getCompanyByName(company.getName()));
		}
	}
	
	private void initManagerAndCompanyChoiceBoxListener() {
		managerChoiceBox.valueProperty().addListener((obs, oldManager, newManager) -> {
	        
			if (newManager == null) {
	        	companyChoiceBox.getItems().clear();
	        	companyChoiceBox.setDisable(true);
	            return;
	        }
	        companyChoiceBox.setDisable(false);
	        
	        manager = newManager;

	        companyChoiceBox.setItems((ObservableList<Company>) manager.getCompanyList());
	        companyChoiceBox.getSelectionModel().clearSelection();	
		});
		companyChoiceBox.valueProperty().addListener((obs, oldCompany, newCompany) -> {
			if (newCompany == null) {
	            return;
	        }
			
			company = newCompany;
	        companyNameTextField.setText(company.getName());
		});
	}
}
