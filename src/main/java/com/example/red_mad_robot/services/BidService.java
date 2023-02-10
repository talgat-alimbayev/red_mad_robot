package com.example.red_mad_robot.services;

import com.example.red_mad_robot.models.Ad;
import com.example.red_mad_robot.models.Bid;
import com.example.red_mad_robot.models.User;
import com.example.red_mad_robot.repositories.AdRepository;
import com.example.red_mad_robot.repositories.BidRepository;
import com.example.red_mad_robot.repositories.UserRepository;
import com.example.red_mad_robot.services.exceptions.AdNotFoundException;
import com.example.red_mad_robot.services.exceptions.ArchivedAdException;
import com.example.red_mad_robot.services.exceptions.LowBidException;
import com.example.red_mad_robot.services.exceptions.UserNotFoundException;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
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
    public void placeBid(Bid bid){
        Optional<User> user_opt = userRepo.findById(bid.getUser().getId());
        if (user_opt.isEmpty()){
            throw new UserNotFoundException(bid.getUser().getId());
        }

        Optional<Ad> ad_opt = adRepo.findById(bid.getAd().getId());
        if (ad_opt.isEmpty()){
            throw new AdNotFoundException(bid.getAd().getId());
        }
        // if no bids then start the countdown and schedule the ad archiver
        Ad ad = ad_opt.get();
        if (ad.getStatus().equals("no bids")){
            adArchiver.setAdToArchive(ad);
            taskScheduler.schedule(adArchiver, Instant.now().plusSeconds(ad.getAdDurationMinutes()*60));
            ad.setStatus("active");
            adRepo.save(ad);

            bid.setHighest(true);
            bidRepo.save(bid);
        } else if (ad.getStatus().equals("archived")) {
            throw new ArchivedAdException(ad.getId());
        }

        Bid highestBid = findTheHighestBid(ad);
        if (bid.getBid().compareTo(highestBid.getBid()) == 1){
            highestBid.setHighest(false);
            bidRepo.save(highestBid);

            bid.setHighest(true);
            bidRepo.save(bid);
        }
        else {
            throw new LowBidException();
        }
    }

    private Bid findTheHighestBid(Ad ad){
        return bidRepo.findBidsByAdAndHighest(ad.getId());
    }
}
