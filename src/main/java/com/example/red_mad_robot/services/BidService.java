package com.example.red_mad_robot.services;

import com.example.red_mad_robot.models.Bid;
import com.example.red_mad_robot.repositories.AdRepository;
import com.example.red_mad_robot.repositories.BidRepository;
import org.springframework.stereotype.Service;

@Service
public class BidService {
    private BidRepository bidRepo;
    private AdRepository adRepo;

    public BidService(BidRepository bidRepo, AdRepository adRepo) {
        this.bidRepo = bidRepo;
        this.adRepo = adRepo;
    }

    public Bid placeBid(Bid bid){
        return bidRepo.save(bid);
    }
}
