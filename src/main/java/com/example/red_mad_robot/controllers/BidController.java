package com.example.red_mad_robot.controllers;

import com.example.red_mad_robot.models.Bid;
import com.example.red_mad_robot.services.BidService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(produces = "application/json")
@Slf4j
public class BidController {
    private BidService bidService;

    public BidController(BidService bidService) {
        this.bidService = bidService;
    }

    @PostMapping(path = "api/bids/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void placeBid(@RequestBody @Valid Bid bid){
        bidService.placeBid(bid);
    }
}
