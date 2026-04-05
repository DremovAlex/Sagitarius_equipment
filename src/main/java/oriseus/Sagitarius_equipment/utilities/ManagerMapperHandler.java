package oriseus.Sagitarius_equipment.utilities;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import javafx.collections.ObservableList;
import oriseus.Sagitarius_equipment.dto.ManagerDTO;
import oriseus.Sagitarius_equipment.model.Company;
import oriseus.Sagitarius_equipment.model.Manager;

public class ManagerMapperHandler {
	
	public static ManagerDTO toDto(Manager manager) {
		ManagerDTO managerDTO = new ManagerDTO();
		
		managerDTO.id = manager.getId();
		managerDTO.name = manager.getName();
		managerDTO.companyIds = new ArrayList<Long>();
		for (Company company : manager.getCompanyList()) {
			managerDTO.companyIds.add(company.getId());
		}
		
		return managerDTO;
	}
	
    public static Manager fromDtoShallow(ManagerDTO managerDTO) {
    	Manager manager = new Manager(managerDTO.id, managerDTO.name);
    	return manager;        
    }
    
    public static void linkCompanies(Manager manager, ManagerDTO dto, Map<Long, Company> companiesById) {
    	    ((ObservableList<Company>) manager.getCompanyList()).setAll(
    	        dto.companyIds.stream()
    	            .map(companiesById::get)
    	            .filter(Objects::nonNull)
    	            .toList()
    	    );
    	}
}
