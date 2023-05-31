package com.spring.blog.repository;

import com.spring.blog.entity.Certification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface CertificationRepository extends JpaRepository<Certification, String> {
    Optional<Certification> findByIdAndExpirationDateAfterAndExpired(String token, LocalDateTime now, boolean expired);
}
