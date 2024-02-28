package com.bokyoung.userService.controller;

import com.bokyoung.userService.user.controller.AuthController;
import com.bokyoung.userService.user.dto.request.UserLoginRequest;
import com.bokyoung.userService.global.exception.ErrorCode;
import com.bokyoung.userService.user.domain.model.UserAccount;
import com.bokyoung.userService.user.dto.request.UserJoinRequest;
import com.bokyoung.userService.global.exception.PreOrderServiceException;
import com.bokyoung.userService.user.application.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    @Test
    @WithAnonymousUser
    public void 회원가입_성공() throws Exception {
        //given
        String email = "test@gmail.com";
        String password = "password1234!";
        String nickname = "홍길동";
        String greeting = "안녕하세요!";
        String profile_image = "";
        Boolean emailVerified = false;

        //when
        when(authService.join(email, password, nickname, greeting, profile_image, emailVerified)).thenReturn(mock(UserAccount.class));

        //then
        mockMvc.perform(post("/user-service/auth/join")
                .contentType(MediaType.APPLICATION_JSON)
                // TODO : add reqeust body
                .content(objectMapper.writeValueAsBytes(new UserJoinRequest(email, password, nickname, greeting, profile_image, emailVerified)))
        ).andDo(print())
                .andExpect(status().isOk());
    }

    @WithAnonymousUser
    @Test
    public void 회원가입시_이미_회원가입된_email로_회원가입을_시도하면_에러반환 () throws Exception {
        //given
        String email = "username";
        String password = "password";
        String nickname = "신보경";
        String greeting = "안녕하세요!";
        String profile_image = "";
        Boolean emailVerified = false;

        //when
        // TODO : develop
        when(authService.join(email, password, nickname, greeting, profile_image, emailVerified)).thenThrow(new PreOrderServiceException(ErrorCode.DUPLICATED_EMAIL));

        //then
        mockMvc.perform(post("/user-service/auth/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        // TODO : add reqeust body
                        .content(objectMapper.writeValueAsBytes(new UserJoinRequest(email, password, nickname, greeting, profile_image, emailVerified)))
        ).andDo(print())
                .andExpect(status().isConflict());
    }

    @WithAnonymousUser
    @Test
    public void 회원가입시_입력값이_유효하지_않으면_에러반환 () throws Exception {
        //given
        String email = "username";
        String password = "password";
        String nickname = "신보경";
        String greeting = "안녕하세요!";
        String profile_image = "";
        Boolean emailVerified = false;

        //when
        // TODO : develop
        when(authService.join(email, password, nickname, greeting, profile_image, emailVerified)).thenThrow(new PreOrderServiceException(ErrorCode.DUPLICATED_EMAIL));

        //then
        mockMvc.perform(post("/user-service/auth/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        // TODO : add reqeust body
                        .content(objectMapper.writeValueAsBytes(new UserJoinRequest(email, password, nickname, greeting, profile_image, emailVerified)))
                ).andDo(print())
                .andExpect(status().isConflict());
    }

    @WithAnonymousUser
    @Test
    public void 회원가입시_메일인증번호가_유효하지_않으면_에러반환 () throws Exception {
        //given
        String email = "username";
        String password = "password";
        String nickname = "신보경";
        String greeting = "안녕하세요!";
        String profile_image = "";
        Boolean emailVerified = false;

        //when
        // TODO : develop
        when(authService.join(email, password, nickname, greeting, profile_image, emailVerified)).thenThrow(new PreOrderServiceException(ErrorCode.DUPLICATED_EMAIL));

        //then
        mockMvc.perform(post("/user-service/auth/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        // TODO : add reqeust body
                        .content(objectMapper.writeValueAsBytes(new UserJoinRequest(email, password, nickname, greeting, profile_image, emailVerified)))
                ).andDo(print())
                .andExpect(status().isConflict());
    }

    @WithAnonymousUser
    @Test
    public void 회원가입시_메일인증_완료 () throws Exception {
        //given
        String email = "username";
        String password = "password";
        String nickname = "신보경";
        String greeting = "안녕하세요!";
        String profile_image = "";
        Boolean emailVerified = false;

        //when
        // TODO : develop
        when(authService.join(email, password, nickname, greeting, profile_image, emailVerified)).thenThrow(new PreOrderServiceException(ErrorCode.DUPLICATED_EMAIL));

        //then
        mockMvc.perform(post("/user-service/auth/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        // TODO : add reqeust body
                        .content(objectMapper.writeValueAsBytes(new UserJoinRequest(email, password, nickname, greeting, profile_image, emailVerified)))
                ).andDo(print())
                .andExpect(status().isConflict());
    }

    @WithAnonymousUser
    @Test
    public void 로그인_성공() throws Exception {
        //given
        String email = "username";
        String password = "password";

        //when
        when(authService.login(email, password)).thenReturn("test_token");

        //then
        mockMvc.perform(post("/user-service/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        // TODO : add reqeust body
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(email, password)))
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @WithAnonymousUser
    @Test
    public void 로그인_시_회원가입이_안된_email을_입력할경우_에러반환() throws Exception {
        //given
        String email = "username";
        String password = "password";

        //when
        when(authService.login(email, password)).thenThrow(new PreOrderServiceException(ErrorCode.USER_NOT_FOUND));

        //then
        mockMvc.perform(post("/user-service/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        // TODO : add reqeust body
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(email, password)))
                ).andDo(print())
                .andExpect(status().isNotFound());
    }

    @WithAnonymousUser
    @Test
    public void 로그인_시_틀린_password를_입력할경우_에러반환() throws Exception {
        //given
        String email = "username";
        String password = "password";

        //when
        when(authService.login(email, password)).thenThrow(new PreOrderServiceException(ErrorCode.INVALID_PASSWORD));

        //then
        mockMvc.perform(post("/user-service/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        // TODO : add reqeust body
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(email, password)))
                ).andDo(print())
                .andExpect(status().isUnauthorized());
    }
}

