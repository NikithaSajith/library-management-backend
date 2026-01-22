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
    return bookRepository.findByStatus(0);
}


    // Add a new book
  public Book addBook(Book book) {
    book.setStatus(0);      // ✅ VERY IMPORTANT
    book.setBorrowedBy(null);
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
    // Update book details
    public Book updateBook(Long id, Book updatedBook) {
    Book existing = bookRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Book not found"));

    existing.setName(updatedBook.getName());
    existing.setAuthor(updatedBook.getAuthor());
    existing.setCategory(updatedBook.getCategory());
    existing.setPublisher(updatedBook.getPublisher());

    return bookRepository.save(existing);
}


    // Delete a book
    public void deleteBook(Long id) {
    Book book = bookRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Book not found"));

    book.setStatus(1); // mark as deleted
    bookRepository.save(book);
}

}
