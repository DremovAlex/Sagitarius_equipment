package oriseus.Sagitarius_equipment.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import oriseus.Sagitarius_equipment.model.LogEntity;
import oriseus.Sagitarius_equipment.ports.LogLevel;
import oriseus.Sagitarius_equipment.ports.SettingsPage;
import oriseus.Sagitarius_equipment.ports.Theme;
import oriseus.Sagitarius_equipment.utilities.ConfigHundler;
import oriseus.Sagitarius_equipment.utilities.LogHundler;
import oriseus.Sagitarius_equipment.utilities.WindowManager;

public class AppearanceSettingsController {

    @FXML
    private ListView<Theme> themeListView;

    @FXML
    private CheckBox showConsoleCheckBox;

    @FXML
    private Button okButton;
    @FXML
    private Button cancelButton;

    private Theme selectedTheme;

    @FXML
    private void initialize() {
        themeListView.getItems().addAll(Theme.values());
        setListenerToThemeListView();
        showConsoleCheckBox.setSelected(setConsoleVisibleCheckBox());
    }

    @FXML
    private void okButtonPressed() {

        setConsoleVisible();

        if (selectedTheme != null) {
            setThemeToPoproperties(selectedTheme);
        }

        LogHundler.writeLogingMessage(new LogEntity(LogLevel.INFO, 
			"Закрыто окно настроек"));
		// WindowManager.closeWindow((Stage) okButton.getScene().getWindow());
    }

    @FXML
    private void cancelButtonPressed() {
        LogHundler.writeLogingMessage(new LogEntity(LogLevel.INFO, 
			"Закрыто окно настроек"));
//		WindowManager.closeWindow((Stage) cancelButton.getScene().getWindow());
    }

    public void setData(SettingsPage appearanceSettingsPage) {
        // TODO Auto-generated method stub
    }

    private void setConsoleVisible() {
        if (showConsoleCheckBox.isSelected()) {
            try {
                ConfigHundler.set("console.visible", "true");
            } catch (Exception e) {
                e.printStackTrace();
                LogHundler.writeLogingMessage(new LogEntity(LogLevel.ERROR, 
                    e.getMessage()));
            }
        } else {
            try {
                ConfigHundler.set("console.visible", "false");
            } catch (Exception e) {
                e.printStackTrace();
                LogHundler.writeLogingMessage(new LogEntity(LogLevel.ERROR, 
                    e.getMessage()));
            }
        }
    }

    //Возвращает значение видна ли консоль из property
    private boolean setConsoleVisibleCheckBox() {
        boolean isConsoleVisible;
        if (ConfigHundler.get("console.visible", "").equals("true")) {
            isConsoleVisible = true;
        } else {
            isConsoleVisible = false;
        }
        return isConsoleVisible;
    }
    
    //устанавливает слушатель на themelistview
    private void setListenerToThemeListView() {
        themeListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            selectedTheme = newVal;
        });
    }

    //записывает выбранную тему в properties
    private void setThemeToPoproperties(Theme theme) {
        try {
            ConfigHundler.set("theme", theme.getTitle());
        } catch (Exception e) {
            e.printStackTrace();
            LogHundler.writeLogingMessage(new LogEntity(LogLevel.ERROR, e.getMessage()));
        }
    }
}
