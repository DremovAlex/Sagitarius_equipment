package oriseus.Sagitarius_equipment.utilities;

import java.util.Map;

import oriseus.Sagitarius_equipment.dto.FrameDTO;
import oriseus.Sagitarius_equipment.model.Company;
import oriseus.Sagitarius_equipment.model.Frame;
import oriseus.Sagitarius_equipment.model.Manager;
import oriseus.Sagitarius_equipment.model.StatusOfFrame;
import oriseus.Sagitarius_equipment.model.TypeOfFrame;

public class FrameMapperHandler {
	public static FrameDTO toDto(Frame frame) {
		FrameDTO frameDTO = new FrameDTO();
		
		frameDTO.id = frame.getId();
		frameDTO.name = frame.getName();
		frameDTO.managerId = frame.getManager().getId();
		frameDTO.companyId = frame.getCompany().getId();
		frameDTO.dateOFCreation = frame.getDateOfCreationFrame().get();
		frameDTO.numberOfLayouts = frame.getNumberOfLayout();
		frameDTO.typeOfFrameId = frame.getTypeOfFrame().getId();
		frameDTO.dateOFLastWork = frame.getDateOfLastWork().get();
		frameDTO.comment = frame.getComment();
		frameDTO.statusOfFrameId = frame.getStatusOfFrame().getId();
		frameDTO.imageFrameList = frame.getImageFrameList();
		frameDTO.dateOfSendToArchive = frame.getDateOfSendToArchive().get();
		frameDTO.pathToPdf = frame.getPathToPdf();

		return frameDTO;
	}
	
	public static Frame fromDtoShallow(FrameDTO dto) {
	    Frame frame = new Frame(dto.id, dto.name);
	    return frame;
	}
	
	public static void linkRelations(Frame frame, FrameDTO dto, Map<Long, Manager> managers,
									Map<Long, Company> companies, Map<Long, TypeOfFrame> typesOfFrames,
									Map<Long, StatusOfFrame> statusOfFrames) {
		frame.setManager(managers.get(dto.managerId));
		frame.setTypeOfFrame(typesOfFrames.get(dto.typeOfFrameId));
		frame.setStatusOfFrame(statusOfFrames.get(dto.statusOfFrameId));
		frame.setDateOfCreation(dto.dateOFCreation);
		frame.setNumberOfLayout(dto.numberOfLayouts);
		frame.setDateOfLastWork(dto.dateOFLastWork);
		frame.setComment(dto.comment);
		frame.setImageFrameList(dto.imageFrameList);
		frame.setDateOfSendToArchive(dto.dateOfSendToArchive);
		frame.setPathToPdf(dto.pathToPdf);

		
	}

}
