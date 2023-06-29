package com.spring.blog.repository;

import com.spring.blog.entity.Post;
import com.spring.blog.entity.common.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {
    Optional<Post> findById(Long postId);

    @Query(
            value = "SELECT p FROM Post p WHERE (p.title LIKE %:title% OR p.content LIKE %:content%) AND p.isEnable = 0",
            countQuery = "SELECT COUNT(p.id) FROM Post p WHERE (p.title LIKE %:title% OR p.content LIKE %:content%) AND p.isEnable = 0"
    )
    Page<Post> findAllSearch(String title, String content, Pageable pageable);

}
