package com.stackroute.searchservice.service;

import com.stackroute.searchservice.model.Book;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SearchServiceImplTest {

    private static String baseUrl;
    List<Book> books;
    @Mock
    HttpURLConnection connection;

    @Spy
    SearchServiceImpl searchService;

    @BeforeEach
    void setUp() {
        baseUrl="http://openlibrary.org/search.json";
        MockitoAnnotations.initMocks(this);
        books = new ArrayList<>();
    }

    @AfterEach
    void tearDown(){
        books=null;
    }

    @Test
    void givenAPIUrlThenEstablishConnectionToHttpUrlConnection() throws IOException {

         class UrlWrapper {

            URL url;

            public UrlWrapper() throws MalformedURLException {
                url = new URL(baseUrl);
            }

            public URLConnection openConnection() throws IOException {
                return url.openConnection();
            }
        }

        UrlWrapper url = Mockito.mock(UrlWrapper.class);
         connection = Mockito.mock(HttpURLConnection.class);
        when(url.openConnection()).thenReturn(connection);
        assertTrue(url.openConnection() instanceof HttpURLConnection);
    }

    @Test
    void givenBookTitleThenReturnListOfBookswithTitle() throws IOException{
        doReturn(books).when(searchService).searchBooks("title","the lord of rings");
        URL url = new URL(baseUrl+"?title=the+lord+of+rings&limit=1");
        List<Book> bookList = searchService.searchBooks("title","the lord of rings");
        assertEquals(books,bookList);
    }

    @Test
    void givenBookAuthorThenReturnListOfBookswithTitle() throws IOException{
        doReturn(books).when(searchService).searchBooks("author","tolkien");
        URL url = new URL(baseUrl+"?author=tolkien&limit=1");
        List<Book> bookList = searchService.searchBooks("author","tolkien");
        assertEquals(books,bookList);
    }

    @Test
    void givenInvalidUrlThenThrowException() throws IOException,RuntimeException{
        doThrow(new RuntimeException()).when(searchService).searchBooks("isbn","12345");
        URL url = new URL(baseUrl+"?isbn=1234&limit=1");
        assertThrows(RuntimeException.class,()->{
            searchService.searchBooks("isbn","12345");
        });
    }

    @Test
    void getRecommendationsBasedOnAuthorName() throws IOException{
        doReturn(books).when(searchService).getRecommendations("tolkien");
        URL url = new URL(baseUrl+"?author=tolkien&limit=1");
        List<Book> bookList = searchService.getRecommendations("tolkien");
        assertEquals(books,bookList);
    }
}