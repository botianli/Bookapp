package com.stackroute.searchservice.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "Book entity for search request of the user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @ApiModelProperty(notes = "Book title")
    private String title;

    @ApiModelProperty(notes = "Author's full name")
    private String author;

    @ApiModelProperty(notes = "Book subject e.g. fantasy, sci-fi, history, etc.")
    private String subject;

    @ApiModelProperty(notes = "Book's published ISBN identifier")
    private String ISBN;

    @ApiModelProperty(notes = "Image URL for book cover")
    private String cover;

    public Book(String title, String author, String subject, String ISBN) {
        this.title=title;
        this.author=author;
        this.subject=subject;
        this.ISBN=ISBN;
    }
}

