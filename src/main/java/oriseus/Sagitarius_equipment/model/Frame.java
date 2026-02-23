package oriseus.Sagitarius_equipment.model;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import oriseus.Sagitarius_equipment.ports.FilterValue;

public class Frame implements FilterValue{
	
	private final Long id;
	private final StringProperty name = new SimpleStringProperty();
	private final ObjectProperty<Manager> manager = new SimpleObjectProperty<>();
	private final ObjectProperty<Company> company = new SimpleObjectProperty<>();
	private final ObjectProperty<LocalDate> dateOfCreationFrame = new SimpleObjectProperty<>();
	private final StringProperty numberOfLayout = new SimpleStringProperty();
	private final ObjectProperty<TypeOfFrame> typeOfFrame = new SimpleObjectProperty<>();
	private final ObjectProperty<LocalDate> dateOfLastWork = new SimpleObjectProperty<>();
	private final StringProperty comment = new SimpleStringProperty();
	private final ObjectProperty<StatusOfFrame> statusOfFrame = new SimpleObjectProperty<>();
	private final ObjectProperty<LocalDate> dateOfSendToArchive = new SimpleObjectProperty<>();
	
	private List<String> imageFrameList;
	
	public Frame(Long id, String name, Manager manager, Company company, String numberOfLayout, TypeOfFrame typeOfFrame, StatusOfFrame statusOfFrame) {
		this.id = id;
		this.name.set(name);
		this.manager.set(manager);
		this.company.set(company);	
		this.numberOfLayout.set(numberOfLayout);
		this.typeOfFrame.set(typeOfFrame);
		
		dateOfCreationFrame.set(LocalDate.now());
		
		comment.set("");
		this.statusOfFrame.set(statusOfFrame);
		imageFrameList = new LinkedList<String>();
	}
	
	public Frame(Long id, String name) {
		this.id = id;
		this.name.set(name);		
		
		imageFrameList = new LinkedList<String>();
	}

	public Long getId() {
		return id;
	}
	
	public StringProperty frameNameProperty() {
		return name; 
	}
	
    public ObjectProperty<Manager> managerProperty() {
        return manager;
    }
	
    public ObservableValue<String> managerNameProperty() {
        return manager.flatMap(Manager::nameProperty);
    }
    
    public ObjectProperty<Company> companyProperty() {
        return company;
    }
    
    public ObservableValue<String> companyNameProperty() {
        return company.flatMap(Company::nameProperty);
    }
    
    public ObservableValue<String> nameStatusOfFrameProperty() {
        return statusOfFrame.flatMap(StatusOfFrame::frameStatusProperty);
    }
    
    public ObjectProperty<LocalDate> createdDateProperty() {
        return dateOfCreationFrame;
    }
    
    public StringProperty numberOfLayoutProperty() {
    	return numberOfLayout;
    }
	
    public ObjectProperty<LocalDate> dateOfLastWorkProperty() {
    	return dateOfLastWork;
    }
   
    public ObservableValue<String> typeOfFrameNameProperty() {
        return typeOfFrame.flatMap(TypeOfFrame::typeProperty);
    }
    
    public StringProperty commentProperty() {
    	return comment;
    }
    
    public ObjectProperty<StatusOfFrame> statusOfFrameProperty() {
    	return statusOfFrame;
    }
	
    public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public ObjectProperty<LocalDate> getDateOfCreationFrame() {
		return dateOfCreationFrame;
	}
	
	public ObjectProperty<LocalDate> getDateOfSendToArchive() {
		return dateOfSendToArchive;
	}

	public Company getCompany() {
		return company.get();
	}

	public void setCompany(Company company) {
		this.company.set(company);
	}

	public Manager getManager() {
		return manager.get();
	}

	public void setManager(Manager manager) {
		this.manager.set(manager);
	}

	public TypeOfFrame getTypeOfFrame() {
		return typeOfFrame.get();
	}

	public void setTypeOfFrame(TypeOfFrame typeOfFrame) {
		this.typeOfFrame.set(typeOfFrame);
	}

	public ObjectProperty<LocalDate> getDateOfLastWork() {
		return dateOfLastWork;
	}

	public void setDateOfLastWork(LocalDate dateOfLastWork) {
		this.dateOfLastWork.set(dateOfLastWork);
	}

	public String getComment() {
		return comment.get();
	}

	public void setComment(String comment) {
		this.comment.set(comment);
	}

	public StatusOfFrame getStatusOfFrame() {
		return statusOfFrame.get();
	}

	public void setStatusOfFrame(StatusOfFrame statusOfFrame) {
		this.statusOfFrame.set(statusOfFrame);
	}

	public String getNumberOfLayout() {
		return numberOfLayout.get();
	}

	public void setNumberOfLayout(String numberOfLayout) {
		this.numberOfLayout.set(numberOfLayout);
	}
	
	public void setDateOfCreation(LocalDate localDate) {
		dateOfCreationFrame.set(localDate);
	}

	public void setDateOfSendToArchive(LocalDate localDate) {
		dateOfSendToArchive.set(localDate);
	}
	
	public List<String> getImageFrameList() {
		return imageFrameList;
	}

	public void setImageFrameList(List<String> imageFrameList) {
		this.imageFrameList = imageFrameList;
	}
	
	public void addImageToFrame(String image) {
		imageFrameList.add(image);
	}
	
	public void deleteImageFromFrame(int index) {
		imageFrameList.remove(index);
	}
}
