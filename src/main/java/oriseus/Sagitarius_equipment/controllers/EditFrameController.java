package oriseus.Sagitarius_equipment.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import oriseus.Sagitarius_equipment.model.Company;
import oriseus.Sagitarius_equipment.model.DataBase;
import oriseus.Sagitarius_equipment.model.Frame;
import oriseus.Sagitarius_equipment.model.Manager;
import oriseus.Sagitarius_equipment.model.StatusOfFrame;
import oriseus.Sagitarius_equipment.model.TypeOfFrame;
import oriseus.Sagitarius_equipment.utilities.Converters;
import oriseus.Sagitarius_equipment.utilities.WindowManager;

public class EditFrameController {
	private Manager manager;
	private Company company;
	private Frame frame;
	private TypeOfFrame typeOfFrame;
	
	@FXML
	private TextField frameNameTextField;
	@FXML
	private TextField numberOfLayoutTextField;
	
	@FXML
	private ChoiceBox<Manager> managerChoiceBox;
	@FXML
	private ChoiceBox<Company> companyChoiceBox;
	@FXML
	private ChoiceBox<TypeOfFrame> typeOfFrameChoiceBox;
	@FXML
	private ChoiceBox<StatusOfFrame> statusOfFrameChoiceBox;
	
	@FXML
	private DatePicker lastDateOfWorkDatePicker;
	
	@FXML
	private TextArea commentTextArea;
	
	@FXML
	private Button okButton;
	@FXML
	private Button cancelButton;
	
	@FXML
	private void initialize() {		
		companyChoiceBox.setDisable(true);
		companyChoiceBox.setItems(FXCollections.observableArrayList());

		managerChoiceBox.getSelectionModel()
	        .selectedItemProperty()
	        .addListener((obs, oldManager, newManager) -> {
	        	initChoiceBoxes(newManager);
	        	manager = newManager;	        	
	        });
		
		managerChoiceBox.setConverter(Converters.simpleConverter(Manager::getName));
		companyChoiceBox.setConverter(Converters.simpleConverter(Company::getName));
		typeOfFrameChoiceBox.setConverter(Converters.simpleConverter(TypeOfFrame::getName));
		statusOfFrameChoiceBox.setConverter(Converters.simpleConverter(StatusOfFrame::getName));
	}
	
	@FXML
	private void okButtonPressed() {
		
		if (companyChoiceBox.getValue() == null || manager == null || 
				frame == null || frameNameTextField.getText().isBlank() || 
				numberOfLayoutTextField.getText().isBlank()) return;
		
//		manager = managerChoiceBox.getValue();
//		company = companyChoiceBox.getValue();
		TypeOfFrame typeOfFrame = typeOfFrameChoiceBox.getValue();
		StatusOfFrame statusOfFrame = statusOfFrameChoiceBox.getValue();
		
		DataBase.getInstance().getFrameByName(frame.getName()).setManager(manager);
		DataBase.getInstance().getFrameByName(frame.getName()).setCompany(company);
		DataBase.getInstance().getFrameByName(frame.getName()).setName(frameNameTextField.getText());
		DataBase.getInstance().getFrameByName(frame.getName()).setNumberOfLayout(numberOfLayoutTextField.getText());
		DataBase.getInstance().getFrameByName(frame.getName()).setTypeOfFrame(typeOfFrame);
		DataBase.getInstance().getFrameByName(frame.getName()).setStatusOfFrame(statusOfFrame);
		
		if (lastDateOfWorkDatePicker.getValue() != null) {
			DataBase.getInstance().getFrameByName(frame.getName()).setDateOfLastWork(lastDateOfWorkDatePicker.getValue());
			System.out.println(DataBase.getInstance().getFrameByName(frame.getName()).getDateOfLastWork());
		}
		
		DataBase.getInstance().getFrameByName(frame.getName()).setComment(commentTextArea.getText());
		

		if(statusOfFrame.getName().equals(DataBase.getInstance().getStatusByName("Списана").getName())) {
			DataBase.getInstance().setFrameToArchive(frame);		
		} 
		
		if(statusOfFrame.getName().equals(DataBase.getInstance().getStatusByName("В работе").getName()) && !DataBase.getInstance().isActual()) {	
			DataBase.getInstance().setToActual(frame);
		}
		
		WindowManager.closeWindow((Stage) okButton.getScene().getWindow());
	}
	
	@FXML
	private void cancelButtonPressed() {
		WindowManager.closeWindow((Stage) cancelButton.getScene().getWindow());
	}
	
	public void setData(Manager manager, Company company, Frame frame) {
		this.manager = manager;
		this.company = company;
		this.frame = frame;
		
		managerChoiceBox.getItems().addAll(DataBase.getInstance().getManagerList());
		companyChoiceBox.getItems().addAll(DataBase.getInstance().getManagerByName(manager.getName()).getCompanyList());
		typeOfFrameChoiceBox.getItems().addAll(DataBase.getInstance().getTypeOfFrameList());
		statusOfFrameChoiceBox.getItems().addAll(DataBase.getInstance().getStatusOfFrameList());
		
		managerChoiceBox.getSelectionModel().select(DataBase.getInstance().getManagerByName(manager.getName()));
		companyChoiceBox.getSelectionModel().select(DataBase.getInstance().getManagerByName(manager.getName()).getCompanyByName(company.getName()));
		typeOfFrameChoiceBox.getSelectionModel().select(DataBase.getInstance().getTypeOfFrameByName(frame.getTypeOfFrame().getName()));
		statusOfFrameChoiceBox.getSelectionModel().select(DataBase.getInstance().getStatusByName(frame.getStatusOfFrame().getName()));
		
		frameNameTextField.setText(frame.getName());
		numberOfLayoutTextField.setText(frame.getNumberOfLayout());
		commentTextArea.setText(frame.getComment());
	}
	private void initChoiceBoxes(Manager manager) {

	    if (manager == null) {
	    	companyChoiceBox.getItems().clear();
	    	companyChoiceBox.setDisable(true);
	        return;
	    }

	    ObservableList<Company> companyList = (ObservableList<Company>) manager.getCompanyList();

	    if (companyList == null) {
	    	companyChoiceBox.getItems().clear();
	    	companyChoiceBox.setDisable(true);
	        return;
	    }

	    companyChoiceBox.setDisable(false);
	    companyChoiceBox.setItems(companyList);
	    companyChoiceBox.getSelectionModel().clearSelection();
	    
		companyChoiceBox.valueProperty().addListener((obs, oldCompany, newCompany) -> {
			if (newCompany == null) {
	            return;
	        }
			
			company = newCompany;

		});
	}

}
