package com.example.red_mad_robot;

import com.example.red_mad_robot.controllers.AdController;
import com.example.red_mad_robot.controllers.BidController;
import com.example.red_mad_robot.models.Ad;
import com.example.red_mad_robot.models.Bid;
import com.example.red_mad_robot.models.User;
import com.example.red_mad_robot.services.AdService;
import com.example.red_mad_robot.services.BidService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BidController.class)
@ContextConfiguration(classes = BidController.class)
public class BidControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    BidService bidService;

    private static User user = new User();
    private static Bid bid = new Bid();
    private static Ad ad = new Ad();

    @BeforeAll
    static void setUp(){
        user.setId(1L);
        user.setEmail("example@example.com");
        user.setPassword("qwerty");

        ad.setId(1L);
        ad.setDescription("тестовое объявление без ставок");
        ad.setStartPrice(new BigDecimal(200.5));
        ad.setImageLink("https://images.google.com/");
        ad.setAdDurationMinutes(1L);
        ad.setUser(user);

        bid.setId(1L);
        bid.setAd(ad);
        bid.setBid(new BigDecimal(300.5));
        bid.setUser(user);
        bid.setHighest(true);

    }

    @Test
    public void findAllBidsSucceeds() throws Exception {
        Mockito.when(bidService.findAllBids()).
                thenReturn(Arrays.asList(bid));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost:8080/api/bids/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(bid.getId()))
                .andExpect(jsonPath("$[0].ad.id").value(bid.getAd().getId()))
                .andExpect(jsonPath("$[0].ad.description").value(bid.getAd().getDescription()))
                .andExpect(jsonPath("$[0].ad.startPrice").value(bid.getAd().getStartPrice()))
                .andExpect(jsonPath("$[0].ad.imageLink").value(bid.getAd().getImageLink()))
                .andExpect(jsonPath("$[0].ad.status").value(bid.getAd().getStatus()))
                .andExpect(jsonPath("$[0].ad.adDurationMinutes").value(bid.getAd().getAdDurationMinutes()))
                .andExpect(jsonPath("$[0].ad.user").value(bid.getAd().getUser()))
                .andExpect(jsonPath("$[0].bid").value(bid.getBid()))
                .andExpect(jsonPath("$[0].user").value(bid.getUser()));
    }

    @Test
    public void findBidsByAdSucceeds() throws Exception {
        Mockito.when(bidService.findAllBidsByAd(ad.getId())).
                thenReturn(Arrays.asList(bid));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost:8080/api/bids/by-ad")
                        .param("adId", ad.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(bid.getId()))
                .andExpect(jsonPath("$[0].ad.id").value(bid.getAd().getId()))
                .andExpect(jsonPath("$[0].ad.description").value(bid.getAd().getDescription()))
                .andExpect(jsonPath("$[0].ad.startPrice").value(bid.getAd().getStartPrice()))
                .andExpect(jsonPath("$[0].ad.imageLink").value(bid.getAd().getImageLink()))
                .andExpect(jsonPath("$[0].ad.status").value(bid.getAd().getStatus()))
                .andExpect(jsonPath("$[0].ad.adDurationMinutes").value(bid.getAd().getAdDurationMinutes()))
                .andExpect(jsonPath("$[0].ad.user").value(bid.getAd().getUser()))
                .andExpect(jsonPath("$[0].bid").value(bid.getBid()))
                .andExpect(jsonPath("$[0].user").value(bid.getUser()));
    }

    @Test
    public void findHighestBidsByAdSucceeds() throws Exception {
        Mockito.when(bidService.findTheHighestBid(ad.getId())).
                thenReturn(bid);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost:8080/api/bids/highest-bid/by-ad")
                        .param("adId", ad.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(bid.getId()))
                .andExpect(jsonPath("$.ad.id").value(bid.getAd().getId()))
                .andExpect(jsonPath("$.ad.description").value(bid.getAd().getDescription()))
                .andExpect(jsonPath("$.ad.startPrice").value(bid.getAd().getStartPrice()))
                .andExpect(jsonPath("$.ad.imageLink").value(bid.getAd().getImageLink()))
                .andExpect(jsonPath("$.ad.status").value(bid.getAd().getStatus()))
                .andExpect(jsonPath("$.ad.adDurationMinutes").value(bid.getAd().getAdDurationMinutes()))
                .andExpect(jsonPath("$.ad.user").value(bid.getAd().getUser()))
                .andExpect(jsonPath("$.bid").value(bid.getBid()))
                .andExpect(jsonPath("$.user").value(bid.getUser()));
    }


    @Test
    public void placeBidSucceeds() throws Exception {
        Mockito.doNothing().when(bidService).placeBid(bid);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("http://localhost:8080/api/bids/create")
                        .content(objectMapper.writeValueAsString(bid))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
}
