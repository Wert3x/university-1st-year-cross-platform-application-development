public enum FlashlightStatus {
    OFF("Выключен"),
    ON("Светит");

    private final String description;

    FlashlightStatus(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}