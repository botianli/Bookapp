package com.stackroute.favoriteservice.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ApiModel(description = "A user's favorite book")
public class Book {
    @Id
    @ApiModelProperty(notes = "Book's unique ISBN")
    private String ISBN;

    @ApiModelProperty(notes = "Book title")
    private String title;

    @ApiModelProperty(notes = "Author's full name")
    private String author;

    @ApiModelProperty(notes = "Book subject e.g. fantasy, sci-fi, history, etc.")
    private String subject;

    @ApiModelProperty(notes = "Image URL for book cover")
    private String cover;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return ISBN.equals(book.ISBN);
    }
}
