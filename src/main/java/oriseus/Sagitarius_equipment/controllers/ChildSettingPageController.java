package oriseus.Sagitarius_equipment.controllers;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import oriseus.Sagitarius_equipment.model.DataBase;
import oriseus.Sagitarius_equipment.model.StatusOfFrame;
import oriseus.Sagitarius_equipment.model.TypeOfFrame;
import oriseus.Sagitarius_equipment.ports.FilterValue;
import oriseus.Sagitarius_equipment.ports.SettingsPage;

public class ChildSettingPageController {

	private SettingsPage settingsPage;
	private FilterValue filterValue;
	
	@FXML
	private ListView<FilterValue> listView;
	
	@FXML
	private TextField filterValueTextField;
	
	@FXML
	private Button addButton;
	@FXML
	private Button editButton;
	@FXML
	private Button deleteButton;
	
	@FXML
	private void initialize() {
		setConverterToListView();
		addListenerToListView();
	}
	
	@FXML
	private void addButtonPressed() {
		if (filterValueTextField.getText().isBlank()) return;
		
		if (settingsPage.equals(SettingsPage.STATUSOFFRAME)) {
			StatusOfFrame statusOfFrame = new StatusOfFrame(DataBase.getInstance().getIdGenerator().next(StatusOfFrame.class), filterValueTextField.getText());
			DataBase.getInstance().getStatusOfFrameList().add(statusOfFrame);
		}
		if (settingsPage.equals(SettingsPage.TYPEOFFRAME)) {
			TypeOfFrame typeOfFrame = new TypeOfFrame(DataBase.getInstance().getIdGenerator().next(TypeOfFrame.class), filterValueTextField.getText());
			DataBase.getInstance().getTypeOfFrameList().add(typeOfFrame);
		}
	}
	
	@FXML
	private void editButtonPressed() {
		if (filterValue.getName().equals("В работе") || filterValue.getName().equals("Списана")) return;
		
		filterValue.setName(filterValueTextField.getText());
	}
	
	@FXML
	private void deleteButtonPressed() {
		
	}
	
	public void setData(SettingsPage page) {
		switch (page) {
        	case STATUSOFFRAME	->	{
        		initializeListView(DataBase.getInstance().getStatusOfFrameList());
        		settingsPage = SettingsPage.STATUSOFFRAME; 
        	}
        	case TYPEOFFRAME	->	{
        		initializeListView(DataBase.getInstance().getTypeOfFrameList());
        		settingsPage = SettingsPage.TYPEOFFRAME;
        	}
		}
	}
	
	//Заполняет listview в зависимости от выбора пользователя
	private void initializeListView(List<? extends FilterValue> filteredValueList) {
		listView.getItems().addAll(filteredValueList);
	}
	
	//вешает конвертер на listview
	private void setConverterToListView() {
		listView.setCellFactory(lv -> new ListCell<>() {
		    @Override
		    protected void updateItem(FilterValue item, boolean empty) {
		        super.updateItem(item, empty);
		        setText(item == null || empty ? null : item.getName());
		    }
		});
	}
	
	//Вешает слушатель на listview
	private void addListenerToListView() {
		listView.getSelectionModel()
		.selectedItemProperty()
		.addListener((obs, old, selected) -> {
            if (selected != null) {
                filterValueTextField.setText(selected.getName());
                filterValue = getTypeOfFilterValue(selected);
            }
        });
	}
	
	//Возвращет обьект нужного типа
	private FilterValue getTypeOfFilterValue(FilterValue filterValue) {
		FilterValue value = null;
		if (filterValue instanceof StatusOfFrame) {
			value = DataBase.getInstance().getStatusByName(filterValue.getName());
		}
		if (filterValue instanceof TypeOfFrame) {
			value = DataBase.getInstance().getTypeOfFrameByName(filterValue.getName());
		}
		return value;
	}
}
