package oriseus.Sagitarius_equipment.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import oriseus.Sagitarius_equipment.model.DataBase;
import oriseus.Sagitarius_equipment.model.LogEntity;
import oriseus.Sagitarius_equipment.model.Manager;
import oriseus.Sagitarius_equipment.ports.LogLevel;
import oriseus.Sagitarius_equipment.utilities.LogHundler;
import oriseus.Sagitarius_equipment.utilities.ThemeHundler;
import oriseus.Sagitarius_equipment.utilities.WindowManager;

public class AddNewManagerController {

	@FXML
	private VBox mainVBox;
	@FXML
	private HBox nameHBox;
	@FXML
	private HBox buttonsHBox;

	@FXML
	private TextField managerNameTextField;
	
	@FXML
	private Button okButton;
	@FXML
	private Button cancelButton;
	
	@FXML
	private void initialize() {
		mainVBox.setAlignment(Pos.CENTER);
		nameHBox.setAlignment(Pos.CENTER);
		buttonsHBox.setAlignment(Pos.CENTER);

		new ThemeHundler().setTheme(mainVBox);
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
