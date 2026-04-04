package oriseus.Sagitarius_equipment.ports;

public enum SettingsPage {
    
	STATUSOFFRAME("Статус сеток"),
    TYPEOFFRAME("Тип сеток"),
	USERPASSWORD("Сменить пароль"),
    DATABASETTINGS("Настройки базы данных"),
    APPEARANCE("Настройка внешнего вида");

    private final String title;

    SettingsPage(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
