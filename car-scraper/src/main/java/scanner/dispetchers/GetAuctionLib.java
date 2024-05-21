package scanner.dispetchers;

import core.model.Auction;
import core.model.Car;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
public class GetAuctionLib {
    private String path;
    private List<Car> carList;
    private List<Car> newCarList;
    private List<Car> uniqueCars;
    private Auction auction;
    private String url;
}
