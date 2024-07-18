package rest.dto;

import core.model.enums.Locations;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class AuctionRequest {
    private Locations location;
    private LocalDate dateOfAuction;
    private LocalTime timeOfAuction;
}
