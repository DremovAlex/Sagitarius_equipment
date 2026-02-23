package oriseus.Sagitarius_equipment.utilities;

import java.util.Map;

import oriseus.Sagitarius_equipment.dto.CompanyDTO;
import oriseus.Sagitarius_equipment.model.Company;
import oriseus.Sagitarius_equipment.model.Manager;

public class CompanyMapperHandler {
	
	public static CompanyDTO toDTO(Company company) {
		CompanyDTO companyDTO = new CompanyDTO();
		
		companyDTO.id = company.getId();
		companyDTO.name = company.getName();
		companyDTO.managerId = company.getManager().getId(); 
		
		return companyDTO;
	}
	
    public static Company fromDto(CompanyDTO companyDTO, Map<Long, Manager> managers) {
            return new Company(companyDTO.id, companyDTO.name, managers.get(companyDTO.managerId));
        }
}
