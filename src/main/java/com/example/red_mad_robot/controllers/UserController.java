package com.example.red_mad_robot.controllers;

import com.example.red_mad_robot.models.User;
import com.example.red_mad_robot.services.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(produces = "application/json")
@Slf4j
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "api/users/find-by-email")
    public ResponseEntity<User> findUserByUsername(@RequestParam String email){
        log.info("ищем пользователя по email=" + email);
        return userService.findByEmail(email);
    }

    @PostMapping(path = "api/users/new-user")
    @ResponseStatus(HttpStatus.CREATED)
    public User saveUser(@RequestBody @Valid User user){
        log.info("сохраняем нового пользователя в БД: " + user.toString());
        return userService.saveUser(user);
    }
}
