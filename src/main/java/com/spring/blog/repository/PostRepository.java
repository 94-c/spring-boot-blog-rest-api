package com.spring.blog.repository;

import com.spring.blog.entity.Post;
import com.spring.blog.entity.common.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findById(Long postId);
    Optional<Post> findByIdAndUserId(Long postId, Long userId);

}
