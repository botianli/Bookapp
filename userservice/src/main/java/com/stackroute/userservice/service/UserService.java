package com.stackroute.userservice.service;

import com.stackroute.userservice.exception.UserAlreadyExistsException;
import com.stackroute.userservice.exception.UserNotFoundException;
import com.stackroute.userservice.model.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    User saveUser(User user) throws UserAlreadyExistsException;
    User getUserByEmailAndPassword(String email,String password) throws UserNotFoundException;
    User deleteUser(int id) throws UserNotFoundException;
    User getUserByEmail(String email) throws UserNotFoundException;
    User saveImage(int userId, MultipartFile file);
    byte[] downloadImage(int id) throws UserNotFoundException;
}
