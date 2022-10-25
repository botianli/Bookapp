package com.stackroute.favoriteservice.service;

import com.stackroute.favoriteservice.controller.UserController;
import com.stackroute.favoriteservice.domain.Book;
import com.stackroute.favoriteservice.domain.User;
import com.stackroute.favoriteservice.exception.BookAlreadyExistsException;
import com.stackroute.favoriteservice.exception.BookNotFoundException;
import com.stackroute.favoriteservice.exception.UserNotFoundException;
import com.stackroute.favoriteservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@CacheConfig(cacheNames = "user")
@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private UserRepository repo;

    @Autowired
    public UserServiceImpl(UserRepository repo) {
        this.repo = repo;
    }

    @Cacheable(value = "userCache", key="#userId")
    @Override
    public List<Book> getBooks(int userId) throws UserNotFoundException {
        User user = null;
        List<Book> books = null;
        Optional<User> optional = repo.findById(userId);

        if (optional.isPresent()) {
            user = optional.get();
            books = user.getBooks();
        }
            else {
                logger.error("Could not find user id {}", userId);
                throw new UserNotFoundException();
        }

        return books;
    }

    @Caching(evict = {
            @CacheEvict(value = "userCache", key = "#userId")
    })
    @Override
    public List<Book> addBook(int userId, Book book) throws UserNotFoundException, BookAlreadyExistsException {
        User user = null;
        List<Book> books = null;
        Optional<User> optional = repo.findById(userId);

        if (optional.isPresent()) {
            user = optional.get();
            // get books
            books = user.getBooks();
            if (books.contains(book)) {
                logger.error("Book already exists for user {}", userId);
                throw new BookAlreadyExistsException();
            }
        }
        else {
            logger.info("Creating new user with id {}", userId);
            // create new user
            user = new User();
            user.setId(userId);
            // instantiate book list
            books = new ArrayList<Book>();
        }
        // update user's books
        books.add(book);
        user.setBooks(books);
        repo.save(user);

        return books;
    }

    @Caching(evict = {
            @CacheEvict(value = "userCache", key = "#userId")
    })
    @Override
    public List<Book> deleteBook(int userId, String bookId) throws UserNotFoundException, BookNotFoundException {
        User user = null;
        List<Book> books = null;
        Optional<User> optional = repo.findById(userId);

        if (optional.isPresent()) {
            user = optional.get();
            // modify books
            books = user.getBooks();
            boolean success = books.removeIf(book -> book.getISBN().equals(bookId));
            if (success) { // book removed
                // if there is more than 1 book, update user's books
                if (books.size() > 0) {
                    user.setBooks(books);
                    repo.save(user);
                } else { // else delete user
                    logger.info("Removing empty user with id {}", userId);
                    repo.delete(user);
                }
            } else { // could not remove the book, something is wrong here
                logger.error("Could not find book: {}", bookId);
                throw new BookNotFoundException();
            }
        }
        else {
            logger.error("Could not find user id {}", userId);
            throw new UserNotFoundException();
        }

        return books;
    }

    @Cacheable(value = "authorsCache", key="#userId")
    @Override
    public List<String> getAuthors(int userId) throws UserNotFoundException {
        User user = null;
        List<Book> books = null;
        Optional<User> optional = repo.findById(userId);
        List<String> authors = new ArrayList<>();

        // get books
        if (optional.isPresent()) {
            user = optional.get();
            books = user.getBooks();
            // get authors as list, then add to hashset, then to arraylist
            authors = books.stream()
                            .map(Book::getAuthor) // list of authors
                            .distinct() // remove dupes
                            .sorted(String::compareTo) // sort alphabetically
                            .collect(Collectors.toList());
        }
        else { // user not found
            logger.error("Could not find user id {}", userId);
            throw new UserNotFoundException();
        }

        return authors;
    }

    @Cacheable(value = "authorCache", key="#userId")
    public String getFavoriteAuthor(int userId) throws UserNotFoundException {
        User user = null;
        List<Book> books = null;
        Optional<User> optional = repo.findById(userId);
        String author = null;

        // get books
        if (optional.isPresent()) {
            user = optional.get();
            books = user.getBooks();
            // get authors as list, then add to hashset, then to arraylist
            author = books.stream()
                    .map(Book::getAuthor)
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                    .entrySet()
                    .stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey).orElse(null);
        }
        else { // user not found
            logger.error("Could not find user id {}", userId);
            throw new UserNotFoundException();
        }

        return author;
    }
}
