package com.example.red_mad_robot.services;

import com.example.red_mad_robot.models.User;
import com.example.red_mad_robot.repositories.UserRepository;
import com.example.red_mad_robot.services.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public ResponseEntity<User> findByEmail(String email){
        Optional<User> user = userRepo.findByEmail(email);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }
        throw new UserNotFoundException(email);
    }

    public ResponseEntity<User> findById(Long id){
        Optional<User> user = userRepo.findById(id);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }
        throw new UserNotFoundException(id);
    }

    public User saveUser(User user){
        return userRepo.save(user);
    }
}
