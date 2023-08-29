package com.example.project6.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "name should not be empty")
    @Column(columnDefinition = "varchar(20) not null")
    private String name;

    @NotEmpty(message = "phone should be not empty")
    @Column(columnDefinition = "varchar(20) not null")
    private String phone;

    @Email
    @NotEmpty(message = "email should be not empty")
    @Column(columnDefinition = "varchar(20) not null unique")
    private String email;

    @NotNull(message = "balance should be not empty")
    @PositiveOrZero(message = "balance must be positive")
    @Column(columnDefinition = "int default 0 ")
    private Integer balance;


    @Column(columnDefinition = "boolean default 0")
    private Boolean isBooked=false;

    // if he booked by which room he got?
    @Column(columnDefinition = "varchar(20) default ''")
    private String whichApartment="";

    @PositiveOrZero(message = "duration must be positive")
    @Column(columnDefinition = "int default 0")
    private Integer duration = 0;


    @OneToOne
    @MapsId
    @JsonIgnore
    private User user;



}
