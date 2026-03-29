package oriseus.Sagitarius_equipment.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import oriseus.Sagitarius_equipment.ports.SettingsPage;
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
	}
	
	@FXML
	private void okButtonPressed() {
		WindowManager.closeWindow((Stage) cancelButton.getScene().getWindow());
	}
	
	@FXML
	private void cancelButtonPressed() {
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
		return WindowManager.loadView("/oriseus/Sagitarius_equipment/statusSettingsChildrenPage.fxml",  
				(ChildSettingPageController c) -> c.setData(SettingsPage.STATUSOFFRAME));
	}
	
	private Node openTypeOfFrameSettingsPage() {
		return WindowManager.loadView("/oriseus/Sagitarius_equipment/statusSettingsChildrenPage.fxml", 
				(ChildSettingPageController c) -> c.setData(SettingsPage.TYPEOFFRAME));
	}
	
	private Node openChangeUserPasswordPage() {
		return WindowManager.loadView("/oriseus/Sagitarius_equipment/changePasswordSettingsChildrenPage.fxml", 
				(ChangePasswordController c) -> c.setData(SettingsPage.USERPASSWORD)); 
						
	}

	private Node openDataBaseSettingsPage() {
		return WindowManager.loadView("/oriseus/Sagitarius_equipment/dataBaseSettingsChildrenPage.fxml", 
				(DataBaseSettingController c) -> c.setData(SettingsPage.DATABASETTINGS));
	}
}
