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

    public Ad archiveAd(Long id){
        Optional<Ad> ad = adRepo.findById(id);
        if (ad.isEmpty()){
            throw new AdNotFoundException(id);
        }
        Ad adArchived = ad.get().archiveAd();
        return adRepo.save(adArchived);
    }
}
