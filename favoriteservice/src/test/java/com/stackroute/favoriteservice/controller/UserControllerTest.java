package com.stackroute.favoriteservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.favoriteservice.domain.Book;
import com.stackroute.favoriteservice.domain.User;
import com.stackroute.favoriteservice.exception.BookAlreadyExistsException;
import com.stackroute.favoriteservice.exception.BookNotFoundException;
import com.stackroute.favoriteservice.exception.GlobalExceptionHandler;
import com.stackroute.favoriteservice.exception.UserNotFoundException;
import com.stackroute.favoriteservice.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    private MockMvc mockMvc;
    @Mock
    private UserService service;
    @InjectMocks
    private UserController controller;

    private User user;
    private Book book1, book2, book3;
    private List<Book> books;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(new GlobalExceptionHandler()).build();

        book1 = new Book("isbn123", "lotr1", "tolkien", "fantasy", "http://img.com/lotr1");
        book2 = new Book("isbn456", "lotr2", "tolkien", "fantasy", "http://img.com/lotr2");
        book3 = new Book("isbn789", "lotr3", "tolkien", "fantasy", "http://img.com/lotr3");

        user = new User(1, new ArrayList<>(List.of(book1, book2)));
    }

    @AfterEach
    void tearDown() {
        user = null;
        book1 = book2 = book3 = null;
    }

    @Test
    void givenRealUserToRetrieveThenShouldReturnBooks() throws Exception {
        when(service.getBooks(user.getId())).thenReturn(user.getBooks());
        mockMvc.perform(get("/api/v1/books/1"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

        verify(service).getBooks(user.getId());
        verify(service, times(1)).getBooks(user.getId());
    }

    @Test
    void givenFakeUserToRetrieveThenShouldThrowError() throws Exception {
        when(service.getBooks(user.getId())).thenThrow(UserNotFoundException.class);
        mockMvc.perform(get("/api/v1/books/1"))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());

        verify(service).getBooks(user.getId());
        verify(service, times(1)).getBooks(user.getId());
    }

    @Test
    void givenBookToAddAndRealUserThenShouldReturnBooks() throws Exception {
        List<Book> newBookList = new ArrayList<>(List.of(book1,book2,book3));

        when(service.addBook(user.getId(), book3)).thenReturn(newBookList);
        mockMvc.perform(post("/api/v1/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(book3)))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print());

        verify(service).addBook(user.getId(), book3);
        verify(service, times(1)).addBook(user.getId(), book3);
    }

    @Test
    void givenExistingBookToAddThenShouldThrowError() throws Exception {
        when(service.addBook(user.getId(), book3)).thenThrow(new BookAlreadyExistsException());
        mockMvc.perform(post("/api/v1/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(book3)))
                .andExpect(status().isConflict())
                .andDo(MockMvcResultHandlers.print());

        verify(service).addBook(user.getId(), book3);
        verify(service, times(1)).addBook(user.getId(), book3);
    }

    @Test
    void givenBookToAddAndFakeUserThenShouldReturnBooks() throws Exception {
        List<Book> newBookList = new ArrayList<>(List.of(book1));

        when(service.addBook(user.getId(), book1)).thenReturn(newBookList);
        mockMvc.perform(post("/api/v1/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(book1)))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print());

        verify(service).addBook(user.getId(), book1);
        verify(service, times(1)).addBook(user.getId(), book1);
    }

    @Test
    void givenBookToDeleteAndRealUserThenShouldReturnBooks() throws Exception {
        List<Book> newBookList = new ArrayList<>(List.of(book2));

        when(service.deleteBook(user.getId(), book1.getISBN())).thenReturn(newBookList);
        mockMvc.perform(delete("/api/v1/books/1/isbn123"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

        verify(service).deleteBook(user.getId(), book1.getISBN());
        verify(service, times(1)).deleteBook(user.getId(), book1.getISBN());
    }

    @Test
    void givenBookToDeleteAndFakeUserThenShouldThrowError() throws Exception {
        when(service.deleteBook(user.getId(), book1.getISBN())).thenThrow(UserNotFoundException.class);
        mockMvc.perform(delete("/api/v1/books/1/isbn123"))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());

        verify(service).deleteBook(user.getId(), book1.getISBN());
        verify(service, times(1)).deleteBook(user.getId(), book1.getISBN());
    }

    @Test
    void givenFakeBookToDeleteAndRealUserThenShouldThrowError() throws Exception {
        when(service.deleteBook(user.getId(), book1.getISBN())).thenThrow(BookNotFoundException.class);
        mockMvc.perform(delete("/api/v1/books/1/isbn123"))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());

        verify(service).deleteBook(user.getId(), book1.getISBN());
        verify(service, times(1)).deleteBook(user.getId(), book1.getISBN());
    }

    @Test
    void givenRealUserWhenGettingAuthorsThenReturnList() throws Exception {
        List<String> newAuthorList = new ArrayList<>(List.of("rowling","tolkien"));

        when(service.getAuthors(user.getId())).thenReturn(newAuthorList);
        mockMvc.perform(get("/api/v1/books/1/authors"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

        verify(service).getAuthors(user.getId());
        verify(service, times(1)).getAuthors(user.getId());


    }

    @Test
    void givenFakeUserWhenGettingAuthorsThenShouldThrowError() throws Exception {
        when(service.getAuthors(user.getId())).thenThrow(UserNotFoundException.class);
        mockMvc.perform(get("/api/v1/books/1/authors"))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());

        verify(service).getAuthors(user.getId());
        verify(service, times(1)).getAuthors(user.getId());
    }

    @Test
    void givenRealUserWhenGettingFavoriteAuthorThenReturnString() throws Exception {
        when(service.getFavoriteAuthor(user.getId())).thenReturn("tolkien");
        mockMvc.perform(get("/api/v1/books/1/author"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

        verify(service).getFavoriteAuthor(user.getId());
        verify(service, times(1)).getFavoriteAuthor(user.getId());
    }

    @Test
    void givenFakeUserWhenGettingFavoriteAuthorThenShouldThrowError() throws Exception {
        when(service.getFavoriteAuthor(user.getId())).thenThrow(UserNotFoundException.class);
        mockMvc.perform(get("/api/v1/books/1/author"))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());

        verify(service).getFavoriteAuthor(user.getId());
        verify(service, times(1)).getFavoriteAuthor(user.getId());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
