package oriseus.Sagitarius_equipment.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import oriseus.Sagitarius_equipment.model.DataBase;
import oriseus.Sagitarius_equipment.model.LogEntity;
import oriseus.Sagitarius_equipment.model.Manager;
import oriseus.Sagitarius_equipment.ports.LogLevel;
import oriseus.Sagitarius_equipment.utilities.LogHundler;
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
		LogHundler.writeLogingMessage(new LogEntity(LogLevel.INFO, 
			"Открыто окно добавления нового менеджера"));
	}
	
	@FXML
	private void okButtonPressed() {
		if (!managerNameTextField.getText().isBlank()) {
			Manager manager = new Manager(DataBase.getInstance().getIdGenerator().next(Manager.class), managerNameTextField.getText());
			DataBase.getInstance().getManagerList().add(manager);

			LogHundler.writeLogingMessage(new LogEntity(LogLevel.INFO, 
				"Добавление нового менеджера"));
		    WindowManager.closeWindow((Stage) okButton.getScene().getWindow());
		}
	}
	
	@FXML
	private void cancelButtonPressed() {
		LogHundler.writeLogingMessage(new LogEntity(LogLevel.INFO, 
				"Отмена добавление нового менеджера"));
		WindowManager.closeWindow((Stage) cancelButton.getScene().getWindow());
	}
}
