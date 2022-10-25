package com.stackroute.userservice.service;

import com.stackroute.userservice.exception.UserAlreadyExistsException;
import com.stackroute.userservice.exception.UserNotFoundException;
import com.stackroute.userservice.model.User;
import com.stackroute.userservice.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private Optional optional;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        user = new User(1,"Loreum","Ipsum","test@email.com","password","/");
        optional = Optional.of(user);
    }

    @AfterEach
    void tearDown() {
        user = null;
    }

    @Test
    void givenUserToSaveThenShouldReturnSavedUser() throws UserAlreadyExistsException {
        when(userRepository.save(any())).thenReturn(user);
        assertEquals(user,userService.saveUser(user));
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void givenUserToSaveThenShouldNotSavedUser() throws UserAlreadyExistsException {
        when(userRepository.save(any())).thenThrow(new UserAlreadyExistsException());
        assertThrows(UserAlreadyExistsException.class, () -> userService.saveUser(user));
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void givenUserIdToDeleteThenShouldReturnDeletedUser() throws UserNotFoundException {
        when(userRepository.findById(user.getUserId())).thenReturn(optional);
        User deletedUser = userService.deleteUser(1);
        assertEquals(1,deletedUser.getUserId());

        verify(userRepository, times(2)).findById(user.getUserId());
        verify(userRepository, times(1)).deleteById(user.getUserId());
    }

    @Test
    void givenUserIdToDeleteThenShouldNotDeletedUser() {
        when(userRepository.findById(user.getUserId())).thenThrow(UserNotFoundException.class);
        Assertions.assertThrows(UserNotFoundException.class, () ->
                userService.deleteUser(1));
        verify(userRepository, times(1)).findById(user.getUserId());
    }

    @Test
    void givenUserEmailThenShouldReturnRespectiveUser() throws UserNotFoundException {
        when(userRepository.findByEmail(anyString())).thenReturn(user);
        User retrievedUser = userService.getUserByEmail(user.getEmail());
        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void givenUserEmailThenShouldNotReturnRespectiveUser() {
        when(userRepository.findByEmail(user.getEmail())).thenThrow(UserNotFoundException.class);
        Assertions.assertThrows(UserNotFoundException.class, () ->
                userService.getUserByEmail("test@email.com"));
        verify(userRepository, times(1)).findByEmail(user.getEmail());
    }

    @Test
    void givenUserEmailAndPasswordThenShouldReturnRespectiveUser(){
        when(userRepository.findByEmailAndPassword(anyString(),anyString())).thenReturn(user);
        User retrievedUser = userService.getUserByEmailAndPassword(user.getEmail(), user.getPassword());
        assertEquals("test@email.com",user.getEmail());
        assertEquals("password",user.getPassword());
        verify(userRepository, times(1)).findByEmailAndPassword(anyString(),anyString());
    }

    @Test
    void givenUserEmailAndPasswordThenShouldNotReturnRespectiveUser(){
        when(userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword())).thenThrow(UserNotFoundException.class);
        Assertions.assertThrows(UserNotFoundException.class, () ->
                userService.getUserByEmailAndPassword("test@email.com","password"));
        verify(userRepository, times(1)).findByEmailAndPassword(user.getEmail(), user.getPassword());
    }
}