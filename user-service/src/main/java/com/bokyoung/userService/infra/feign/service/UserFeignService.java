package com.bokyoung.userService.infra.feign.service;

import com.bokyoung.userService.global.exception.ErrorCode;
import com.bokyoung.userService.global.exception.PreOrderServiceException;
import com.bokyoung.userService.domain.model.UserAccount;
import com.bokyoung.userService.dao.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFeignService {

    private final UserAccountRepository userAccountRepository;

    public UserAccount getUserAccount(Long userId) {
        return userAccountRepository.findById(userId).map(UserAccount::fromEntity).orElseThrow(() ->
                new PreOrderServiceException(ErrorCode.USER_NOT_FOUND, String.format("%s is not founded", userId)));
    }
}
