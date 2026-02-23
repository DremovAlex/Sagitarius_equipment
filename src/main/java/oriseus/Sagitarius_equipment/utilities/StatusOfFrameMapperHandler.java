package oriseus.Sagitarius_equipment.utilities;

import oriseus.Sagitarius_equipment.dto.StatusOfFrameDTO;
import oriseus.Sagitarius_equipment.model.StatusOfFrame;

public class StatusOfFrameMapperHandler {
	
	public static StatusOfFrameDTO toDto(StatusOfFrame statusOfFrame) {
		
		StatusOfFrameDTO statusOfFrameDTO = new StatusOfFrameDTO();
		statusOfFrameDTO.id = statusOfFrame.getId();
		statusOfFrameDTO.status = statusOfFrame.getName();
		return statusOfFrameDTO;
	}
	
	public static StatusOfFrame fromDto(StatusOfFrameDTO statusOfFrameDTO) {
		return new StatusOfFrame(statusOfFrameDTO.id, statusOfFrameDTO.status);
	}
}
