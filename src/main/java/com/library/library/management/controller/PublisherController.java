package com.library.library.management.controller;

import com.library.library.management.entity.Publisher;
import com.library.library.management.repository.PublisherRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publishers")
@CrossOrigin(origins = "http://localhost:3000")
public class PublisherController {

    private final PublisherRepository publisherRepository;

    public PublisherController(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    // ADMIN: Add publisher
    @PostMapping
    public Publisher addPublisher(@RequestBody Publisher publisher) {
        publisher.setStatus(0);
        return publisherRepository.save(publisher);
    }

    // Fetch active publishers
    @GetMapping
    public List<Publisher> getActivePublishers() {
        return publisherRepository.findByStatus(0);
    }

    // Soft delete publisher
    @PutMapping("/{id}/delete")
    public void deletePublisher(@PathVariable Long id) {
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publisher not found"));

        publisher.setStatus(1);
        publisherRepository.save(publisher);
    }
}
