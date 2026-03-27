package oriseus.Sagitarius_equipment.dto;

import java.time.LocalDate;
import java.util.List;


public class FrameDTO {
	public Long id;
	public String name;
	public Long managerId;
	public Long companyId;
	public LocalDate dateOFCreation;
	public String numberOfLayouts;
	public Long typeOfFrameId;
	public LocalDate dateOFLastWork;
	public String comment;
	public Long statusOfFrameId;
	public List<String> imageFrameList;
	public LocalDate dateOfSendToArchive;
	public String pathToPdf;
}
