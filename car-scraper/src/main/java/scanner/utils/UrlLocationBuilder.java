package scanner.utils;

import core.model.enums.Locations;

public class UrlLocationBuilder {
    public static String buildUrl(Locations location) {

        String url = "https://www.carmaxauctions.com/search?view=list&sort=runlist&direction=asc&weblocationnames=";

        return url + location.getLocation();
    }
}
