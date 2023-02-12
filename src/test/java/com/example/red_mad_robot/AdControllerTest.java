package com.example.red_mad_robot;

import com.example.red_mad_robot.controllers.AdController;
import com.example.red_mad_robot.controllers.UserController;
import com.example.red_mad_robot.models.Ad;
import com.example.red_mad_robot.models.User;
import com.example.red_mad_robot.services.AdService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AdController.class)
@ContextConfiguration(classes = AdController.class)
public class AdControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    AdService adService;

    private static User user = new User();
    private static Ad adActive = new Ad();
    private static Ad adNoBids = new Ad();
    private static Ad adArchived = new Ad();

    @BeforeAll
    static void setUp(){
        user.setId(1L);
        user.setEmail("example@example.com");
        user.setPassword("qwerty");

        adActive.setId(1L);
        adActive.setDescription("активное тестовое объявление");
        adActive.setStartPrice(new BigDecimal(100.5));
        adActive.setImageLink("https://images.google.com/");
        adActive.setStatus("active");
        adActive.setAdDurationMinutes(1L);
        adActive.setUser(user);

        adNoBids.setId(2L);
        adNoBids.setDescription("тестовое объявление без ставок");
        adNoBids.setStartPrice(new BigDecimal(200.5));
        adNoBids.setImageLink("https://images.google.com/");
        adNoBids.setAdDurationMinutes(2L);
        adNoBids.setUser(user);

        adArchived.setId(3L);
        adArchived.setDescription("тестовое объявление в архиве");
        adArchived.setStartPrice(new BigDecimal(300.5));
        adArchived.setImageLink("https://images.google.com/");
        adActive.setStatus("archived");
        adArchived.setAdDurationMinutes(3L);
        adArchived.setUser(user);
    }

    @Test
    public void findActiveAdsSucceeds() throws Exception {
        Mockito.when(adService.findActiveAds()).
                thenReturn(Arrays.asList(adActive));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost:8080/api/ads/active")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(adActive.getId()))
                .andExpect(jsonPath("$[0].description").value(adActive.getDescription()))
                .andExpect(jsonPath("$[0].startPrice").value(adActive.getStartPrice()))
                .andExpect(jsonPath("$[0].status").value(adActive.getStatus()))
                .andExpect(jsonPath("$[0].adDurationMinutes").value(adActive.getAdDurationMinutes()))
                .andExpect(jsonPath("$[0].user").value(adActive.getUser()));
    }

    @Test
    public void findNoBidsAdsSucceeds() throws Exception {
        Mockito.when(adService.findNoBidsAds()).
                thenReturn(Arrays.asList(adNoBids));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost:8080/api/ads/no-bids")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(adNoBids.getId()))
                .andExpect(jsonPath("$[0].description").value(adNoBids.getDescription()))
                .andExpect(jsonPath("$[0].startPrice").value(adNoBids.getStartPrice()))
                .andExpect(jsonPath("$[0].status").value(adNoBids.getStatus()))
                .andExpect(jsonPath("$[0].adDurationMinutes").value(adNoBids.getAdDurationMinutes()))
                .andExpect(jsonPath("$[0].user").value(adNoBids.getUser()));
    }

    @Test
    public void findArchivedAdsSucceeds() throws Exception {
        Mockito.when(adService.findArchivedAds()).
                thenReturn(Arrays.asList(adArchived));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost:8080/api/ads/archived")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(adArchived.getId()))
                .andExpect(jsonPath("$[0].description").value(adArchived.getDescription()))
                .andExpect(jsonPath("$[0].startPrice").value(adArchived.getStartPrice()))
                .andExpect(jsonPath("$[0].status").value(adArchived.getStatus()))
                .andExpect(jsonPath("$[0].adDurationMinutes").value(adArchived.getAdDurationMinutes()))
                .andExpect(jsonPath("$[0].user").value(adArchived.getUser()));
    }

    @Test
    public void createAdSucceeds() throws Exception {
        Mockito.when(adService.saveAd(adNoBids)).
                thenReturn(adNoBids);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("http://localhost:8080/api/ads/create")
                        .content(objectMapper.writeValueAsString(adNoBids))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
}
