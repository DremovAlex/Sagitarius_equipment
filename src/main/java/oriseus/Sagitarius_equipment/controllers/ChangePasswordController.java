package oriseus.Sagitarius_equipment.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import oriseus.Sagitarius_equipment.model.DataBase;
import oriseus.Sagitarius_equipment.model.LogEntity;
import oriseus.Sagitarius_equipment.ports.LogLevel;
import oriseus.Sagitarius_equipment.ports.SettingsPage;
import oriseus.Sagitarius_equipment.utilities.LogHundler;

public class ChangePasswordController {

	@FXML
	private TextField newPasswordTextField;
	@FXML
	private TextField confirmNewPasswordTextField;
	
	@FXML
	private Label infoLabel;
	
	@FXML
	private Button okButton;
	@FXML
	private Button cancelButton;
	
	@FXML
	private void initialize() {
		infoLabel.setVisible(false);

		LogHundler.writeLogingMessage(new LogEntity(LogLevel.INFO, 
			"Открыто окно смены пароля"));
	}
	
	@FXML
	private void okButtonPressed() {
		if (!newPasswordTextField.getText().equals(confirmNewPasswordTextField.getText())) {
			infoLabel.setVisible(true);
			infoLabel.setText("Пароли не совпадают!");
			
			LogHundler.writeLogingMessage(new LogEntity(LogLevel.INFO, 
				"Пароли не совпадают"));
			return;
		}
		
		if (newPasswordTextField.getText().isBlank() || confirmNewPasswordTextField.getText().isBlank()) {
			infoLabel.setVisible(true);
			infoLabel.setText("Вы не ввели пароль!");
			
			LogHundler.writeLogingMessage(new LogEntity(LogLevel.INFO, 
				"Пароль не введен"));
			return;
		}
		
		if (newPasswordTextField.getText().equals(confirmNewPasswordTextField.getText())) {
			DataBase.getInstance().getUser().setPassword(newPasswordTextField.getText());
			infoLabel.setVisible(true);
			infoLabel.setText("Пароль успешно изменен!");
			
			LogHundler.writeLogingMessage(new LogEntity(LogLevel.INFO, 
				"Пароль изменен"));			
		}
	}
	
	@FXML
	private void cancelButtonPressed() {
					LogHundler.writeLogingMessage(new LogEntity(LogLevel.INFO, 
				"Отмена изменения пароля"));
	}
	
	public void setData(SettingsPage page) {
		
	}
}
