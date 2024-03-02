package com.distribuida.appbooks.db;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "books")
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String isbn;

    @Column
    private String title;

    @Column
    private Double price;

    @Column(name="author_id")
    private Integer authorId;

}
