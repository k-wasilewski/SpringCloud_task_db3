package com.springcloud.task_db3.db.repositories;

import com.springcloud.task_db3.db.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAll();

    Optional<Book> findById(Long bookId);

    void deleteById(Long bookId);
}
