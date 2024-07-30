package core.model.enums;

public enum Transmission {
    AUTOMATIC("Automatic"), MANUAL("Manual"), NA("n/a");

    private final String displayValue;

    Transmission(String displayValue) {
        this.displayValue = displayValue;
    }

    public static Transmission fromString(String text) {
        if (text == null) {
            return NA;
        }
        text = text.trim().toUpperCase();
        for (Transmission transmission : Transmission.values()) {
            if (transmission.displayValue.equalsIgnoreCase(text)) {
                return transmission;
            }
        }
        return NA;
    }

    @Override
    public String toString() {
        return this.displayValue;
    }
}
