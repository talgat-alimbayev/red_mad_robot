package com.example.red_mad_robot.services;

import com.example.red_mad_robot.models.Ad;
import com.example.red_mad_robot.repositories.AdRepository;
import com.example.red_mad_robot.services.exceptions.AdNotFoundException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Data
@Slf4j
@Service
public class AdArchiver implements Runnable{
    private AdRepository adRepo;
    private Ad adToArchive;

    public AdArchiver(AdRepository adRepo) {
        this.adRepo = adRepo;
    }

    @Override
    public void run() {
        Long id = adToArchive.getId();
        Optional<Ad> ad = adRepo.findById(id);
        if (ad.isEmpty()){
            throw new AdNotFoundException(id);
        }
        Ad adArchived = ad.get().archiveAd();
        adRepo.save(adArchived);
        log.info("архивируем объявление " + ad.get().getId().toString());
    }


}
