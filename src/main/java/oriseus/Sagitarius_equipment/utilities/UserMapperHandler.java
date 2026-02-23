package oriseus.Sagitarius_equipment.utilities;

import oriseus.Sagitarius_equipment.dto.UserDTO;
import oriseus.Sagitarius_equipment.model.User;

public class UserMapperHandler {

	public static UserDTO toDto(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.id = user.getId();
		userDTO.password = user.getPassword();
		userDTO.isSuperUser = user.isSuperUser();
		return userDTO;
	}
	
	public static User fromDto(UserDTO userDTO) {
		User user = new User(userDTO.id);
		user.setPassword(userDTO.password);
		return user;
	}
}
