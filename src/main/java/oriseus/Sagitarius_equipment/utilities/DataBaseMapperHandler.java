package oriseus.Sagitarius_equipment.utilities;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import oriseus.Sagitarius_equipment.dto.CompanyDTO;
import oriseus.Sagitarius_equipment.dto.DataBaseDTO;
import oriseus.Sagitarius_equipment.dto.FrameDTO;
import oriseus.Sagitarius_equipment.dto.ManagerDTO;
import oriseus.Sagitarius_equipment.dto.StatusOfFrameDTO;
import oriseus.Sagitarius_equipment.dto.TypeOfFrameDTO;
import oriseus.Sagitarius_equipment.model.Company;
import oriseus.Sagitarius_equipment.model.DataBase;
import oriseus.Sagitarius_equipment.model.Frame;
import oriseus.Sagitarius_equipment.model.Manager;
import oriseus.Sagitarius_equipment.model.StatusOfFrame;
import oriseus.Sagitarius_equipment.model.TypeOfFrame;

public class DataBaseMapperHandler {

	public static DataBaseDTO toDto(DataBase dataBase) {
	    DataBaseDTO dBDto = new DataBaseDTO();

	    dBDto.managerList = dataBase.getManagerList().stream()
	        .map(ManagerMapperHandler::toDto)
	        .toList();

	    dBDto.typeOfFrameList = dataBase.getTypeOfFrameList().stream()
		        .map(TypeOfFrameMapperHundler::toDto)
		        .toList();

	    dBDto.statusOfFrameList = dataBase.getStatusOfFrameList().stream()
		        .map(StatusOfFrameMapperHandler::toDto)
		        .toList();

	    dBDto.frameList = dataBase.getFrameListByIsActual().stream()
	        .map(FrameMapperHandler::toDto)
	        .toList();

	    dBDto.archiveFrameList = dataBase.getArchiveFrameList().stream()
		        .map(FrameMapperHandler::toDto)
		        .toList();

	    dBDto.companyList = new ArrayList<CompanyDTO>();
	    for (Manager manager : dataBase.getManagerList()) {
	    	
	    	for (Company company : manager.getCompanyList()) {
	    		dBDto.companyList.add(CompanyMapperHandler.toDTO(company));
	    	}

	    }
	    
	    return dBDto;
	}
	
	public static DataBase fromDto(DataBaseDTO dBDto) {
		
		DataBase db = DataBase.getInstance();
		
		db.getFrameList().clear();
		db.getArchiveFrameList().clear();
		db.getManagerList().clear();
		db.getTypeOfFrameList().clear();
		db.getStatusOfFrameList().clear();
		
	    for (ManagerDTO managerDto : dBDto.managerList) {
	        Manager manager = new Manager(managerDto.id, managerDto.name);
	        
	        manager.setCompanyList(FXCollections.observableArrayList());

	        for (Long l : managerDto.companyIds) {
	        	
	        	for (CompanyDTO companyDTO : dBDto.companyList) {
	        		if (l.equals(companyDTO.id)) {
	        			Company company = new Company(l, companyDTO.name, manager);
	        			manager.getCompanyList().add(company);
	        		}
	        	}
	        	
	        }

	        db.getManagerList().add(manager);
	            
	    }
	    
	    for (TypeOfFrameDTO typeOfFrameDTO : dBDto.typeOfFrameList) {
	    	db.getTypeOfFrameList().add(new TypeOfFrame(typeOfFrameDTO.id, typeOfFrameDTO.type));
	    }
		
	    for (StatusOfFrameDTO statusOfFrameDTO : dBDto.statusOfFrameList) {
	    	db.getStatusOfFrameList().add(new StatusOfFrame(statusOfFrameDTO.id, statusOfFrameDTO.status));
	    }
	    
	    for (FrameDTO frameDTO : dBDto.frameList) {
	    	db.getFrameList().add(new Frame(frameDTO.id, frameDTO.name));
	    }
	    
	    for (FrameDTO frameDTO : dBDto.archiveFrameList) {
	    	db.getArchiveFrameList().add(new Frame(frameDTO.id, frameDTO.name));
	    }
	    
/*	    
	    for (FrameDTO f : dBDto.frameList) {
	        Frame frame = db.getFrameList().stream()
	                .filter(x -> x.getId().equals(f.id))
	                .findFirst().orElse(null);

	        Frame archiveFrame = db.getArchiveFrameList().stream()
	                .filter(x -> x.getId().equals(f.id))
	                .findFirst().orElse(null);	        
	        
	        StatusOfFrame statusOfFrame = db.getStatusOfFrameList().stream()
	                .filter(x -> x.getId().equals(f.managerId))
	                .findFirst().orElse(null);

	        TypeOfFrame typeOfFrame = db.getTypeOfFrameList().stream()
	                .filter(x -> x.getId().equals(f.managerId))
	                .findFirst().orElse(null);	        
	        
	        Manager manager = db.getManagerList().stream()
	                .filter(x -> x.getId().equals(f.managerId))
	                .findFirst().orElse(null);
	        
	        frame.setManager(manager);
	        frame.setCompany(manager.getCompanyList().stream()
	                .filter(c -> c.getId().equals(f.companyId))
	                .findFirst().orElse(null));
	        frame.setStatusOfFrame(statusOfFrame);
	        frame.setTypeOfFrame(typeOfFrame);
	        frame.setDateOfCreation(f.dateOFCreation);
	        frame.setDateOfLastWork(f.dateOFLastWork);
	        frame.setComment(f.comment);
	        frame.setName(f.name);
	        frame.setNumberOfLayout(f.numberOfLayouts);
	        
	        archiveFrame.setManager(manager);
	        archiveFrame.setCompany(manager.getCompanyList().stream()
	                .filter(c -> c.getId().equals(f.companyId))
	                .findFirst().orElseThrow());
	        archiveFrame.setStatusOfFrame(statusOfFrame);
	        archiveFrame.setTypeOfFrame(typeOfFrame);
	        archiveFrame.setDateOfCreation(f.dateOFCreation);
	        archiveFrame.setDateOfLastWork(f.dateOFLastWork);
	        archiveFrame.setComment(f.comment);
	        archiveFrame.setName(f.name);
	        archiveFrame.setNumberOfLayout(f.numberOfLayouts);
	    }
*/	    
		for (FrameDTO f : dBDto.frameList) {

		    Frame frame = db.getFrameList().stream()
		            .filter(x -> x.getId().equals(f.id))
		            .findFirst().orElse(null);

		    if (frame == null) continue;

		    Manager manager = db.getManagerList().stream()
		            .filter(x -> x.getId().equals(f.managerId))
		            .findFirst().orElse(null);

		    StatusOfFrame statusOfFrame = db.getStatusOfFrameList().stream()
		            .filter(x -> x.getId().equals(f.statusOfFrameId))
		            .findFirst().orElse(null);

		    TypeOfFrame typeOfFrame = db.getTypeOfFrameList().stream()
		            .filter(x -> x.getId().equals(f.typeOfFrameId))
		            .findFirst().orElse(null);

		    frame.setManager(manager);

		    if (manager != null) {
//		        frame.setCompany(
//		            manager.getCompanyList().stream()
//		                .filter(c -> c.getId().equals(f.companyId))
//		                .findFirst().orElse(null)
//		        );
		        for (Company company : manager.getCompanyList()) {
		        	System.out.println(company.getName());
		        	if (company.getId().equals(f.companyId)) {
		        		frame.setCompany(company);
		        	}
		        }
		        
		    }

		    frame.setStatusOfFrame(statusOfFrame);
		    frame.setTypeOfFrame(typeOfFrame);
		    frame.setDateOfCreation(f.dateOFCreation);
		    frame.setDateOfLastWork(f.dateOFLastWork);
		    frame.setComment(f.comment);
		    frame.setName(f.name);
		    frame.setNumberOfLayout(f.numberOfLayouts);
		    frame.setImageFrameList(f.imageFrameList);
			frame.setPathToPdf(f.pathToPdf);
		}
	    
		for (FrameDTO f : dBDto.archiveFrameList) {

		    Frame archiveFrame = db.getArchiveFrameList().stream()
		            .filter(x -> x.getId().equals(f.id))
		            .findFirst().orElse(null);

		    if (archiveFrame == null) continue;

		    Manager manager = db.getManagerList().stream()
		            .filter(x -> x.getId().equals(f.managerId))
		            .findFirst().orElse(null);

		    StatusOfFrame statusOfFrame = db.getStatusOfFrameList().stream()
		            .filter(x -> x.getId().equals(f.statusOfFrameId))
		            .findFirst().orElse(null);

		    TypeOfFrame typeOfFrame = db.getTypeOfFrameList().stream()
		            .filter(x -> x.getId().equals(f.typeOfFrameId))
		            .findFirst().orElse(null);

		    archiveFrame.setManager(manager);

		    if (manager != null) {
		        archiveFrame.setCompany(
		            manager.getCompanyList().stream()
		                .filter(c -> c.getId().equals(f.companyId))
		                .findFirst().orElse(null)
		        );
		    }

		    archiveFrame.setStatusOfFrame(statusOfFrame);
		    archiveFrame.setTypeOfFrame(typeOfFrame);
		    archiveFrame.setDateOfCreation(f.dateOFCreation);
		    archiveFrame.setDateOfLastWork(f.dateOFLastWork);
		    archiveFrame.setComment(f.comment);
		    archiveFrame.setName(f.name);
		    archiveFrame.setNumberOfLayout(f.numberOfLayouts);
		    archiveFrame.setImageFrameList(f.imageFrameList);
			archiveFrame.setPathToPdf(f.pathToPdf);
		}

		return db;
	}
}
