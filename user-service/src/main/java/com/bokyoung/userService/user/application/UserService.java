package com.bokyoung.userService.user.application;

import com.bokyoung.userService.global.exception.ErrorCode;
import com.bokyoung.userService.global.exception.PreOrderServiceException;
import com.bokyoung.userService.user.domain.model.UserAccount;
import com.bokyoung.userService.user.domain.entity.UserAccountEntity;
import com.bokyoung.userService.user.dao.UserAccountRepository;
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

    //TODO: modify
    @Transactional
    public Long update(Long id, String password, String nickName, String greeting, String profile_image) {
        UserAccountEntity userAccountEntity = userAccountRepository.findById(id).orElseThrow(() ->
                new PreOrderServiceException(ErrorCode.USER_NOT_FOUND, String.format("%s is not founded", id)));

        if (password != null) {
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

        UserAccount.fromEntity(userAccountRepository.save(userAccountEntity));
        return userAccountEntity.getId();
    }
}
