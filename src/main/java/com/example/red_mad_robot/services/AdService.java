package com.example.red_mad_robot.services;

import com.example.red_mad_robot.models.Ad;
import com.example.red_mad_robot.repositories.AdRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdService {
    private AdRepository adRepo;

    public AdService(AdRepository adRepo) {
        this.adRepo = adRepo;
    }

    public Ad saveAd(Ad ad){
        return adRepo.save(ad);
    }

    public Iterable<Ad> findAllAds() {
        return adRepo.findAll();
    }

    public Iterable<Ad> findActiveAds() {
        return adRepo.findByStatus("active");
    }
    public Iterable<Ad> findNoBidsAds() {
        return adRepo.findByStatus("no bids");
    }
    public Iterable<Ad> findArchivedAds() {
        return adRepo.findByStatus("archived");
    }
}
