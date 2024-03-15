package com.example.robinhood.controller;

import com.example.robinhood.entity.User;
import com.example.robinhood.model.request.UserDataRequestModel;
import com.example.robinhood.service.UserService;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RateLimiter(name = "backendA" , fallbackMethod = "limitHandler")
    @GetMapping("/getData")
    public ResponseEntity<List<User>> getDataUser() {
        return ResponseEntity.ok(userService.findAll());
    }


    @RateLimiter(name = "oneTime" , fallbackMethod = "limitHandler")
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody UserDataRequestModel request) {
        User user = new User();
        BeanUtils.copyProperties(request, user);
        return ResponseEntity.ok(userService.registerNewUser(user));
    }

    public ResponseEntity<String> limitHandler(RequestNotPermitted t) {
        return new ResponseEntity<>("Too many Request", HttpStatus.TOO_MANY_REQUESTS);
    }


}
