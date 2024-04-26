package carmax.version001.model.carInner;

public enum Drive {
    WD2("2WD"), WD4("4WD"), NA("n/a"); // "n/a" for not available

    private final String displayValue;

    Drive(String displayValue) {
        this.displayValue = displayValue;
    }

    public static Drive fromString(String text) {
        if (text == null) {
            return NA;
        }
        text = text.trim().toUpperCase();
        for (Drive drive : Drive.values()) {
            if (drive.displayValue.equals(text)) {
                return drive;
            }
        }
        return NA;
    }

    @Override
    public String toString() {
        return this.displayValue;
    }
}
