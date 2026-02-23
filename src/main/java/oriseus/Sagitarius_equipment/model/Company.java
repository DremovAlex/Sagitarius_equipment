package oriseus.Sagitarius_equipment.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import oriseus.Sagitarius_equipment.ports.FilterValue;

public class Company implements FilterValue {
	private final Long id;
	private final StringProperty name = new SimpleStringProperty();
	private Manager manager;
	
	public Company(Long id, String name, Manager manager) {
		this.id = id;
		this.name.set(name);
		this.manager = manager;
	}
	
	public StringProperty nameProperty() {
		return name; 
	}
	
	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}
	
}
