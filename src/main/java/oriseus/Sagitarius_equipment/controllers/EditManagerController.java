package oriseus.Sagitarius_equipment.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import oriseus.Sagitarius_equipment.model.DataBase;
import oriseus.Sagitarius_equipment.model.LogEntity;
import oriseus.Sagitarius_equipment.model.Manager;
import oriseus.Sagitarius_equipment.ports.LogLevel;
import oriseus.Sagitarius_equipment.utilities.Converters;
import oriseus.Sagitarius_equipment.utilities.LogHundler;
import oriseus.Sagitarius_equipment.utilities.ThemeHundler;
import oriseus.Sagitarius_equipment.utilities.WindowManager;

public class EditManagerController {
	
	private Manager manager;
	
	@FXML
	private VBox mainVBox;

	@FXML
	private ChoiceBox<Manager> managerChoiceBox;
	
	@FXML
	private TextField managerNameTextField;
	
	@FXML
	private Button okButton;
	@FXML
	private Button cancelButton;
	
	@FXML
	private void initialize() {
		managerChoiceBox.getItems().addAll(DataBase.getInstance().getManagerList());
		initManagerChoiceBoxListener();
		managerChoiceBox.setConverter(Converters.simpleConverter(Manager::getName));
	
		new ThemeHundler().setTheme(mainVBox);
	}
	
	@FXML
	private void okButtonPressed() {
		if (manager != null && !managerNameTextField.getText().isBlank()) {
			DataBase.getInstance().getManagerByName(manager.getName()).setName(managerNameTextField.getText());
			
			LogHundler.writeLogingMessage(new LogEntity(LogLevel.INFO, 
			"Изменен менеджер - " + manager.getName()));
			
			WindowManager.closeWindow((Stage) cancelButton.getScene().getWindow());
		}
	}
	
	@FXML
	private void cancelButtonPressed() {
		LogHundler.writeLogingMessage(new LogEntity(LogLevel.INFO, 
			"Отмена изменения менеджера - " + manager.getName()));		
		WindowManager.closeWindow((Stage) cancelButton.getScene().getWindow());
	}
	
	public void setData(Manager manager) {
		if (manager != null) {
			this.manager = manager;
			managerChoiceBox.getSelectionModel().select(DataBase.getInstance().getManagerByName(manager.getName()));
		}
	}
	
	private void initManagerChoiceBoxListener() {
		managerChoiceBox.valueProperty().addListener((obs, oldManager, newManager) -> {
			manager = newManager;
			managerNameTextField.setText(manager.getName());
		});
	}
}
