package com.spring.blog.repository;

import com.spring.blog.entity.User;
import com.spring.blog.exception.ResourceNotFoundException;
import com.spring.blog.security.UserPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    User findUserByStatus(Integer status);
    Boolean existsByEmail(@NotBlank String email);

    Optional<User> findByNameOrEmail(String name, String email);

    @Query(
            value = "SELECT u FROM User u WHERE u.email like %:email% or u.name LIKE %:name%",
            countQuery = "SELECT COUNT(u.id) FROM User u WHERE u.email like %:email% or u.name LIKE %:name%"
    )
    Page<User> findAllSearch(String email, String name, Pageable pageable);


}
