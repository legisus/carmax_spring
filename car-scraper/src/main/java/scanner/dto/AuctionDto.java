package scanner.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import core.model.enums.Locations;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Getter
@Setter
public class AuctionDto {
    private LocalDate dateOfAuction;
    private LocalTime timeOfAuction;
    private Locations location;
    private Set<CarDto> cars;

    @JsonManagedReference
    public Set<CarDto> getCars() {
        return cars;
    }
}
