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

    @PostMapping(path = "api/ads/archive/id")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void archiveAd(@RequestParam Long id){
        log.info("архивируем объявление " + id.toString());
        adService.archiveAd(id);
    }
}
