package oriseus.Sagitarius_equipment.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import oriseus.Sagitarius_equipment.model.DataBase;
import oriseus.Sagitarius_equipment.utilities.WindowManager;

public class EnterPasswordController {
	
	@FXML
	private Label infoLabel;
	
	@FXML
	private TextField passwordTextField;
	
	@FXML
	private Button okButton;
	@FXML
	private Button cancelButton;
	
	@FXML
	private void initialize() {
		infoLabel.setVisible(false);
	}
	
	@FXML
	private void okButtonPressed() {
		if (passwordTextField.getText().isBlank()) return;
		
		if (passwordTextField.getText().equals(DataBase.getInstance().getUser().getPassword())) {
			DataBase.getInstance().getUser().setSuperUser(true);
			WindowManager.closeWindow((Stage) cancelButton.getScene().getWindow());
		} else {
			infoLabel.setVisible(true);
			infoLabel.setText("Неправильно введен пароль");
		}
	}
	
	@FXML
	private void cancelButtonPressed() {
		WindowManager.closeWindow((Stage) cancelButton.getScene().getWindow());
	}
}
