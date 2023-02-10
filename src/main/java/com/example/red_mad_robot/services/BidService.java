package com.example.red_mad_robot.services;

import com.example.red_mad_robot.models.Ad;
import com.example.red_mad_robot.models.Bid;
import com.example.red_mad_robot.models.User;
import com.example.red_mad_robot.repositories.AdRepository;
import com.example.red_mad_robot.repositories.BidRepository;
import com.example.red_mad_robot.repositories.UserRepository;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Service
public class BidService {
    private BidRepository bidRepo;
    private AdRepository adRepo;
    private UserRepository userRepo;
    private AdService adService;
    private AdArchiver adArchiver;

    private final TaskScheduler taskScheduler;

    public BidService(BidRepository bidRepo, AdRepository adRepo, UserRepository userRepo, AdService adService, AdArchiver adArchiver, TaskScheduler taskScheduler) {
        this.bidRepo = bidRepo;
        this.adRepo = adRepo;
        this.userRepo = userRepo;
        this.adService = adService;
        this.adArchiver = adArchiver;
        this.taskScheduler = taskScheduler;
    }
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Bid placeBid(Bid bid){
        Optional<User> user_opt = userRepo.findById(bid.getUser().getId());
        if (user_opt.isEmpty()){
            throw new UserNotFoundException(bid.getUser().getId());
        }

        Optional<Ad> ad_opt = adRepo.findById(bid.getAd().getId());
        if (ad_opt.isEmpty()){
            throw new AdNotFoundException(bid.getAd().getId());
        }

        Ad ad = ad_opt.get();
        if (ad.getStatus().equals("no bids")){
            adArchiver.setAdToArchive(ad);
            taskScheduler.schedule(adArchiver, Instant.now().plusSeconds(ad.getAdDuration()));
        }
        return bidRepo.save(bid);
    }
}
