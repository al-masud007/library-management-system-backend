package com.example.library_management_BE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "http://localhost:8080")
public class BookActionController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BorrowRecordRepository borrowRecordRepository;

    // For now, we assume userId = 1 (since login not yet built)
    private final Long FIXED_USER_ID = 1L;

    // Borrow
    @PostMapping("/{id}/borrow")
    public BaseResponse borrowBook(@PathVariable Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isEmpty()) {
            return new BaseResponse(false, "Book not found", 404);
        }

        Book book = optionalBook.get();

        if (book.getQuantity() <= 0) {
            return new BaseResponse(false, "No copies available to borrow", 400);
        }

        // track borrow record
        BorrowRecord record = borrowRecordRepository
                .findByUserIdAndBookId(FIXED_USER_ID, id)
                .orElseGet(() -> {
                    BorrowRecord newRecord = new BorrowRecord();
                    newRecord.setUserId(FIXED_USER_ID);
                    newRecord.setBookId(id);
                    newRecord.setQuantityBorrowed(0);
                    return newRecord;
                });

        // Check if already borrowed
//        if (record.getQuantityBorrowed() >= 1) {
//            return new BaseResponse(false, "You have already borrowed this book", 400);
//        }

        // reduce stock
        book.setQuantity(book.getQuantity() - 1);
        bookRepository.save(book);

        record.setQuantityBorrowed(record.getQuantityBorrowed() + 1);
        borrowRecordRepository.save(record);

        return new BaseResponse(true, "Book borrowed successfully", 200);
    }

    // Return
    @PostMapping("/{id}/return")
    public BaseResponse returnBook(@PathVariable Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isEmpty()) {
            return new BaseResponse(false, "Book not found", 404);
        }

        BorrowRecord record = borrowRecordRepository.findByUserIdAndBookId(FIXED_USER_ID, id)
                .orElse(null);

        if (record == null || record.getQuantityBorrowed() <= 0) {
            return new BaseResponse(false, "You haven’t borrowed this book", 400);
        }

        // increase stock
        Book book = optionalBook.get();
        book.setQuantity(book.getQuantity() + 1);
        bookRepository.save(book);

        // decrease borrow record
        record.setQuantityBorrowed(record.getQuantityBorrowed() - 1);
        borrowRecordRepository.save(record);

        return new BaseResponse(true, "Book returned successfully", 200);
    }
}
