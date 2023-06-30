package com.spring.blog.repository;

import com.spring.blog.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query(
            value = "SELECT n FROM Notification n WHERE (n.title LIKE %:title% OR n.content LIKE %:content%) AND n.isEnable = 0",
            countQuery = "SELECT COUNT(n.id) FROM Notification n WHERE (n.title LIKE %:title% OR n.content LIKE %:content%) AND n.isEnable = 0"
    )
    Page<Notification> findAllSearch(String title, String content, Pageable pageable);
}
