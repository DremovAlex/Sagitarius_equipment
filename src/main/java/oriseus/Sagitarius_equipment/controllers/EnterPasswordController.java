package oriseus.Sagitarius_equipment.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import oriseus.Sagitarius_equipment.model.DataBase;
import oriseus.Sagitarius_equipment.model.LogEntity;
import oriseus.Sagitarius_equipment.ports.LogLevel;
import oriseus.Sagitarius_equipment.utilities.LogHundler;
import oriseus.Sagitarius_equipment.utilities.ThemeHundler;
import oriseus.Sagitarius_equipment.utilities.WindowManager;

public class EnterPasswordController {
	
	@FXML
	private VBox mainVBox;

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

		new ThemeHundler().setTheme(mainVBox);

		LogHundler.writeLogingMessage(new LogEntity(LogLevel.INFO, 
			"Открыто окно ввода пароля"));
	}
	
	@FXML
	private void okButtonPressed() {
		if (passwordTextField.getText().isBlank()) {
			LogHundler.writeLogingMessage(new LogEntity(LogLevel.INFO, 
			"Поле ввода пароля пустое"));
			return;
		}
		
		if (passwordTextField.getText().equals(DataBase.getInstance().getUser().getPassword())) {
			DataBase.getInstance().getUser().setSuperUser(true);
			
			LogHundler.writeLogingMessage(new LogEntity(LogLevel.INFO, 
			"Введен пароль, получены права суперпользователя"));

			WindowManager.closeWindow((Stage) cancelButton.getScene().getWindow());
		} else {
			infoLabel.setVisible(true);
			infoLabel.setText("Неправильно введен пароль");

			LogHundler.writeLogingMessage(new LogEntity(LogLevel.INFO, 
			"Неправильный ввод пароля"));
		}
	}
	
	@FXML
	private void cancelButtonPressed() {
		LogHundler.writeLogingMessage(new LogEntity(LogLevel.INFO, 
			"Закрытие окна ввода пароля"));
		WindowManager.closeWindow((Stage) cancelButton.getScene().getWindow());
	}
}
