package oriseus.Sagitarius_equipment.utilities;

import javafx.scene.layout.Pane;

public class ThemeHundler {

	public void setCatppucinTheme(Pane pane) {
	    pane.sceneProperty().addListener((obs, oldScene, newScene) -> {
	        if (newScene != null) {
	        	newScene.getStylesheets().add(getClass()
	        		        .getResource("/oriseus/Sagitarius_equipment/styles/catppuccin/catppuccin-mocha.css")
	        		        .toExternalForm());
	        	}
	    });
	}
	
}
