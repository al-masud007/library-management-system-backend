package com.example.library_management_BE;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class BorrowRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;  // in future link with User table
    private Long bookId;
    private int quantityBorrowed;

}
