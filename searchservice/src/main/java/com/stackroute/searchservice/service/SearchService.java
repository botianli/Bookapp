package com.stackroute.searchservice.service;

import com.stackroute.searchservice.model.Book;

import java.util.List;

public interface SearchService {
    List<Book> searchBooks(String searchOption, String searchInput) throws RuntimeException;
    List<Book> getRecommendations(String authorName);
}
