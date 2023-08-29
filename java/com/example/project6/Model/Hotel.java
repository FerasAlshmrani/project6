package com.example.project6.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "name should be not empty")
    @Column(columnDefinition = "varchar(20) not null ")
    private String name;

    @Column(columnDefinition = "varchar(20) not null")
    private String phone;

    @NotEmpty(message = "name should be not empty")
    @Column(columnDefinition = "varchar(20) not null unique")
    private String apartment;

    @NotNull(message = "floor number should be not empty")
    private Integer floorNumber;

    @Positive
    @Column(columnDefinition = "int not null check( rooms > 0 )")
    private Integer rooms;

    @Positive(message = "price must be positive")
    @Column(columnDefinition = "int not null check( price >= 0 )")
    private Integer price;

    @Column(columnDefinition = "boolean default 0")
    private Boolean isBooked = false;

    @Column(columnDefinition = "varchar(20) default ''")
    private String byUsername = "";

    @Column(columnDefinition = "int default 0")
    private Integer discount = 0;


    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;


}
