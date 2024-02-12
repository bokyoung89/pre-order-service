package com.bokyoung.newsFeedService.service;

import com.bokyoung.newsFeedService.exception.ErrorCode;
import com.bokyoung.newsFeedService.exception.PreOrderServiceException;
import com.bokyoung.newsFeedService.repository.NewsFeedRepository;
import com.bokyoung.newsFeedService.repository.UserAccountRepository;
import com.bokyoung.newsFeedService.model.UserAccount;
import com.bokyoung.newsFeedService.model.entity.UserAccountEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserAccountRepository userAccountRepository;
    private final NewsFeedRepository newsFeedRepository;

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

        userAccountEntity = userAccountRepository.save(UserAccountEntity.of(password, nickName, greeting, profile_image));
        return userAccountEntity.getId();
    }
}
