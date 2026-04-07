package oriseus.Sagitarius_equipment.controllers;

import java.time.format.DateTimeFormatter;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import oriseus.Sagitarius_equipment.model.Frame;

public class PrintController {

    @FXML
    GridPane gridPane;

    @FXML
    private void initialize() {
        gridPane.getColumnConstraints().clear();
    }

    public void setData(List<Frame> frameList) {

        int row = 0;
        for (Frame frame : frameList) {

            Label dateOfCreationCell = new Label("Дата создания: " + frame.getDateOfCreationFrame().get().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            dateOfCreationCell.setStyle("-fx-border-color: black; -fx-padding: 5;");
            gridPane.add(dateOfCreationCell, 0, row);
            Label managerCell = new Label(frame.getManager().getName());
            managerCell.setStyle("-fx-border-color: black; -fx-padding: 5;");
            gridPane.add(managerCell, 1, row);
            Label companyCell = new Label(frame.getCompany().getName());
            companyCell.setStyle("-fx-border-color: black; -fx-padding: 5;");
            gridPane.add(companyCell, 2, row);
            Label nameCell = new Label(frame.getName());
            nameCell.setStyle("-fx-border-color: black; -fx-padding: 5;");
            gridPane.add(nameCell, 3, row);
            Label numberOfLayoutCell = new Label(frame.getNumberOfLayout());
            numberOfLayoutCell.setStyle("-fx-border-color: black; -fx-padding: 5;");
            gridPane.add(numberOfLayoutCell, 4, row);
            Label typeOfFrameCell = new Label(frame.getTypeOfFrame().getName());
            typeOfFrameCell.setStyle("-fx-border-color: black; -fx-padding: 5;");
            gridPane.add(typeOfFrameCell, 5, row);
            
            if (frame.getDateOfLastWork().get() != null) {
                Label dateOfLastWorkCell = new Label("Дата последней работы: " + frame.getDateOfLastWork().get().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                dateOfLastWorkCell.setStyle("-fx-border-color: black; -fx-padding: 5;");
                gridPane.add(dateOfLastWorkCell, 6, row);
            } else {
                Label dateOfLastWorkCell = new Label("Дата последней работы: не была в работе."  );
                dateOfLastWorkCell.setStyle("-fx-border-color: black; -fx-padding: 5;");
                gridPane.add(dateOfLastWorkCell, 6, row);
            }
            
            Label commentCell = new Label(frame.getComment());
            commentCell.setStyle("-fx-border-color: black; -fx-padding: 5;");
            gridPane.add(commentCell, 7, row);
            Label statusOfFrameCell = new Label(frame.getStatusOfFrame().getName());
            statusOfFrameCell.setStyle("-fx-border-color: black; -fx-padding: 5;");
            gridPane.add(statusOfFrameCell, 8, row);
            
            row++;
        }
    }

}
