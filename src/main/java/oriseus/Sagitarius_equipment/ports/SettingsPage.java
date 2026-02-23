package oriseus.Sagitarius_equipment.ports;

public enum SettingsPage {
    
	STATUSOFFRAME("Статус сеток"),
    TYPEOFFRAME("Тип сеток"),
	USERPASSWORD("Сменить пароль");

    private final String title;

    SettingsPage(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
