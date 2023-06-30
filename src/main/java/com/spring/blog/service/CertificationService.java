package com.spring.blog.service;

import com.spring.blog.entity.Certification;

public interface CertificationService {

    Certification createEmailToken(Long userId);

    void createEmailToken(Long userId, String receiverEmail);

    void createPasswordToken(Long userId, String receiverEmail);

    Certification findByIdAndExpirationDateAfterAndExpired(String token);

}
