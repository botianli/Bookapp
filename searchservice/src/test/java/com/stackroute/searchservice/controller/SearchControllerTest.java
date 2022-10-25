package com.stackroute.searchservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.searchservice.model.Book;
import com.stackroute.searchservice.service.SearchService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SearchControllerTest {

    private MockMvc mockMvc;

    @Mock
    SearchService searchService;

    List<Book> books =null;
    private Book book;

    @InjectMocks
    private SearchController searchController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(searchController).build();
        books = new ArrayList<>();
        book = new Book();
    }

    @AfterEach
    void tearDown() {
        books = null;
    }

    @Test
    void givenTitleThenReturnListOfBooksBasedOnTitle() throws Exception {
        when(searchService.searchBooks("title","the lord of rings")).thenReturn(books);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/search?searchOption=title&searchInput=the lord of rings")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(book)))
                .andDo(MockMvcResultHandlers.print())
               .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void givenAuthorThenReturnListOfBooksBasedOnAuthor() throws Exception {
        String authorName = "tolkien";
        when(searchService.searchBooks("author",authorName)).thenReturn(books);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/search?searchOption=author&searchInput="+authorName)
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(book)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void giveRecommendationstoUserBasedOnAuthorName() throws Exception {
        when(searchService.getRecommendations("tolkien")).thenReturn(books);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/recommend/tolkien")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(book)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}