package com.example.red_mad_robot.controllers;

import com.example.red_mad_robot.models.Ad;
import com.example.red_mad_robot.services.AdService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class AdController {
    private AdService adService;

    public AdController(AdService adService) {
        this.adService = adService;
    }

    @PostMapping(path = "api/ads/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createAd(@RequestBody @Valid Ad ad){
        log.info("сохраняем новое объявление " + ad.toString());
        adService.saveAd(ad);
    }

    @GetMapping(path = "api/ads/active")
    public Iterable<Ad> findActiveAds(){
        log.info("поиск активных объявлений");
        return adService.findActiveAds();
    }

    @GetMapping(path = "api/ads/no-bids")
    public Iterable<Ad> findNoBidsAds(){
        log.info("поиск объявлений без ставок");
        return adService.findNoBidsAds();
    }
    @GetMapping(path = "api/ads/archived")
    public Iterable<Ad> findArchivedAds(){
        log.info("поиск объявлений в архиве");
        return adService.findArchivedAds();
    }
}
