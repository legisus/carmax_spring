package rest.dto;

import core.model.enums.Locations;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class AuctionRequestDto {
    @NonNull
    private Locations location;
    @NonNull
    private LocalDate dateOfAuction;
    @NonNull
    private LocalTime timeOfAuction;


}
