package oriseus.Sagitarius_equipment.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.DirectoryChooser;
import oriseus.Sagitarius_equipment.model.LogEntity;
import oriseus.Sagitarius_equipment.ports.LogLevel;
import oriseus.Sagitarius_equipment.ports.SettingsPage;
import oriseus.Sagitarius_equipment.utilities.ConfigHundler;
import oriseus.Sagitarius_equipment.utilities.LogHundler;
import oriseus.Sagitarius_equipment.utilities.WindowManager;

public class DataBaseSettingController {

    @FXML
    private Label pathToDataBaseLabel;

    @FXML
    private Button changePathToDataBaseButton;

    @FXML
    private CheckBox deleteOldDatabaseCheckBox;

    @FXML
    private void initialize() {
        pathToDataBaseLabel.setText(setPathToDataBase());
    }

    @FXML
    private void changePathToDataBase() {
        Optional<ButtonType> result = WindowManager.showConfirmationWindow("Вы уверенны что хотите изменить расположение базы данных?", "");
        
        if (result.isPresent() && result.get() == ButtonType.OK) {

            String oldPathToDataBase = ConfigHundler.get("database.path", "");

            DirectoryChooser chooser = new DirectoryChooser();
            chooser.setTitle("Выберите папку");

            File selectedDirectory = chooser.showDialog(changePathToDataBaseButton.getScene().getWindow());

            if (selectedDirectory != null) {                
                try {
                    ConfigHundler.set("database.path", selectedDirectory.getAbsolutePath() + File.separator + "database.bin");
                    pathToDataBaseLabel.setText(setPathToDataBase());

                    if (deleteOldDatabaseCheckBox.isSelected()) {                  
                        saveDataBaseInNewPlaceAndDeleteOld(oldPathToDataBase, setPathToDataBase());
                    }

                } catch (Exception e) {
                    LogHundler.writeLogingMessage(new LogEntity(LogLevel.FATAL, e.getMessage()));
                }

            } else {
                LogHundler.writeLogingMessage(new LogEntity(LogLevel.ERROR, "База данных не выбрана!"));
            }
        }
    }

    //Устанавливаем путь к базе в label
    private String setPathToDataBase() {
        String pathToDataBase = ConfigHundler.get("database.path", "");
        return pathToDataBase;
    }

    public void setData(SettingsPage databasettings) {
        // TODO Auto-generated method stub
    }

    private void saveDataBaseInNewPlaceAndDeleteOld(String pathToOldDB, String pathToNewDB) {
        File oldDB = new File(pathToOldDB);
        File newDB = new File(pathToNewDB);

        try {
            Files.move(Path.of(oldDB.getAbsolutePath()), Path.of(newDB.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            LogHundler.writeLogingMessage(new LogEntity(LogLevel.FATAL, e.getMessage()));
        }
    }

}
