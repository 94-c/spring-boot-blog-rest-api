package com.spring.blog.repository;

import com.spring.blog.entity.User;
import com.spring.blog.exception.ResourceNotFoundException;
import com.spring.blog.security.UserPrincipal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(@NotBlank String name);
    Optional<User> findByEmail(@NotBlank String email);

    Boolean existsByName(@NotBlank String name);

    Boolean existsByEmail(@NotBlank String email);

    Optional<User> findByNameOrEmail(String name, String email);

    default User getUser(UserPrincipal currentUser) {
        return getUserByName(currentUser.getUsername());
    }

    default User getUserByName(String name) {
        return findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("User", "name", name));
    }
}
