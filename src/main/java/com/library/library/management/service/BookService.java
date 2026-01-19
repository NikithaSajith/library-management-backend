package com.library.library.management.service;

import com.library.library.management.entity.Book;
import com.library.library.management.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    // Constructor Injection (BEST PRACTICE)
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Get all books
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Add a new book
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    // Borrow or return a book
    public Book borrowOrReturnBook(Long id, String username) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        // If book is borrowed by another user
        if (book.getBorrowedBy() != null &&
                !book.getBorrowedBy().equals(username)) {
            throw new IllegalStateException("Book already borrowed by another user");
        }

        // Toggle borrow / return
        book.setBorrowedBy(
                book.getBorrowedBy() == null ? username : null
        );

        return bookRepository.save(book);
    }

    // Delete a book
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
