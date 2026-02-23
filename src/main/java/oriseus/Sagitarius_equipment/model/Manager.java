package oriseus.Sagitarius_equipment.model;

import java.util.List;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import oriseus.Sagitarius_equipment.ports.FilterValue;

public class Manager implements FilterValue{
	private final Long id;
	private final StringProperty name = new SimpleStringProperty();
	private List<Company> companyList;
	
	public Manager(Long id, String name) {
		this.id = id;
		this.name.set(name);
		companyList = FXCollections.observableArrayList();
	}
	
	public Long getId() {
		return id;
	}
	public StringProperty nameProperty() {
		return name; 
	}
		
	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public List<Company> getCompanyList() {
		return companyList;
	}

	public void setCompanyList(List<Company> companyList) {
		this.companyList = companyList;
	}
	
	public Company getCompanyByName(String name) {
		for (Company company : companyList) {
			if (company.getName().equals(name)) {
				return company;
			}
		}
		return null;
	}
}
