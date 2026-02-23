package oriseus.Sagitarius_equipment.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import oriseus.Sagitarius_equipment.ports.FilterValue;

public class StatusOfFrame implements FilterValue{
	
	private final Long id;
	private final StringProperty status = new SimpleStringProperty();
	
	public StatusOfFrame(Long id, String status) {
		this.id = id;
		this.status.set(status);
	}
	
	public Long getId() {
		return id;
	}
	
	
	public StringProperty frameStatusProperty() {
		return status; 
	}

	public String getName() {
		return status.get();
	}

	public void setName(String status) {
		this.status.set(status);
	}
}
