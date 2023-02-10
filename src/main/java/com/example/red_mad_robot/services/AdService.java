package com.example.red_mad_robot.services;

import com.example.red_mad_robot.models.Ad;
import com.example.red_mad_robot.repositories.AdRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdService implements Runnable{
    private AdRepository adRepo;

    public AdService(AdRepository adRepo) {
        this.adRepo = adRepo;
    }

    public Ad saveAd(Ad ad){
        return adRepo.save(ad);
    }

}
