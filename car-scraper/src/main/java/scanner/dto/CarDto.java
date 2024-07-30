package scanner.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import core.model.enums.Drive;
import core.model.enums.Transmission;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarDto {
    private String vin;
    private Integer year;
    private String make;
    private String model;
    private String trim;
    private Drive drive;
    private Transmission transmission;
    private String engine;
    private String color;
    private Integer mileage;
    private Integer soldPrice;
    private Integer runNumber;
    private String lane;
    private String defects;
    private String picturesUrl;
    private AuctionDto auction;

    @JsonBackReference
    public AuctionDto getAuction() {
        return auction;
    }
}
