package scanner.dto;

import core.model.Auction;
import core.model.Car;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DtoConverter {

    public static CarDto toCarDTO(Car car) {
        CarDto carDto = new CarDto();
        carDto.setVin(car.getVin());
        carDto.setYear(car.getYear());
        carDto.setMake(car.getMake());
        carDto.setModel(car.getModel());
        carDto.setTrim(car.getTrim());
        carDto.setDrive(car.getDrive());
        carDto.setTransmission(car.getTransmission());
        carDto.setEngine(car.getEngine());
        carDto.setColor(car.getColor());
        carDto.setMileage(car.getMileage());
        carDto.setSoldPrice(car.getSoldPrice());
        carDto.setRunNumber(car.getRunNumber());
        carDto.setLane(car.getLane());
        carDto.setDefects(car.getDefects());
        carDto.setPicturesUrl(car.getPicturesUrl());
        carDto.setAuction(null); // Avoid circular reference by setting it to null
        return carDto;
    }

    public static AuctionDto toAuctionDto(Auction auction) {
        AuctionDto auctionDto = new AuctionDto();
        auctionDto.setDateOfAuction(auction.getDateOfAuction());
        auctionDto.setTimeOfAuction(auction.getTimeOfAuction());
        auctionDto.setLocation(auction.getLocation());
        Set<CarDto> carDTOs = auction.getCars().stream()
                .map(DtoConverter::toCarDTO)
                .collect(Collectors.toSet());
        auctionDto.setCars(carDTOs);
        return auctionDto;
    }

    public static Set<CarDto> toCarDtoSet(Set<Car> cars) {
        return cars.stream().map(DtoConverter::toCarDTO).collect(Collectors.toSet());
    }

    public static List<AuctionDto> toAuctionDtoList(List<Auction> auctions) {
        return auctions.stream().map(DtoConverter::toAuctionDto).collect(Collectors.toList());
    }
}
