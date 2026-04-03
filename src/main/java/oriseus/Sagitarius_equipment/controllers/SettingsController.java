package oriseus.Sagitarius_equipment.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import oriseus.Sagitarius_equipment.model.LogEntity;
import oriseus.Sagitarius_equipment.ports.LogLevel;
import oriseus.Sagitarius_equipment.ports.SettingsPage;
import oriseus.Sagitarius_equipment.utilities.LogHundler;
import oriseus.Sagitarius_equipment.utilities.WindowManager;

public class SettingsController {
	
	@FXML
	private BorderPane primaryBorderPane;
	
	@FXML
	private ListView<SettingsPage> menuList;
	
	@FXML
	private Button okButton;
	@FXML
	private Button cancelButton;
	
	@FXML
	private void initialize() {
		menuList.getItems().addAll(
		        SettingsPage.STATUSOFFRAME,
		        SettingsPage.TYPEOFFRAME,
		        SettingsPage.USERPASSWORD,
				SettingsPage.DATABASETTINGS);
		
		menuList.setCellFactory(lv -> new ListCell<>() {
		    @Override
		    protected void updateItem(SettingsPage item, boolean empty) {
		        super.updateItem(item, empty);
		        setText(empty || item == null ? null : item.getTitle());
		    }
		});
		
		menuList.getSelectionModel()
        .selectedItemProperty()
        .addListener((obs, old, selected) -> {
            if (selected != null) {
                showPage(selected);
            }
        });

		LogHundler.writeLogingMessage(new LogEntity(LogLevel.INFO, 
			"Открыто окно настроек"));
	}
	
	@FXML
	private void okButtonPressed() {
		LogHundler.writeLogingMessage(new LogEntity(LogLevel.INFO, 
			"Закрыто окно настроек"));
		WindowManager.closeWindow((Stage) cancelButton.getScene().getWindow());
	}
	
	@FXML
	private void cancelButtonPressed() {
		LogHundler.writeLogingMessage(new LogEntity(LogLevel.INFO, 
			"Закрыто окно настроек"));
		WindowManager.closeWindow((Stage) cancelButton.getScene().getWindow());
	}
	
	private void showPage(SettingsPage page) {

	    Node content = switch (page) {
	        case STATUSOFFRAME	->	openStatusOfFrameSettingsPage();
	        case TYPEOFFRAME	->	openTypeOfFrameSettingsPage();
	        case USERPASSWORD	->	openChangeUserPasswordPage();
			case DATABASETTINGS	-> openDataBaseSettingsPage();
	    };

	    primaryBorderPane.setCenter(content);
	}
	
	private Node openStatusOfFrameSettingsPage() {
		LogHundler.writeLogingMessage(new LogEntity(LogLevel.INFO, 
			"Выбрано дочернее окно настроек статуса сеток"));
		return WindowManager.loadView("/oriseus/Sagitarius_equipment/statusSettingsChildrenPage.fxml",  
				(ChildSettingPageController c) -> c.setData(SettingsPage.STATUSOFFRAME));
	}
	
	private Node openTypeOfFrameSettingsPage() {
		LogHundler.writeLogingMessage(new LogEntity(LogLevel.INFO, 
			"Выбрано дочернее окно настроек типов сеток"));
		return WindowManager.loadView("/oriseus/Sagitarius_equipment/statusSettingsChildrenPage.fxml", 
				(ChildSettingPageController c) -> c.setData(SettingsPage.TYPEOFFRAME));
	}
	
	private Node openChangeUserPasswordPage() {LogHundler.writeLogingMessage(new LogEntity(LogLevel.INFO, 
			"Выбрано дочернее окно настроек смены пароля"));
		return WindowManager.loadView("/oriseus/Sagitarius_equipment/changePasswordSettingsChildrenPage.fxml", 
				(ChangePasswordController c) -> c.setData(SettingsPage.USERPASSWORD)); 
						
	}

	private Node openDataBaseSettingsPage() {
		LogHundler.writeLogingMessage(new LogEntity(LogLevel.INFO, 
			"Выбрано дочернее окно настроек базы данных"));
		return WindowManager.loadView("/oriseus/Sagitarius_equipment/dataBaseSettingsChildrenPage.fxml", 
				(DataBaseSettingController c) -> c.setData(SettingsPage.DATABASETTINGS));
	}
}
