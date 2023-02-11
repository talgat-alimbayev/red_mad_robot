package com.example.red_mad_robot.repositories;

import com.example.red_mad_robot.models.Ad;
import com.example.red_mad_robot.models.Bid;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BidRepository extends CrudRepository<Bid, Long> {
    Iterable<Bid> findByAd(Ad ad);

    @Query("SELECT bid FROM Bid bid WHERE bid.ad=?1 AND bid.highest = true")
    Optional<Bid> findBidsByAdAndHighest(Ad ad);
}
