package oriseus.Sagitarius_equipment.dto;

import java.util.List;

import oriseus.Sagitarius_equipment.utilities.IdGenerator;

public class DataBaseDTO {
//	public DataBaseDTO INSTANCE;
	public List<FrameDTO> frameList;
	public List<FrameDTO> archiveFrameList;
	public List<ManagerDTO> managerList;
	public List<TypeOfFrameDTO> typeOfFrameList;
	public List<StatusOfFrameDTO> statusOfFrameList;
	public List<CompanyDTO> companyList;
	public Long userId;
	public boolean isActual;
	public IdGenerator idGenerator;
	

	public DataBaseDTO() {}  // обязательно
}
