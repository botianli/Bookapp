package com.stackroute.userservice.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.stackroute.userservice.exception.UserAlreadyExistsException;
import com.stackroute.userservice.exception.UserNotFoundException;
import com.stackroute.userservice.jwtconfig.JWTTokenGenerator;
import com.stackroute.userservice.model.User;
import com.stackroute.userservice.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private UserService userService;
    private JWTTokenGenerator jwtTokenGenerator;

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Value("${data.controller.exception.emptyCredentials}")
    private String emptyCredentials;
    @Value("${data.controller.exception.invalidCredentials}")
    private String invalidCredentials;
    @Value("${app.validationConfirmationMessage}")
    private String validationConfirmationMessage;

    @Autowired
    public UserController(UserService userService, JWTTokenGenerator jwtTokenGenerator) {
        this.userService=userService;
        this.jwtTokenGenerator = jwtTokenGenerator;
    }
    /**
     * save a new User
     */
    @ApiOperation(value = "Add new User ", notes = "Add new User to CGI library application")
    @PostMapping("/signup")
    public ResponseEntity<?> saveUser(@RequestBody User user)throws UserAlreadyExistsException {
        try {
            User savedUser = userService.saveUser(user);
            logger.info("User successfully added");
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
        }catch (Exception e){
            logger.error("A problem occurred, User data not saved");
            return new ResponseEntity<>("User Already exists", HttpStatus.CONFLICT);
        }
    }

    /**
     * login user by email and password
     */
    @ApiOperation(value = "User logs into application ",
            notes = "User logs into application by providing email and password")
    @PostMapping("/login")
    public ResponseEntity<?> getUserByEmailAndPassword(@RequestBody User user) throws UserNotFoundException{
        if(user.getEmail() == null || user.getPassword() == null){
            logger.error("Problem occurred, Either email or password is empty");
            throw new UserNotFoundException(emptyCredentials);
        }
        User loggedUser = userService.getUserByEmailAndPassword(user.getEmail(),user.getPassword());
        if(loggedUser == null){
            logger.error("User doesn't exist for provided email");
            throw new UserNotFoundException(invalidCredentials);
        }
        if (!(user.getPassword().equals(loggedUser.getPassword()))) {
            logger.error("Entered password is incorrect");
            throw new UserNotFoundException(invalidCredentials);
        }
        logger.info("User successfully logged in");
        return new ResponseEntity<>(jwtTokenGenerator.generateToken(loggedUser), HttpStatus.OK);
    }

    /**
     * delete user by id
     */
    @ApiOperation(value = "User deletes his profile ")
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<User> getUserAfterDelete(@PathVariable("userId") int userId)throws UserNotFoundException {
        logger.info("User profile deleted");
        return new ResponseEntity<>(userService.deleteUser(userId), HttpStatus.OK);
    }

    /**
     * get user by email
     */
    @PostMapping("/search")
    public ResponseEntity<?> getUserByEmail(@RequestBody ObjectNode object) throws UserNotFoundException{
            logger.info("User found, retrieved user details");
            return new ResponseEntity<>(userService.getUserByEmail(object.get("email").asText()),HttpStatus.OK);
    }

    /**
     * upload profile picture using user id
     */
    @ApiOperation(value = "Upload user image",
            notes = "Requires a token and the user's id",
            response = User.class)
    @PutMapping(
            path = "/image/{userId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<User> saveUserImage(@PathVariable("userId") int userId,
                                              @RequestParam("file") MultipartFile file) {
        logger.info("Called saveImage() for user {}", userId);
        return new ResponseEntity<>(userService.saveImage(userId, file), HttpStatus.CREATED);
    }

    /**
     * get profile picture by user id
     */
    @ApiOperation(value = "Get user's profile picture",
            notes = "Requires a token and the user's id",
            response = byte.class)
    @GetMapping(value = "/image/{userId}")
    public ResponseEntity<byte[]> downloadUserImage(@PathVariable("userId") int userId) throws UserNotFoundException {
        logger.info("Called downloadImage() for user {}", userId);
        return new ResponseEntity<byte[]>(userService.downloadImage(userId), HttpStatus.OK);
    }

    /**
     * Authorize token
     */
    @GetMapping("/auth")
    public String authUser(){
        return validationConfirmationMessage;
    }

}
