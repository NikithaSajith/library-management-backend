package com.library.library.management.controller;

import com.library.library.management.entity.Author;
import com.library.library.management.repository.AuthorRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthorController {

    private final AuthorRepository authorRepository;

    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    // ADMIN: Add author
    @PostMapping
    public Author addAuthor(@RequestBody Author author) {
        author.setStatus(0);
        return authorRepository.save(author);
    }

    // Fetch only active authors
    @GetMapping
    public List<Author> getActiveAuthors() {
        return authorRepository.findByStatus(0);
    }

    // Soft delete author
    @PutMapping("/{id}/delete")
    public void deleteAuthor(@PathVariable Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));

        author.setStatus(1);
        authorRepository.save(author);
    }
}
