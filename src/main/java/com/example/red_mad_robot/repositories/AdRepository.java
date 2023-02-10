package com.example.red_mad_robot.repositories;

import com.example.red_mad_robot.models.Ad;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AdRepository extends CrudRepository<Ad, Long> {
    Optional<Ad> findByUser(String user);
    Iterable<Ad> findByStatus(String status);
}
