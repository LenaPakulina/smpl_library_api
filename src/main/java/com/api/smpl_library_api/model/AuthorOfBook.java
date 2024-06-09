package com.api.smpl_library_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "authors_of_books")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorOfBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "book_id")
    private int bookId;

    @Column(name = "author_id")
    private int authorId;
}
