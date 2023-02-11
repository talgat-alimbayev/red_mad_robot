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
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
@Slf4j
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

        // сначала проверяем на наличие пользователя
        Optional<User> user_opt = userRepo.findById(bid.getUser().getId());
        if (user_opt.isEmpty()){
            throw new UserNotFoundException(bid.getUser().getId());
        }
        // потом на наличие объявления
        Optional<Ad> ad_opt = adRepo.findById(bid.getAd().getId());
        if (ad_opt.isEmpty()){
            throw new AdNotFoundException(bid.getAd().getId());
        }
        // потом проверяем находится ли объявление в архиве
        Ad ad = ad_opt.get();
        if (ad.getStatus().equals("archived")) {
            throw new ArchivedAdException(ad.getId());
        }


        Optional<Bid> currentHighestBid_opt = findTheHighestBid(ad); // это будет пустое в случае, если это первая ставка

        if (currentHighestBid_opt.isPresent() &&
                bid.getBid().compareTo(currentHighestBid_opt.get().getBid()) == 1){
            Bid currentHighestBid = currentHighestBid_opt.get();
            currentHighestBid.setHighest(false);
            User userToNotify = userRepo.findById(currentHighestBid.getId()).get();
            log.info("Отправляем уведомление на " + userToNotify.getEmail().strip() + ", что их ставка больше неактуальна");
            bidRepo.save(currentHighestBid);

            bid.setHighest(true);
            bidRepo.save(bid);
            log.info("Принята новая ставка " + bid.toString());
        }
        else if (currentHighestBid_opt.isPresent() &&
                bid.getBid().compareTo(currentHighestBid_opt.get().getBid()) != 1) {
            throw new LowBidException();
        }

        // если у объявления нет ставок, то переводим объявление в статус active и сохраняем ставку, делая эту ставку максимально
        if (ad.getStatus().strip().equals("no bids") &&
                currentHighestBid_opt.isEmpty()){
            adArchiver.setAdToArchive(ad);
            taskScheduler.schedule(adArchiver, Instant.now().plusSeconds(ad.adDurationSeconds()));
            ad.setStatus("active");
            adRepo.save(ad);

            bid.setHighest(true);
            bidRepo.save(bid);
            log.info("Создана ставка " + bid.toString() + ". Начат отсчет");
        }

    }

    public Iterable<Bid> findAllBids(){
        return bidRepo.findAll();
    }

    public Iterable<Bid> findAllBidsByAd(Long adId){
        Optional<Ad> ad = adRepo.findById(adId);
        if (ad.isEmpty()){
            throw new AdNotFoundException(adId);
        }
        return bidRepo.findByAd(ad.get());
    }

    private Optional<Bid> findTheHighestBid(Ad ad){
        return bidRepo.findBidsByAdAndHighest(ad);
    }
}
