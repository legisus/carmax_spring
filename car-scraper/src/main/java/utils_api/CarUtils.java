package utils_api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import core.model.Auction;
import core.model.Car;
import scanner.dto.AuctionDto;
import scanner.dto.DtoConverter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CarUtils {

    public static void saveCarsListToJsonFile(List<Car> cars, String path) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Convert the list of cars to JSON string
            String json = mapper.writeValueAsString(cars);
            // Save this string to a properties file
            Files.write(Paths.get(path), json.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveAuctionsListToJsonFile(List<Auction> auctions, String path) {
        ObjectMapper mapper = new ObjectMapper();
        // Register the JavaTimeModule to handle Java 8 date/time types
        mapper.registerModule(new JavaTimeModule());

        // Convert model objects to DTOs
        List<AuctionDto> auctionDTOs = DtoConverter.toAuctionDtoList(auctions);

        try {
            // Convert the list of auctionDTOs to JSON string
            String json = mapper.writeValueAsString(auctionDTOs);
            // Save this string to a file
            Files.write(Paths.get(path), json.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveAuctionToJsonFile(Auction auction, String path) {
        ObjectMapper mapper = new ObjectMapper();
        // Register the JavaTimeModule to handle Java 8 date/time types
        mapper.registerModule(new JavaTimeModule());

        // Convert model objects to DTOs
        AuctionDto auctionDto = DtoConverter.toAuctionDto(auction);

        try {
            // Convert the list of auctionDTOs to JSON string
            String json = mapper.writeValueAsString(auctionDto);
            // Save this string to a file
            Files.write(Paths.get(path), json.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Car> loadCarsListFromJsonFile(String path) {
        ObjectMapper mapper = new ObjectMapper();
        List<Car> cars = null;
        try {
            // Read the JSON string from the file
            String json = new String(Files.readAllBytes(Paths.get(path)));
            // Convert the JSON string back to a list of cars
            cars = mapper.readValue(json, new TypeReference<List<Car>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cars;
    }

    public static Optional<Car> findCarByVin(List<Car> cars, String vin) {
        if (cars == null || vin == null) {
            return Optional.empty(); // Return empty if the list or vin is null
        }

        return cars.stream()
                .filter(car -> car.getVin().equals(vin))
                .findFirst();
    }

    public static List<Car> getUniqueCars(List<Car> carList, List<Car> newCarList) {
        if(carList == null) {
            return newCarList; // Return the new list if the old list is null
        }

        if (newCarList == null) {
            return Collections.emptyList(); // Return an empty list if either list is null
        }

        return newCarList.stream()
                .filter(newCar -> newCar != null && newCar.getVin() != null) // Check for null cars and null VINs
                .filter(newCar -> findCarByVin(carList, newCar.getVin()).isEmpty())
                .toList();
    }
}
