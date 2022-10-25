package com.stackroute.userservice.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
@Table(name = "user")
@ApiModel(description = "Details about the User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "The user's name")
    private int userId;

    @ApiModelProperty(notes = "The user's firstName")
    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @ApiModelProperty(notes = "The user's lastName")
    @Column(unique = true,nullable = false)
    private String email;

    @ApiModelProperty(notes = "The user's password")
    @Column(nullable = false)
    private String password;

    @Column(nullable = true)
    @ApiModelProperty(notes = "The user's imageUrl")
    private String imageUrl;

    public User(String email, String password){
        this.email=email;
        this.password=password;
    }
}
