package com.example.red_mad_robot.repositories;

import com.example.red_mad_robot.models.Bid;
import org.springframework.data.repository.CrudRepository;

public interface BidRepository extends CrudRepository<Bid, Long> {
    Iterable<Bid> findByAd(Long id);
}
