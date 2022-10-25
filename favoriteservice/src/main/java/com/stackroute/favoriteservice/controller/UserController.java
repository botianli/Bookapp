package com.stackroute.favoriteservice.controller;

import com.stackroute.favoriteservice.domain.Book;
import com.stackroute.favoriteservice.exception.BookAlreadyExistsException;
import com.stackroute.favoriteservice.exception.BookNotFoundException;
import com.stackroute.favoriteservice.exception.UserNotFoundException;
import com.stackroute.favoriteservice.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/v1/books")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @ApiOperation(value = "Get all books from user",
            notes = "Requires a token and the user's id",
            response = Book.class)
    @GetMapping("/{userId}")
    public ResponseEntity<List<Book>> getBooks(@PathVariable("userId") int userId) throws UserNotFoundException {
        logger.info("Called getBooks() for user id {}", userId);
        return new ResponseEntity<List<Book>>(service.getBooks(userId), HttpStatus.OK);
    }

    @ApiOperation(value = "Add favorite book to user",
            notes = "Requires a token, the user's id, and the book entity",
            response = Book.class)
    @PostMapping("/{userId}")
    public ResponseEntity<List<Book>> addBook(@PathVariable int userId, @RequestBody Book book) throws UserNotFoundException, BookAlreadyExistsException {
        logger.info("Called addBook() for user id {}", userId);
        List<Book> updatedBooks = service.addBook(userId, book);
        return new ResponseEntity<>(updatedBooks, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Delete favorite book from user",
            notes = "Requires a token, the user's id, and the book's ISBN.",
            response = Book.class)
    @DeleteMapping("/{userId}/{bookId}")
    public ResponseEntity<List<Book>> deleteBook(@PathVariable int userId, @PathVariable String bookId) throws UserNotFoundException, BookNotFoundException {
        logger.info("Called deleteBook() for user id {}", userId);
        List<Book> updatedBooks = service.deleteBook(userId, bookId);
        return new ResponseEntity<>(updatedBooks, HttpStatus.OK);
    }

    @ApiOperation(value = "Get all authors from user",
            notes = "Requires a token and the user's id",
            response = String.class)
    @GetMapping("/{userId}/authors")
    public ResponseEntity<List<String>> getAuthors(@PathVariable("userId") int userId) throws UserNotFoundException {
        logger.info("Called getAuthors() for user id {}", userId);
        return new ResponseEntity<List<String>>(service.getAuthors(userId), HttpStatus.OK);
    }

    @ApiOperation(value = "Get user's favorite author",
            notes = "Requires a token and the user's id",
            response = String.class)
    @GetMapping("/{userId}/author")
    public ResponseEntity<String> getFavoriteAuthor(@PathVariable("userId") int userId) throws UserNotFoundException {
        logger.info("Called getAuthor() for user id {}", userId);
        return new ResponseEntity<String>(service.getFavoriteAuthor(userId), HttpStatus.OK);
    }

}