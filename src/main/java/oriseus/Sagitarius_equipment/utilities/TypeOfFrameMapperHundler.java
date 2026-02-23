package oriseus.Sagitarius_equipment.utilities;

import oriseus.Sagitarius_equipment.dto.TypeOfFrameDTO;
import oriseus.Sagitarius_equipment.model.TypeOfFrame;

public class TypeOfFrameMapperHundler {

	public static TypeOfFrameDTO toDto(TypeOfFrame typeOfFrame) {
		TypeOfFrameDTO typeOfFrameDTO = new TypeOfFrameDTO();
		typeOfFrameDTO.id = typeOfFrame.getId();
		typeOfFrameDTO.type = typeOfFrame.getName();
		return typeOfFrameDTO;
	}
	
	public static TypeOfFrame fromDto(TypeOfFrameDTO typeOfFrameDTO) {
		return new TypeOfFrame(typeOfFrameDTO.id, typeOfFrameDTO.type);
	}
}
