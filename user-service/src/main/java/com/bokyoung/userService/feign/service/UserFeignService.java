package com.bokyoung.userService.feign.service;

import com.bokyoung.userService.exception.ErrorCode;
import com.bokyoung.userService.exception.PreOrderServiceException;
import com.bokyoung.userService.model.UserAccount;
import com.bokyoung.userService.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFeignService {

    private final UserAccountRepository userAccountRepository;

    public UserAccount getUserAccount(String email) {
        return userAccountRepository.findByEmail(email).map(UserAccount::fromEntity).orElseThrow(() ->
                new PreOrderServiceException(ErrorCode.USER_NOT_FOUND, String.format("%s is not founded", email)));
    }
}
