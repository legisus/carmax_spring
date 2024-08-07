package rest.dto;

import core.model.enums.Locations;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class AuctionWithActionDto {
    @NonNull
    private Locations location;
    @NonNull
    private LocalDate dateOfAuction;
    @NonNull
    private LocalTime timeOfAuction;
    @NonNull
    private String actionName;


    //You are given a binary tree where each node contains an integer value.
    // Write a function to determine if there is a root-to-leaf path in the tree such that the sum of the values along the path equals a given target.
}
