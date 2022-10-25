package com.stackroute.favoriteservice.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ApiModel(description = "User's collection of favorite books")
public class User {
    @Id
    @ApiModelProperty(notes = "Unique identifier for user")
    private int id;

    @ApiModelProperty(notes = "List of favorite books")
    private List<Book> books;
}
