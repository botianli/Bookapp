package com.stackroute.userservice.service;

import com.stackroute.userservice.awsconfig.BucketName;
import com.stackroute.userservice.controller.UserController;
import com.stackroute.userservice.exception.UserAlreadyExistsException;
import com.stackroute.userservice.exception.UserNotFoundException;
import com.stackroute.userservice.model.User;
import com.stackroute.userservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.apache.http.entity.ContentType.*;
import static org.apache.http.entity.ContentType.IMAGE_JPEG;

@Service
public class UserServiceImpl implements UserService{
    private FileStore fileStore;
    private UserRepository userRepository;
    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserServiceImpl(UserRepository userRepository, FileStore filestore){
        this.userRepository = userRepository;
        this.fileStore = filestore;
    }

    @Override
    public User saveUser(User user) throws UserAlreadyExistsException, RuntimeException {
        return userRepository.save(user);
    }

    @Override
    public User getUserByEmailAndPassword(String email, String password) throws UserNotFoundException{
        User user = userRepository.findByEmailAndPassword(email,password);
        if(user == null){
            logger.error("Could not find user {}", email);
            throw new UserNotFoundException();
        }
        return user;
    }

    @Override
    public User deleteUser(int id) throws UserNotFoundException {
        User user = null;
        Optional optional = userRepository.findById(id);
        if(optional.isEmpty()){
            logger.error("Could not find user {}", id);
            throw new UserNotFoundException();
        }
        user = userRepository.findById(id).get();
        userRepository.deleteById(id);
        return user;
    }

    @Override
    public User getUserByEmail(String email) throws UserNotFoundException {
        User user = userRepository.findByEmail(email);
        if(user == null){
            logger.error("Could not find user {}", email);
            throw new UserNotFoundException();
        }
        return user;
    }


    @Override
    public User saveImage(int userId, MultipartFile file) throws UserNotFoundException {
        // check if file is empty
        if (file.isEmpty()) {
            logger.error("File is empty");
            throw new IllegalStateException("Cannot upload empty file");
        }

        // check if file is an image
        if (!Arrays.asList(IMAGE_PNG.getMimeType(),
                IMAGE_BMP.getMimeType(),
                IMAGE_GIF.getMimeType(),
                IMAGE_JPEG.getMimeType()).contains(file.getContentType())) {
            logger.error("Attempting to upload file which is not an image");
            throw new IllegalStateException("FIle uploaded is not an image");
        }

        // check if user exists, get user
        Optional<User> optional = userRepository.findById(userId);
        User user = null;
        if (optional.isEmpty()) {
            throw new UserNotFoundException();
        }
        user = optional.get();

        // temp store old image path
        String oldImage = null;
        if (user.getImageUrl() != null) {
            oldImage = user.getImageUrl();
        }

        // get file metadata
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));

        // save image in s3
        String path = String.format("%s/%s", BucketName.USER_IMAGE.getBucketName(), UUID.randomUUID());
        String fileName = String.format("%d", userId);
        try {
            fileStore.upload(path, fileName, Optional.of(metadata), file.getInputStream());
        } catch (IOException e) {
            throw new IllegalStateException("Failed to upload file", e);
        }

        // save image path in database
        user = optional.get();
        user.setImageUrl(path);
        userRepository.save(user);

        // delete old image
        if (oldImage != null) {
            fileStore.delete(oldImage, Integer.toString(userId));
        }

        return user;
    }

    @Override
    public byte[] downloadImage(int userId) throws UserNotFoundException {
        User user = null;
        byte[] image = null;

        // get user
        Optional<User> optional = userRepository.findById(userId);

        if (optional.isPresent()) {
            user = optional.get();
            image = fileStore.download(user.getImageUrl(), Integer.toString(userId));
        } else {
            logger.error("User not found for user {}", userId);
            throw new UserNotFoundException();
        }

        return image;
    }
}
