package com.example.library_management_BE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "http://localhost:8080")
public class Controller {
    @Autowired
    private  BookRepository BookRepository;

    @PostMapping("/add")
    public BaseResponse addBook(@RequestBody Book book) {

        if (book == null) {
            return new BaseResponse(false, "Book data cannot be null", 400);
        }

        if (BookRepository.findByBookName(book.getBookName().trim()).isPresent()) {
            return new BaseResponse(false, "Book with name '" + book.getBookName() + "' already exists in the database", 409);
        }

        Book savedBook = BookRepository.save(book);
        System.out.println("Book added successfully: " + savedBook);
        return new BaseResponse(true, "Book '" + book.getBookName() + "' added successfully", 201);
    }
    @GetMapping
    public List<Book> getAllBooks() {
        return BookRepository.findAll();
    }
    
}
