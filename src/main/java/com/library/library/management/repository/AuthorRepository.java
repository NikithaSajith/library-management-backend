package com.library.library.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.library.management.entity.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findByStatus(Integer status);
}
