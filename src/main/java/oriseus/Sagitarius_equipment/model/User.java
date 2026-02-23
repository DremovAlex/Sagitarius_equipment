package oriseus.Sagitarius_equipment.model;

public class User {
	
	private final Long id;
	private String password;
	private boolean isSuperUser;
	
	public User(Long id) {
		this.id = id;
		password = "1234";
		isSuperUser = false;
	}

	public Long getId() {
		return id;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isSuperUser() {
		return isSuperUser;
	}

	public void setSuperUser(boolean isSuperUser) {
		this.isSuperUser = isSuperUser;
	}
	
}
