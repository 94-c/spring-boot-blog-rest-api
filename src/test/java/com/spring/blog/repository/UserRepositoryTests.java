package com.spring.blog.repository;

import com.spring.blog.entity.Role;
import com.spring.blog.entity.User;
import com.spring.blog.entity.common.LocalDate;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @DisplayName("JUnit Test create User")
    @Test
    public void givenUserObject_whenSave_thenReturnSavedUser() {

        // given - precondition or setup
        User user = User.builder()
                .email("test@test.com")
                .password("1234")
                .name("테스트 계정")
                .status(0)
                .date(LocalDate.builder()
                        .createdAt(LocalDateTime.now())
                        .build())
                .build();

        // when - action or the behavior that we are going test
        User createUser = userRepository.save(user);

        // then - verify the output
        Assertions.assertThat(createUser).isNotNull();
        Assertions.assertThat(createUser.getId()).isGreaterThan(0);
    }


}
