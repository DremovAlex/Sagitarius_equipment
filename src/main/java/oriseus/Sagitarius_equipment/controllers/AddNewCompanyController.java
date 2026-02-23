package oriseus.Sagitarius_equipment.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import oriseus.Sagitarius_equipment.model.Company;
import oriseus.Sagitarius_equipment.model.DataBase;
import oriseus.Sagitarius_equipment.model.Manager;
import oriseus.Sagitarius_equipment.model.StatusOfFrame;
import oriseus.Sagitarius_equipment.utilities.Converters;
import oriseus.Sagitarius_equipment.utilities.ThemeHundler;
import oriseus.Sagitarius_equipment.utilities.WindowManager;

public class AddNewCompanyController {
	
	private Manager manager;
	
	@FXML
	private VBox mainVBox;
	
	@FXML
	private ChoiceBox<Manager> managerChoiceBox;
	
	@FXML
	private TextField companyNameTextField;
	
	@FXML
	private Button okButton;
	@FXML
	private Button cancelButton;
	
	@FXML
	private void initialize() {		
		managerChoiceBox.getItems().addAll(DataBase.getInstance().getManagerList());
		managerChoiceBox.valueProperty().addListener((obs, oldManager, newManager) -> {manager = newManager;});	
		managerChoiceBox.setConverter(Converters.simpleConverter(Manager::getName));
		
		new ThemeHundler().setCatppucinTheme(mainVBox);
	}
	
	@FXML
	private void okButtonPressed() {
		if (!companyNameTextField.getText().isBlank() && manager != null) {
			Company company = new Company(DataBase.getInstance().getIdGenerator().next(Company.class), companyNameTextField.getText(), manager);
			manager.getCompanyList().add(company);
			
			WindowManager.closeWindow((Stage) okButton.getScene().getWindow());
		}
	}
	
	@FXML
	private void cancelButtonPressed() {
		WindowManager.closeWindow((Stage) cancelButton.getScene().getWindow());
	}
}
