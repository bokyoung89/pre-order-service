package com.bokyoung.preorderservice.service;

import com.bokyoung.preorderservice.exception.ErrorCode;
import com.bokyoung.preorderservice.exception.PreOrderServiceException;
import com.bokyoung.preorderservice.model.UserAccount;
import com.bokyoung.preorderservice.model.entity.UserAccountEntity;
import com.bokyoung.preorderservice.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserAccountRepository userAccountRepository;

    public UserAccount loadByUserByEmail(String email) {
        return userAccountRepository.findByEmail(email).map(UserAccount::fromEntity).orElseThrow(() ->
                new PreOrderServiceException(ErrorCode.USER_NOT_FOUND, String.format("%s is not founded", email)));
    }

    @Transactional
    public Long update(Long id, String password, String nickName, String greeting, String profile_image) {
        UserAccountEntity userAccountEntity = userAccountRepository.findById(id).orElseThrow(() ->
                new PreOrderServiceException(ErrorCode.USER_NOT_FOUND, String.format("%s is not founded", id)));

        if(password != null) {
            userAccountEntity.setPassword(password);
        }
        if (nickName != null) {
            userAccountEntity.setNickname(nickName);
        }
        if (greeting != null) {
            userAccountEntity.setGreeting(greeting);
        }
        if (profile_image != null) {
            userAccountEntity.setProfile_image(profile_image);
        }

        userAccountEntity = userAccountRepository.save(UserAccountEntity.of(password, nickName, greeting, profile_image));
        return userAccountEntity.getId();
    }
}