package com.library.library.management.controller;

import com.library.library.management.entity.Book;
import com.library.library.management.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @PostMapping
    public Book addBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @PutMapping("/{id}/borrow")
    public ResponseEntity<?> borrowBook(
            @PathVariable Long id,
            @RequestParam String username) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        // Book already borrowed by someone else
        if (book.getBorrowedBy() != null &&
                !book.getBorrowedBy().equals(username)) {
            return ResponseEntity
                    .status(409)
                    .body("Book already borrowed by another user");
        }

        // Toggle borrow / return
        book.setBorrowedBy(
                book.getBorrowedBy() == null ? username : null);

        Book saved = bookRepository.save(book);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookRepository.deleteById(id);
        return "Book deleted with id " + id;
    }

}
