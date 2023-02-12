package com.example.red_mad_robot.services;

import com.example.red_mad_robot.models.Ad;
import com.example.red_mad_robot.models.User;
import com.example.red_mad_robot.repositories.AdRepository;
import com.example.red_mad_robot.repositories.UserRepository;
import com.example.red_mad_robot.services.exceptions.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Slf4j
@Service
public class AdService {
    private AdRepository adRepo;
    private UserRepository userRepo;
    public AdService(AdRepository adRepo, UserRepository userRepo) {
        this.adRepo = adRepo;
        this.userRepo = userRepo;
    }
    public Ad saveAd(Ad ad){
        Optional<User> user = userRepo.findById(ad.getUser().getId());
        if (user.isEmpty()){
            throw new UserNotFoundException(ad.getUser().getId());
        }
        log.info("сохраняем новое объявление " + ad.toString());
        return adRepo.save(ad);
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
