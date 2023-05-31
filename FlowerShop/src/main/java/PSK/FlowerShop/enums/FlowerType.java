package PSK.FlowerShop.enums;

public enum FlowerType {
    GELE("GELE"),
    GYVOS_GELES("GYVOS GELES"),
    PUOKSTE("PUOKSTE");

    private String displayName;

    FlowerType() {
        this.displayName = this.name().toLowerCase();
    }

    FlowerType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}