package com.stackroute.favoriteservice.service;

import com.stackroute.favoriteservice.domain.Book;
import com.stackroute.favoriteservice.domain.User;
import com.stackroute.favoriteservice.exception.BookNotFoundException;
import com.stackroute.favoriteservice.exception.UserNotFoundException;
import com.stackroute.favoriteservice.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository repo;

    @InjectMocks
    private UserServiceImpl service;

    private User user;
    private Book book1, book2, book3;
    private List<Book> books;
    private Optional optional;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        book1 = new Book("isbn123", "lotr1", "tolkien", "fantasy", "http://img.com/lotr1");
        book2 = new Book("isbn456", "lotr2", "tolkien", "fantasy", "http://img.com/lotr2");
        book3 = new Book("isbn789", "hp1", "rowling", "fantasy", "http://img.com/hp1");

        user = new User(1, new ArrayList<>(List.of(book1, book2)));
        optional = Optional.of(user);
    }

    @AfterEach
    public void tearDown() {
        user = null;
        book1 = book2 = book3 = null;
    }

    @Test
    void givenRealUserToRetrieveThenShouldReturnBooks() throws UserNotFoundException {
        List<Book> newBookList = new ArrayList<>(List.of(book1,book2));

        when(repo.findById(user.getId())).thenReturn(optional);
        List<Book> fetchedBooks = service.getBooks(user.getId());
        assertEquals(newBookList, fetchedBooks);

        verify(repo, times(1)).findById(user.getId());
    }

    @Test
    void givenFakeUserToRetrieveThenShouldThrowError() throws UserNotFoundException {
        when(repo.findById(user.getId())).thenThrow(UserNotFoundException.class);
        Assertions.assertThrows(UserNotFoundException.class, () ->
                service.getBooks(user.getId()));

        verify(repo, times(1)).findById(user.getId());
    }

    @Test
    void givenBookToAddAndRealUserThenShouldReturnBooks() throws UserNotFoundException {
        // modify local copies for tests
        List<Book> newBookList = new ArrayList<>(List.of(book1,book2,book3));

        when(repo.findById(user.getId())).thenReturn(optional);
        List<Book> moreBooks = service.addBook(user.getId(), book3);
        assertEquals(newBookList, moreBooks);

        verify(repo, times(1)).findById(user.getId());
        verify(repo, times(1)).save(user);
    }

    @Test
    void givenBookToAddAndFakeUserThenShouldReturnBooks() throws UserNotFoundException {
        // it will create a new user
        List<Book> newBookList = new ArrayList<>();
        newBookList.add(book1);

        when(repo.findById(user.getId())).thenReturn(Optional.empty());
        List<Book> moreBooks = service.addBook(user.getId(), book1);
        assertEquals(newBookList, moreBooks);

        verify(repo, times(1)).findById(user.getId());
        verify(repo, times(1)).save(any());
    }

    @Test
    void givenBookToDeleteAndRealUserThenShouldReturnBooks() throws UserNotFoundException, BookNotFoundException {
        List<Book> newBookList = new ArrayList<>(List.of(book2));

        when(repo.findById(user.getId())).thenReturn(optional);
        List<Book> lessBooks = service.deleteBook(user.getId(), book1.getISBN());
        assertEquals(newBookList, lessBooks);

        verify(repo, times(1)).findById(user.getId());
        verify(repo, times(1)).save(user);
    }

    @Test
    void givenBookToDeleteAndUserWithOneBookThenShouldDeleteUser() throws UserNotFoundException {
        List<Book> newBookList = new ArrayList<>();
        user.setBooks(new ArrayList<>(List.of(book1)));

        when(repo.findById(user.getId())).thenReturn(optional);
        List<Book> lessBooks = service.deleteBook(user.getId(), book1.getISBN());
        assertEquals(newBookList, lessBooks);

        verify(repo, times(1)).findById(user.getId());
        verify(repo, times(1)).delete(user);
    }

    @Test
    void givenBookToDeleteAndFakeUserThenShouldThrowError() throws UserNotFoundException, BookNotFoundException {
        when(repo.findById(user.getId())).thenThrow(UserNotFoundException.class);
        Assertions.assertThrows(UserNotFoundException.class, () ->
                service.deleteBook(user.getId(), book1.getISBN()));

        verify(repo, times(1)).findById(user.getId());
    }

    @Test
    void givenFakeBookToDeleteAndRealUserThenThrowError() throws UserNotFoundException, BookNotFoundException {
        when(repo.findById(user.getId())).thenThrow(BookNotFoundException.class);
        Assertions.assertThrows(BookNotFoundException.class, () ->
                service.deleteBook(user.getId(), book1.getISBN()));

        verify(repo, times(1)).findById(user.getId());
    }

    @Test
    void givenRealUserWhenGettingAuthorsThenReturnList() throws UserNotFoundException {
        user.setBooks(List.of(book1, book2, book3));
        List<String> newAuthorList = new ArrayList<>(List.of("rowling","tolkien"));

        when(repo.findById(user.getId())).thenReturn(optional);
        List<String> fetchedAuthors = service.getAuthors(user.getId());
        assertEquals(newAuthorList, fetchedAuthors);

        verify(repo, times(1)).findById(user.getId());
    }

    @Test
    void givenFakeUserWhenGettingAuthorsThenShouldThrowError() throws UserNotFoundException {
        when(repo.findById(user.getId())).thenThrow(UserNotFoundException.class);
        Assertions.assertThrows(UserNotFoundException.class, () ->
                service.getAuthors(user.getId()));

        verify(repo, times(1)).findById(user.getId());
    }

    @Test
    void givenRealUserWhenGettingFavoriteAuthorThenReturnString() throws UserNotFoundException {
        user.setBooks(List.of(book1, book2, book3));

        when(repo.findById(user.getId())).thenReturn(optional);
        String fetchedAuthors = service.getFavoriteAuthor(user.getId());
        assertEquals("tolkien", fetchedAuthors);

        verify(repo, times(1)).findById(user.getId());
    }

    @Test
    void givenFakeUserWhenGettingFavoriteAuthorThenShouldThrowError() throws UserNotFoundException {
        when(repo.findById(user.getId())).thenThrow(UserNotFoundException.class);
        Assertions.assertThrows(UserNotFoundException.class, () ->
                service.getFavoriteAuthor(user.getId()));

        verify(repo, times(1)).findById(user.getId());
    }
}
