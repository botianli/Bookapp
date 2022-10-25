package com.stackroute.userservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.userservice.exception.GlobalExceptionHandler;
import com.stackroute.userservice.exception.UserAlreadyExistsException;
import com.stackroute.userservice.exception.UserNotFoundException;
import com.stackroute.userservice.model.User;
import com.stackroute.userservice.service.UserService;
import com.stackroute.userservice.util.JwtTokenGeneratorTestUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private MockMvc mockMvc;
    @Mock
    UserService userService;

    @InjectMocks
    private UserController userController;
    private User user;
    private User user1;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).setControllerAdvice(new GlobalExceptionHandler()).build();
        user = new User(1,"Loreum","Ipsum","test@email.com","password","/");
        user1 = new User("test@email.com","password");
    }

    @AfterEach
    void tearDown() {
        user = null;
    }

    @Test
    void givenUserToSaveThenShouldReturnSavedUser() throws Exception {
        when(userService.saveUser(any())).thenReturn(user);
        mockMvc.perform(post("/api/v1/users/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user)))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void givenUserToSaveThenShouldNotReturnSavedUser() throws Exception {
        when(userService.saveUser(any())).thenThrow(UserAlreadyExistsException.class);
        mockMvc.perform(post("/api/v1/users/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user)))
                .andExpect(status().isConflict())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void givenUserIdToDeleteThenShouldReturnDeletedUser() throws Exception {
        when(userService.deleteUser(user.getUserId())).thenReturn(user);
        mockMvc.perform(delete("/api/v1/users/delete/1")
                .header("authorization","Bearer "+new JwtTokenGeneratorTestUtil().generateToken(user1))
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user)))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    void givenUserIdToDeleteThenShouldNotReturnDeletedUser() throws Exception {
        when(userService.deleteUser(user.getUserId())).thenThrow(UserNotFoundException.class);
        mockMvc.perform(delete("/api/v1/users/delete/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user)))
                .andExpect(MockMvcResultMatchers.status().isNotFound()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    void givenTokenToAuthenticateThenShouldReturnRespectiveMessage() throws Exception {
        mockMvc.perform(get("/api/v1/users/auth")
                .header("authorization","Bearer "+new JwtTokenGeneratorTestUtil().generateToken(user1)))
                .andExpect(status().isOk());
                //.andExpect(content().string("You have been validated"));
    }

    @Test
    void givenUserEmailThenShouldReturnRespectiveUser() throws Exception {
        when(userService.getUserByEmail(user.getEmail())).thenReturn(user);
        mockMvc.perform(post("/api/v1/users/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user)))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
    }


    @Test
    void givenUserIdThenShouldReturnImage() throws Exception {
        MockMultipartFile file = new MockMultipartFile("image.jpg","image1".getBytes());
        when(userService.downloadImage(user.getUserId())).thenReturn(file.getBytes());
        mockMvc.perform(get("/api/v1/users/image/1")
                .header("authorization","Bearer "+new JwtTokenGeneratorTestUtil().generateToken(user)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void givenFakeUserIdThenShouldThrowError() throws Exception {
        when(userService.downloadImage(user.getUserId())).thenThrow(UserNotFoundException.class);
        mockMvc.perform(get("/api/v1/users/image/1")
                .header("authorization","Bearer "+new JwtTokenGeneratorTestUtil().generateToken(user)))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}