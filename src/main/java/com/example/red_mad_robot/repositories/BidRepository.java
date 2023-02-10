package com.example.red_mad_robot.repositories;

import com.example.red_mad_robot.models.Bid;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BidRepository extends CrudRepository<Bid, Long> {
    Iterable<Bid> findByAd(Long id);

    @Query("SELECT bid FROM bids bid WHERE bid.highest = true AND bid.ad=?1")
    Bid findBidsByAdAndHighest(Long adId);
}
