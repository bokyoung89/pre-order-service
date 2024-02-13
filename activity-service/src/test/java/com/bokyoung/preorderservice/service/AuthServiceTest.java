package com.bokyoung.preorderservice.service;

import com.bokyoung.activityService.exception.PreOrderServiceException;
import com.bokyoung.preorderservice.fixture.UserAccountFixture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@SpringBootTest
@AutoConfigureMockMvc
@Disabled
public class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @MockBean
    private UserAccountRepository userAccountRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    void 회원가입이_정상적으로_동작하는_경우() {
        //given
        String email = "username";
        String password = "password";
        String nickname = "신보경";
        String greeting = "안녕하세요!";
        String profile_image = "";
        Boolean emailVerified = false;

        //when
        //mocking
        when(userAccountRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(password)).thenReturn("encrypt_password");
        when(userAccountRepository.save(any())).thenReturn(UserAccountFixture.get(email, password));

        //then
        Assertions.assertDoesNotThrow(() -> authService.join(email, password, nickname, greeting, profile_image, emailVerified));
    }

    @Test
    void 회원가입시_email로_회원가입한_유저가_이미_있는경우() {
        //given
        String email = "sbk8689@gmail.com";
        String password = "qwer1234!";
        String nickname = "신보경";
        String greeting = "안녕하세요!";
        String profile_image = "";
        Boolean emailVerified = false;
        UserAccountEntity fixture = UserAccountFixture.get(email, password);

        //when
        //mocking
        when(userAccountRepository.findByEmail(email)).thenReturn(Optional.of(fixture));
        when(passwordEncoder.encode(password)).thenReturn("encrypt_password");
        when(userAccountRepository.save(any())).thenReturn(Optional.of(fixture));

        //then
        Assertions.assertThrows(PreOrderServiceException.class, () -> authService.join(email, password, nickname, greeting, profile_image, emailVerified));
    }

    @Test
    void 회원가입된_유저가_메일인증을_하지_않았을_경우_ () throws Exception {
        //given
        //when
        //then
    }

    @Disabled
    @Test
    void 회원가입시_입력값이_유효하지_않은경우() {
        //given
        String email = "sbk8689@gmail.com";
        String password = "qwer1234!";
        String nickname = "신보경";
        String greeting = "안녕하세요!";
        String profile_image = "";
        Boolean emailVerified = false;

        //when
        // TODO : mocking

        //then
        Assertions.assertThrows(PreOrderServiceException.class, () -> authService.join(email, password, nickname, greeting, profile_image, emailVerified));
    }


    @Disabled
    @Test
    void 회원가입시_메일인증번호가_유효하지_않은_경우() {
        //given
        // TODO : implement

        //when
        // TODO : mocking

        //then
        // TODO : implement
        //Assertions.assertDoesNotThrow(() -> ;
    }

    @Disabled
    @Test
    void 회원가입시_메일인증번호가_정상적인_경우() {
        //given
        // TODO : implement

        //when
        // TODO : mocking

        //then
        // TODO : implement
        //Assertions.assertDoesNotThrow(() -> ;
    }

    @Test
    void 로그인이_정상적으로_동작하는_경우() {
        //given
        String email = "username";
        String password = "password";
        UserAccountEntity fixture = UserAccountFixture.get(email, password);

        //when
        when(userAccountRepository.findByEmail(email)).thenReturn(Optional.of(fixture));
        when(passwordEncoder.matches(password, fixture.getPassword())).thenReturn(true);

        //then
        Assertions.assertDoesNotThrow(() -> authService.login(email, password));
    }

    @Test
    void 로그인시_email로_회원가입한_유저가_없는_경우() {
        //given
        String email = "sbk8689@gmail.com";
        String password = "qwer1234!";
        UserAccountEntity fixture = UserAccountFixture.get(email, password);

        //when
        //mocking
        when(userAccountRepository.findByEmail(email)).thenReturn(Optional.empty());

        //then
        Assertions.assertThrows(PreOrderServiceException.class, () -> authService.login(email, password));
    }

    @Test
    void 로그인시_password가_틀린_경우() {
        //given
        String email = "sbk8689@gmail.com";
        String password = "qwer1234!";
        String wrongPassword = "wrongPassword";
        UserAccountEntity fixture = UserAccountFixture.get(email, password);

        //when
        //mocking
        when(userAccountRepository.findByEmail(email)).thenReturn(Optional.of(fixture));

        //then
        Assertions.assertThrows(PreOrderServiceException.class, () -> authService.login(email, wrongPassword));
    }

}
