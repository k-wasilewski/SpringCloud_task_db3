package com.springcloud.task_db3.db.repositories;

import com.springcloud.task_db3.db.entities.BookMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookMessageRepository extends JpaRepository<BookMessage, Long> {
}