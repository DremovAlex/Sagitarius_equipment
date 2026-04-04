package oriseus.Sagitarius_equipment.utilities;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class ThemeHundler {
	
	public void setTheme(Pane pane) {
        String selectedTheme = ConfigHundler.get("theme", "");
        String pathToTheme = getThemePath(selectedTheme);

        pane.sceneProperty().addListener((obs, oldScene, scene) -> {
            if (scene != null) {
                applyTheme(scene, pathToTheme);
            }
        });

        if (pane.getScene() != null) {
            applyTheme(pane.getScene(), pathToTheme);
        }
    }

    private void applyTheme(Scene scene, String path) {
        scene.getStylesheets().clear();

        if (path != null && !path.isEmpty()) {
            scene.getStylesheets().add(
                getClass().getResource(path).toExternalForm()
            );
        }
    }

    private String getThemePath(String selectedTheme) {
        switch (selectedTheme) {
            case "Default light":
                return "/oriseus/Sagitarius_equipment/styles/default-light.css";
            case "Default dark":
                return "/oriseus/Sagitarius_equipment/styles/default-dark.css";
            case "Gruvbox dark":
                return "/oriseus/Sagitarius_equipment/styles/gruvbox-dark.css";
            case "Gruvbox light":
                return "/oriseus/Sagitarius_equipment/styles/gruvbox-light.css";
            case "Catppuccin latte":
                return "/oriseus/Sagitarius_equipment/styles/catppuccin-latte.css";
            case "Catppuccin mocha":
                return "/oriseus/Sagitarius_equipment/styles/catppuccin-mocha.css";
            default:
                return "";
        }
    }
	
}
