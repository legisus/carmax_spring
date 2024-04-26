package carmax.version001.model.carInner;

public enum Location {

    MURRIETA("Murrieta"),
    OCEANSIDE("Oceanside"),
    IRVINE("Irvine"),
    OXNARD("Oxnard"),
    DUARTE("Duarte");

    private final String location;

    Location(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return location;
    }


}
