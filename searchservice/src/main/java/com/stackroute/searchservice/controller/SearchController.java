package com.stackroute.searchservice.controller;

import com.stackroute.searchservice.model.Book;
import com.stackroute.searchservice.service.SearchService;
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
@RequestMapping("api/v1")
public class SearchController {

    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);

    private SearchService service;

    @Autowired
    public SearchController(SearchService service) {
        this.service = service;
    }


    @ApiOperation(value = "Searching books by Title or Author name",
              response = Book.class)
    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(@RequestParam("searchOption") String searchOption, @RequestParam("searchInput") String searchInput){
        logger.info("Calling open library API to search books");
        return new ResponseEntity<List<Book>>(service.searchBooks(searchOption,searchInput), HttpStatus.OK);
    }

    @ApiOperation(value = "recommending books based on favorite Author",
            response = Book.class)
    @GetMapping("/recommend/{authorName}")
    public ResponseEntity<?> getRecommendations(@PathVariable("authorName") String authorName){
        logger.info("Getting recommendations for {} ",authorName);
        return new ResponseEntity<>(service.getRecommendations(authorName),HttpStatus.OK);
    }

}
