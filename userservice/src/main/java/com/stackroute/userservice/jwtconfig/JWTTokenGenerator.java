package com.stackroute.userservice.jwtconfig;

import com.stackroute.userservice.model.User;

import java.util.Map;

public interface JWTTokenGenerator {

    Map<String, String> generateToken(User user);
}
