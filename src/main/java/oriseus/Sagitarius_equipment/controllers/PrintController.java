package oriseus.Sagitarius_equipment.controllers;

import java.time.format.DateTimeFormatter;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import oriseus.Sagitarius_equipment.model.Frame;
import oriseus.Sagitarius_equipment.utilities.PrintService;
import oriseus.Sagitarius_equipment.utilities.WindowManager;

public class PrintController {

    @FXML
    GridPane gridPane;

    @FXML
    private void initialize() {
        gridPane.getColumnConstraints().clear();
        initializeRightMouseClick();
    }

    public void setData(List<Frame> frameList) {

        int row = 0;

        gridPane.add(createCell("Дата создания"), 0, row);
        gridPane.add(createCell("Менеджер"), 1, row);
        gridPane.add(createCell("Компания"), 2, row);
        gridPane.add(createCell("Название"), 3, row);
        gridPane.add(createCell("Оригинал макет"), 4, row);
        gridPane.add(createCell("Тип"), 5, row);
        gridPane.add(createCell("Дата последней работы"), 6, row);
        gridPane.add(createCell("Комментарий"), 7, row);
        gridPane.add(createCell("Статус"), 8, row);

        row = 1;
        
        for (Frame frame : frameList) {
            gridPane.add(createCell(frame.getDateOfCreationFrame().get().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))), 0, row);
            gridPane.add(createCell(frame.getManager().getName()), 1, row);
            gridPane.add(createCell(frame.getCompany().getName()), 2, row);
            gridPane.add(createCell(frame.getName()), 3, row);
            gridPane.add(createCell(frame.getNumberOfLayout()), 4, row);
            gridPane.add(createCell(frame.getTypeOfFrame().getName()), 5, row);
            
            if (frame.getDateOfLastWork().get() != null) {
                gridPane.add(createCell(frame.getDateOfLastWork().get().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))), 6, row);
            } else {
                gridPane.add(createCell("Не была в работе."), 6, row);
            }

            if (frame.getComment().isBlank()) {
                gridPane.add(createCell("Нет комментария"), 7, row);
            } else {
                gridPane.add(createCell(frame.getComment()), 7, row);
            }
            
            gridPane.add(createCell(frame.getStatusOfFrame().getName()), 8, row);

            row++;
        }

    }

    private Label createCell(String text) {
        Label label = new Label(text);

        label.setStyle("-fx-border-color: black; -fx-padding: 5;");
        
        label.setMaxWidth(Double.MAX_VALUE);
        GridPane.setHgrow(label, Priority.ALWAYS);
        
        label.setWrapText(true);
        return label;
    }

    //отправляет grid на печать
    private void print() {
        PrintService.print(gridPane);
    }

    //добавляет контекстное меню на правую кнопку мыши
    private void initializeRightMouseClick() {
		ContextMenu contextMenu = new ContextMenu();

        MenuItem printItem = new MenuItem("Печать");
        MenuItem cancelItem = new MenuItem("Отмена");   

        contextMenu.getItems().addAll(printItem, cancelItem);

        printItem.setOnAction(e -> {
            print();
        });

        cancelItem.setOnAction(e -> {
            WindowManager.closeWindow((Stage) gridPane.getScene().getWindow());
        });

        gridPane.setOnContextMenuRequested(event -> {
            contextMenu.hide();
            contextMenu.show(gridPane, event.getScreenX(), event.getScreenY());
        });
        
    }
}
