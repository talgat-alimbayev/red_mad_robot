package com.example.red_mad_robot;

import com.example.red_mad_robot.controllers.UserController;
import com.example.red_mad_robot.models.User;
import com.example.red_mad_robot.services.UserService;
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


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@ContextConfiguration(classes = UserController.class)
public class UserControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    UserService userService;

    private static User user = new User();

    @BeforeAll
    static void setUp(){
        user.setId(1L);
        user.setEmail("example@example.com");
        user.setPassword("qwerty");
    }

    @Test
    public void findUserByEmailSucceeds() throws Exception {
        Mockito.when(userService.findByEmail(user.getEmail())).
                thenReturn(new ResponseEntity<>(user, HttpStatus.OK));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost:8080/api/users/find-by-email")
                        .param("email", user.getEmail().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.password").value(user.getPassword()));
    }

    @Test
    public void createUserSucceeds() throws Exception {
        Mockito.when(userService.saveUser(user)).
                thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("http://localhost:8080/api/users/new-user")
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.password").value(user.getPassword()));
    }
}
