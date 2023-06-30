package com.spring.blog.service.impl;

import com.spring.blog.entity.Certification;
import com.spring.blog.entity.common.LocalDate;
import com.spring.blog.repository.CertificationRepository;
import com.spring.blog.service.CertificationService;
import com.spring.blog.utils.EmailSenderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CertificationServiceImpl implements CertificationService {

    private static final long EMAIL_TOKEN_EXPIRATION_TIME_VALUE = 5L;
    private final CertificationRepository certificationRepository;
    private final EmailSenderUtil emailSenderUtil;


    @Override
    public Certification createEmailToken(Long userId) {
        return Certification.builder()
                .expirationDate(LocalDateTime.now().plusMinutes(EMAIL_TOKEN_EXPIRATION_TIME_VALUE))
                .expired(false)
                .userId(userId)
                .date(LocalDate.builder()
                        .createdAt(LocalDateTime.now())
                        .build())
                .build();
    }

    @Override
    public void createEmailToken(Long userId, String receiverEmail) {

        //이메일 토큰 저장
        Certification certification = createEmailToken(userId);
        certification.setType("회원가입 이메일 인증");
        certificationRepository.save(certification);

        //이메일 전송
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(receiverEmail);
        mailMessage.setSubject("회원가입 이메일 인증");
        mailMessage.setText("http://localhost:8080/confirm-email?token=" + certification.getId());

        emailSenderUtil.sendEmail(mailMessage);
    }

    @Override
    public void createPasswordToken(Long userId, String receiverEmail) {

        //이메일 토큰 저장
        Certification certification = createEmailToken(userId);
        certification.setType("비밀번호 찾기");
        certificationRepository.save(certification);

        //이메일 전송
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(receiverEmail);
        mailMessage.setSubject("비밀번호 찾기 이메일 인증");
        mailMessage.setText("http://localhost:8080/find-password?token=" + certification.getId());

        emailSenderUtil.sendEmail(mailMessage);
    }


    public Certification findByIdAndExpirationDateAfterAndExpired(String token) {
        Optional<Certification> emailToke = certificationRepository.findByIdAndExpirationDateAfterAndExpired(token, LocalDateTime.now(), false);

        return emailToke.orElseThrow(() -> new RuntimeException("인증 토큰이 존재하지 않습니다."));
    }


}
