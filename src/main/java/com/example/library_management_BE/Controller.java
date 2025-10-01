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

        if (book == null || book.getBookName().isEmpty() || book.getBookAuthor().isEmpty() || book.getQuantity() <= 0 || BookRepository.findByBookName(book.getBookName()).isPresent() ) {

            System.out.println("Book details: " + book);
            return new BaseResponse(false, "invalid Book data", 400);


        } else {
            Book savebook = BookRepository.save(book);
            System.out.println("Book details: " + book);
            return new BaseResponse(true, "Book added successfully", 200);
        }
        

    }
    @GetMapping
    public List<Book> getAllBooks() {
        return BookRepository.findAll();
    }
    
}






