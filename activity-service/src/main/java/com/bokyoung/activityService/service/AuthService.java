package com.bokyoung.activityService.service;

import com.bokyoung.activityService.exception.ErrorCode;
import com.bokyoung.activityService.exception.PreOrderServiceException;
import com.bokyoung.activityService.provider.MailProvider;
import com.bokyoung.activityService.model.UserAccount;
import com.bokyoung.activityService.model.entity.UserAccountEntity;
import com.bokyoung.activityService.repository.UserAccountRepository;
import com.bokyoung.activityService.util.JwtTokenUtils;
import com.bokyoung.activityService.util.MailCertificationNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserAccountRepository userAccountRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final MailProvider mailProvider;

    private final FileServiceImpl fileService;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.expired-time-ms}")
    private Long expiredTimeMs;

    //TODO : implement file upload
    @Transactional
    public UserAccount join(String email, String password, String nickname, String greeting, String profile_image, Boolean emailVerified) {
        // 회원가입하려는 email로 회원가입된 user가 있는지
        userAccountRepository.findByEmail(email).ifPresent(it -> {
            throw new PreOrderServiceException(ErrorCode.DUPLICATED_EMAIL, String.format("%s is duplicated", email));
        });

        // 회원가입 시 입력값이 유효한지
        Pattern passPattern1 = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$"); //8자 영문+특문+숫자
        Matcher passMatcher = passPattern1.matcher(password);

        if (!passMatcher.find()) {
            throw new PreOrderServiceException(ErrorCode.INVALID_INPUT_PASSWORD, String.format("%s is invalid", password));
        }

        // 회원가입 진행 = user를 등록
        UserAccountEntity userAccountEntity = userAccountRepository.save(UserAccountEntity.of(email, passwordEncoder.encode(password), nickname, greeting, profile_image, false));
        sendingCertificationMail(userAccountEntity);

        return UserAccount.fromEntity(userAccountEntity);
    }

    public void sendingCertificationMail(UserAccountEntity userAccountEntity) {
        //이미 이메일 검증 토큰이 있는 경우 예외 반환
//        if(userAccount.getEmailCheckToken() != null) {
//             throw new PreOrderServiceException(ErrorCode.DUPLICATE_EMAIL_CHECK_TOKEN, "Email check token already exists");
//        }
//
//        userAccount.generateEmailCheckToken(); //이메일 검증 토큰 만들기

        String certificationNumber = MailCertificationNumber.getCertificationNumber();

        boolean isSuccessed = mailProvider.sendCertificationMail(userAccountEntity.getEmail(), certificationNumber);
        if (!isSuccessed) {
            throw new PreOrderServiceException(ErrorCode.INVALID_EMAIL_TOKEN, "mail send fail");
        }

        //인증 성공하면
        userAccountEntity.setCertificationNumber(certificationNumber);
        userAccountRepository.save(userAccountEntity);
    }

    public boolean checkCertification(String email, String certificationNumber) {
        //가입된 유저가 아닌 경우 체크
        UserAccountEntity userAccountEntity = userAccountRepository.findByEmail(email).orElseThrow(() ->
                new PreOrderServiceException(ErrorCode.USER_NOT_FOUND, String.format("%s is not founded", email)));

        //유저의 메일주소와 메일인증번호 일치 여부 체크
        boolean isMatched = userAccountEntity.getEmail().equals((email)) && userAccountEntity.getCertificationNumber().equals(certificationNumber);
        if (!isMatched) {
            throw new PreOrderServiceException(ErrorCode.INVALID_CERTIFICATION, "certification number is invaild");
        }
        userAccountEntity.setEmailVerified(true);
        userAccountRepository.save(userAccountEntity);

        return userAccountEntity.isEmailVerified();

    }

    // TODO : implement
    public String login(String email, String password) {
        // 회원가입 여부 체크
        UserAccountEntity userAccountEntity = userAccountRepository.findByEmail(email).orElseThrow(() -> new PreOrderServiceException(ErrorCode.USER_NOT_FOUND, String.format("%s is not founded", email)));

        // 비밀번호 체크
        if (!passwordEncoder.matches(password, userAccountEntity.getPassword())) {
            throw new PreOrderServiceException(ErrorCode.INVALID_PASSWORD);
        }

        // 토큰 생성
        String token = JwtTokenUtils.generateToken(email, secretKey, expiredTimeMs);

        return token;
    }
}
