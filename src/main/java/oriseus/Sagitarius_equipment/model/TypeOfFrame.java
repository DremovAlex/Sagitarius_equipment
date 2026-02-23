package oriseus.Sagitarius_equipment.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import oriseus.Sagitarius_equipment.ports.FilterValue;

public class TypeOfFrame implements FilterValue{
	
	private final Long id;
	private final StringProperty type = new SimpleStringProperty();
	
	public TypeOfFrame(Long id, String type) {
		this.id = id;
		this.type.set(type);
	}
	
	public Long getId() {
		return id;
	}
	
	public StringProperty typeProperty() {
		return type; 
	}

	public String getName() {
		return type.get();
	}

	public void setName(String type) {
		this.type.set(type);
	}
	
}
