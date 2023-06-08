package com.spring.blog.repository;

import com.spring.blog.entity.Like;
import com.spring.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByPostAndUserId(Post post, Long userId);

}
