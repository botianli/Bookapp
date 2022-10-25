package com.stackroute.favoriteservice.service;

import com.stackroute.favoriteservice.domain.Book;
import com.stackroute.favoriteservice.exception.BookAlreadyExistsException;
import com.stackroute.favoriteservice.exception.BookNotFoundException;
import com.stackroute.favoriteservice.exception.UserNotFoundException;

import java.util.List;

public interface UserService {

    List<Book> getBooks(int userId) throws UserNotFoundException;

    List<Book> addBook(int userId, Book book) throws UserNotFoundException, BookAlreadyExistsException;

    List<Book> deleteBook(int userId, String bookId) throws UserNotFoundException, BookNotFoundException;

    List<String> getAuthors(int userId) throws UserNotFoundException;

    String getFavoriteAuthor(int userId) throws UserNotFoundException;
}