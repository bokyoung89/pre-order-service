package com.bokyoung.preorderservice.service;

import com.bokyoung.preorderservice.exception.ErrorCode;
import com.bokyoung.preorderservice.exception.PreOrderServiceException;
import com.bokyoung.preorderservice.model.Post;
import com.bokyoung.preorderservice.model.entity.PostEntity;
import com.bokyoung.preorderservice.model.entity.UserAccountEntity;
import com.bokyoung.preorderservice.repository.PostRepository;
import com.bokyoung.preorderservice.repository.UserAccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserAccountRepository userAccountRepository;


    @Transactional
    public void create(String title, String content, String email) {
        //user find
        UserAccountEntity userAccountEntity = userAccountRepository.findByEmail(email).orElseThrow(() ->
                new PreOrderServiceException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", email)));
        //post save
        postRepository.save(PostEntity.of(title, content, userAccountEntity));
    }

    @Transactional
    public Post modify(String title, String content, String email, Long postId) {
        //user find
        UserAccountEntity userAccountEntity = userAccountRepository.findByEmail(email).orElseThrow(() ->
                new PreOrderServiceException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", email)));
        //post exsit
        PostEntity postEntity = postRepository.findById(postId).orElseThrow(() ->
                new PreOrderServiceException(ErrorCode.POST_NOT_FOUND, String.format("%s not founded", postId)));
        //post permission
        if (postEntity.getUserAccount() != userAccountEntity) {
            throw new PreOrderServiceException(ErrorCode.INVALID_PERMISSION, String.format("%s has no permission with %s", email, postId));
        }

        postEntity.setTitle(title);
        postEntity.setContent(content);

        return Post.fromAccount(postRepository.save(postEntity));
    }

    @Transactional
    public void delete(String email, Long postId) {
        //user find
        UserAccountEntity userAccountEntity = userAccountRepository.findByEmail(email).orElseThrow(() ->
                new PreOrderServiceException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", email)));
        //post exsit
        PostEntity postEntity = postRepository.findById(postId).orElseThrow(() ->
                new PreOrderServiceException(ErrorCode.POST_NOT_FOUND, String.format("%s not founded", postId)));
        //post permission
        if (postEntity.getUserAccount() != userAccountEntity) {
            throw new PreOrderServiceException(ErrorCode.INVALID_PERMISSION, String.format("%s has no permission with %s", email, postId));
        }

        postRepository.delete(postEntity);
    }

    public Page<Post> list(Pageable pageable) {
        return postRepository.findAll(pageable).map(Post::fromAccount);
    }

    public Page<Post> my(String email, Pageable pageable) {
        //user find
        UserAccountEntity userAccountEntity = userAccountRepository.findByEmail(email).orElseThrow(() ->
                new PreOrderServiceException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", email)));

        return postRepository.findAllByUserAccount(userAccountEntity, pageable).map(Post::fromAccount);
    }

    ;
}
