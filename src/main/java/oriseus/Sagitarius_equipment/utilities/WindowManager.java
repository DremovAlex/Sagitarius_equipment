package oriseus.Sagitarius_equipment.utilities;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Consumer;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import oriseus.Sagitarius_equipment.App;
import javafx.scene.Parent;

public class WindowManager {

	public static void openModalWindow(String pathToFXML, String title, Window window) {
		try {
			FXMLLoader loader = new FXMLLoader(WindowManager.class.getResource(pathToFXML));
		    Parent root = loader.load();
		    Stage stage = new Stage();
		    stage.setScene(new Scene(root));
		    stage.setTitle(title);
		    stage.initModality(Modality.WINDOW_MODAL);
		    stage.initOwner(window);
	
		    stage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException("Cannot open window: " + pathToFXML, e);
        }
	}
	
	public static <C> C openModalWindowWithData(String pathToFXML, String title, Window window, Consumer<C> controllerInit) {
		try {
            FXMLLoader loader = new FXMLLoader(WindowManager.class.getResource(pathToFXML));
            Parent root = loader.load();
            C controller = loader.getController();

            if (controllerInit != null) {
                controllerInit.accept(controller);
            }

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.initOwner(window);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();

            return controller;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
	
	public static <T> Node loadView(String fxml, Consumer<T> controllerInit) {
	    try {
	        FXMLLoader loader = new FXMLLoader(App.class.getResource(fxml));
	        Parent view = loader.load();

	        T controller = loader.getController();
	        controllerInit.accept(controller);

	        return view;
	    } catch (IOException e) {
	        throw new RuntimeException(e);
	    }
	}
	
	public static void closeWindow(Stage stage) {
		stage.close();
	}
	
	public static Optional<ButtonType> showConfirmationWindow(String headerText, String contettext) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Подтверждение");
		alert.setHeaderText(headerText);
		alert.setContentText(contettext);
		
		return alert.showAndWait();
	}

}
