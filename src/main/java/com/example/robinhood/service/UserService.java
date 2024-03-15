package com.example.robinhood.service;


import com.example.robinhood.entity.User;

import java.util.List;

public interface UserService {

    User registerNewUser(User user);

    List<User> findAll();

}
