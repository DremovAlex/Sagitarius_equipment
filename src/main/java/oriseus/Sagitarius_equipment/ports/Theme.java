package oriseus.Sagitarius_equipment.ports;

public enum Theme {
    DEFAULT_LIGHT("Default light"),
    DEFAULT_DARK("Default dark"),
    GRUVBOX_DARK("Gruvbox dark"),
    GRUVBOX_LIGHT("Gruvbox light"),
    CATPPUCCIN_LATTE("Catppuccin latte"),
    CATPPUCCIN_MOCHA("Catppuccin mocha");

    private final String title;

    Theme(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return title;
    }
}
