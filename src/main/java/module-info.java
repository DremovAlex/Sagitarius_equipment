module oriseus.Sagitarius_equipment {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.base;
	requires javafx.graphics;
	requires com.fasterxml.jackson.databind;
	requires com.fasterxml.jackson.dataformat.smile;
	requires com.fasterxml.jackson.datatype.jsr310;
	requires java.desktop;
    requires pdfbox;
    requires javafx.swing;

    opens oriseus.Sagitarius_equipment to javafx.fxml;
    opens oriseus.Sagitarius_equipment.controllers to javafx.fxml;
    opens oriseus.Sagitarius_equipment.model to javafx.fxml;
    opens oriseus.Sagitarius_equipment.utilities to javafx.fxml;
    opens oriseus.Sagitarius_equipment.ports;
    opens oriseus.Sagitarius_equipment.dto;
    
    exports oriseus.Sagitarius_equipment;
    exports oriseus.Sagitarius_equipment.controllers;
    exports oriseus.Sagitarius_equipment.model;
    exports oriseus.Sagitarius_equipment.utilities;
    exports oriseus.Sagitarius_equipment.ports;
    exports oriseus.Sagitarius_equipment.dto;
}
