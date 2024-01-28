package com.bokyoung.preorderservice.service;

import com.bokyoung.preorderservice.exception.ErrorCode;
import com.bokyoung.preorderservice.exception.PreOrderServiceException;
import com.bokyoung.preorderservice.model.entity.Post;
import com.bokyoung.preorderservice.model.entity.UserAccount;
import com.bokyoung.preorderservice.repository.PostRepository;
import com.bokyoung.preorderservice.repository.UserAccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserAccountRepository userAccountRepository;


    public void create(String title, String content, String email) {
        //user find
        UserAccount userEntity = userAccountRepository.findByEmail(email).orElseThrow(() ->
                new PreOrderServiceException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", email)));
        //post save
        postRepository.save(Post.of(title, content, userEntity));
    }
}
