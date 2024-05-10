package carmax.version001.model.carInner;

import lombok.Getter;

@Getter
public enum Locations {

    NEWNAN("Georgia", "Newnan", "Newnan"),
    LAUREL("Maryland", "Laurel", "Laurel"),
    MURRIETA("California", "Murrieta", "Murrieta"),
    CHICAGO("Illinois", "Chicago", "Montgomery"),
    CLERMONT("Florida", "Clermont", "Clermont"),
    ROCHESTER("New York", "Rochester", "Rochester"),
    TAMPA("Florida", "Tampa", "Tampa"),
    JACKSONVILLE("Florida", "Jacksonville", "Jacksonville"),
    SACRAMENTO("California", "Sacramento", "Sacramento"),
    CLEVELAND("Ohio", "Cleveland", "Cleveland"),
    SALT_LAKE_CITY("Utah", "Salt Lake City", "South Jordan"),
    LOUISVILLE("Kentucky", "Louisville", "Louisville"),
    CLACKAMAS("Oregon", "Clackamas", "Portland"),
    HARTFORD("Connecticut", "Hartford", "Hartford"),
    DALLAS("Texas", "Dallas", "Wilmer"),
    WEST_COLUMBIA("South Carolina", "West Columbia", "West Columbia"),
    COLORADO_SPRINGS("Colorado", "Colorado Springs", "Colorado Springs"),
    HICKORY("North Carolina", "Hickory", "Hickory"),
    KANSAS_CITY("Kansas", "Kansas City", "Merriam"),
    NASHVILLE("Tennessee", "Nashville", "Madison"),
    ROSEVILLE("California", "Roseville", "Roseville"),
    KENNER("Louisiana", "Kenner", "Kenner"),
    OKLAHOMA_CITY("Oklahoma", "Oklahoma City", "Oklahoma City"),
    PHOENIX("Arizona", "Phoenix", "Tolleson"),
    BOISE("Idaho", "Boise", "Meridian"),
    WESTBOROUGH("Massachusetts", "Westborough", "Westborough"),
    FT_LAUDERDALE("Florida", "Ft. Lauderdale", "Davie"),
    MURFREESBORO("Tennessee", "Murfreesboro", "Murfreesboro"),
    INDIANAPOLIS("Indiana", "Indianapolis", "Carmel"),
    ST_LOUIS("Missouri", "St. Louis", "Saint Peters"),
    NORWOOD("Massachusetts", "Norwood", "Norwood"),
    PHARR("Texas", "Pharr", "McAllen"),
    BATON_ROUGE("Louisiana", "Baton Rouge", "Baton Rouge"),
    DAYTON("Ohio", "Dayton", "Dayton"),
    MIAMI("Florida", "Miami", "Doral"),
    RALEIGH("North Carolina", "Raleigh", "Raleigh"),
    HOUSTON("Texas", "Houston", "Humble"),
    PLEASANTON("California", "Pleasanton", "Pleasanton"),
    RICHMOND("Virginia", "Richmond", "Glen Allen"),
    LAS_VEGAS("Nevada", "Las Vegas", "Las Vegas"),
    CLEARWATER("Florida", "Clearwater", "Clearwater"),
    ROSEDALE("Maryland", "Rosedale", "Rosedale"),
    OXNARD("California", "Oxnard", "Oxnard"),
    BOYNTON_BEACH("Florida", "Boynton Beach", "Boynton Beach"),
    ALBUQUERQUE("New Mexico", "Albuquerque", "Albuquerque"),
    SAN_ANTONIO("Texas", "San Antonio", "San Antonio"),
    FT_MYERS("Florida", "Ft. Myers", "Ft. Myers"),
    EL_PASO("Texas", "El Paso", "El Paso"),
    BROOKLYN_PARK("Minnesota", "Brooklyn Park", "Brooklyn Park"),
    GERMANTOWN("Tennessee", "Germantown", "Germantown"),
    DES_MOINES("Iowa", "Des Moines", "Des Moines"),
    AUSTIN("Texas", "Austin", "Austin"),
    IRVINE("California", "Irvine", "Irvine"),
    FREMONT("California", "Fremont", "Fremont"),
    VIRGINIA_BEACH("Virginia", "Virginia Beach", "Virginia Beach");

    private final String state;
    private final String location;
    private final String city;

    Locations(String state, String location, String city) {
        this.state = state;
        this.location = location;
        this.city = city;
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s", state, location, city);
    }
}
