package oriseus.Sagitarius_equipment.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import oriseus.Sagitarius_equipment.model.DataBase;
import oriseus.Sagitarius_equipment.model.Manager;
import oriseus.Sagitarius_equipment.model.StatusOfFrame;
import oriseus.Sagitarius_equipment.utilities.WindowManager;

public class AddNewManagerController {

	@FXML
	private TextField managerNameTextField;
	
	@FXML
	private Button okButton;
	@FXML
	private Button cancelButton;
	
	@FXML
	private void initialize() {
		
	}
	
	@FXML
	private void okButtonPressed() {
		if (!managerNameTextField.getText().isBlank()) {
			Manager manager = new Manager(DataBase.getInstance().getIdGenerator().next(Manager.class), managerNameTextField.getText());
			DataBase.getInstance().getManagerList().add(manager);

		    WindowManager.closeWindow((Stage) okButton.getScene().getWindow());
		}
	}
	
	@FXML
	private void cancelButtonPressed() {
		WindowManager.closeWindow((Stage) cancelButton.getScene().getWindow());
	}
}
