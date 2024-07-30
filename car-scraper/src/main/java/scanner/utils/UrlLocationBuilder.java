package scanner.utils;

import core.model.Auction;
import core.model.enums.Locations;

public class UrlLocationBuilder {
    public static String buildUrl(Locations location) {
        String url = "https://www.carmaxauctions.com/search?view=list&sort=runlist&direction=asc&weblocationnames=";
        return url + location.getLocation();
    }

    public static String biuldUrlForNotes(Auction auction) {
        StringBuilder sb = new StringBuilder();
        sb.append("https://www.carmaxauctions.com/search?view=detailed&sort=runlist&direction=asc&weblocationnames=");
        sb.append(auction.getLocation().getLocation());
        sb.append("&pageSize=250");
        return sb.toString();
    }
}
