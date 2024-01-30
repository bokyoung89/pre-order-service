package com.bokyoung.preorderservice.service;

import com.bokyoung.preorderservice.exception.ErrorCode;
import com.bokyoung.preorderservice.exception.PreOrderServiceException;
import com.bokyoung.preorderservice.model.Comment;
import com.bokyoung.preorderservice.model.Post;
import com.bokyoung.preorderservice.model.entity.LikePostEntity;
import com.bokyoung.preorderservice.model.entity.PostEntity;
import com.bokyoung.preorderservice.model.entity.UserAccountEntity;
import com.bokyoung.preorderservice.repository.LikePostRepository;
import com.bokyoung.preorderservice.repository.PostRepository;
import com.bokyoung.preorderservice.repository.UserAccountRepository;
import com.bokyoung.preorderservice.model.entity.*;
import com.bokyoung.preorderservice.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserAccountRepository userAccountRepository;
    private final LikePostRepository likePostRepository;
    private final CommentRepository commentRepository;
    private final LikeCommentRepository likeCommentRepository;

    @Transactional
    public void create(String title, String content, String email) {
        UserAccountEntity userAccountEntity = getUserAccountEntityOrException(email);
        postRepository.save(PostEntity.of(title, content, userAccountEntity));
    }

    @Transactional
    public Post modify(String title, String content, String email, Long postId) {
        UserAccountEntity userAccountEntity = getUserAccountEntityOrException(email);
        PostEntity postEntity = getPostEntityOrException(postId);
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
        UserAccountEntity userAccountEntity = getUserAccountEntityOrException(email);
        PostEntity postEntity = getPostEntityOrException(postId);
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
        UserAccountEntity userAccountEntity = getUserAccountEntityOrException(email);

        return postRepository.findAllByUserAccount(userAccountEntity, pageable).map(Post::fromAccount);
    }

    @Transactional
    public void postLike(Long postId, String email) {
        PostEntity postEntity = getPostEntityOrException(postId);
        UserAccountEntity userAccountEntity = getUserAccountEntityOrException(email);

        //check like -> throw
        likePostRepository.findByUserAccountAndPost(userAccountEntity, postEntity).ifPresent(it -> {
            throw new PreOrderServiceException(ErrorCode.ALREADY_LIKED_POST, String.format("userEmail %s already like post %d", email, postId));
        });

        //like save
        likePostRepository.save(LikePostEntity.of(userAccountEntity, postEntity));
    }

    @Transactional
    public int postLikeCount(Long postId) {
        PostEntity postEntity = getPostEntityOrException(postId);

        //count like
//        List<LikePostEntity> likePostEntities = likePostRepository.findAllByPost(postEntity);
//
//        return likePostEntities.size();
        return likePostRepository.countByPost(postEntity);
    }

    @Transactional
    public void comment(Long postId, String email, String comment) {
        PostEntity postEntity = getPostEntityOrException(postId);
        UserAccountEntity userAccountEntity = getUserAccountEntityOrException(email);

        //comment save
        commentRepository.save(CommentEntity.of(userAccountEntity, postEntity, comment));

    }

    @Transactional
    public void commentLike(Long commentId, String email) {
        CommentEntity commentEntity = getCommentEntityOrException(commentId);
        UserAccountEntity userAccountEntity = getUserAccountEntityOrException(email);

        //check like -> throw
        likeCommentRepository.findByUserAccountAndComment(userAccountEntity, commentEntity).ifPresent(it -> {
            throw new PreOrderServiceException(ErrorCode.ALREADY_LIKED_COMMENT, String.format("userEmail %s already like comment %d", email, commentId));
        });

        //like save
        likeCommentRepository.save(LikeCommentEntity.of(userAccountEntity, commentEntity));
    }

    @Transactional
    public int commentLikeCount(Long commentId) {
        CommentEntity commentEntity = getCommentEntityOrException(commentId);

        //count like
//        List<LikePostEntity> likePostEntities = likePostRepository.findAllByPost(postEntity);
//
//        return likePostEntities.size();
        return likeCommentRepository.countByComment(commentEntity);
    }

    public Page<Comment> getComment(Long postId, Pageable pageable) {
        PostEntity postEntity = getPostEntityOrException(postId);
        return commentRepository.findAllByPost(postEntity, pageable).map((Comment::fromAccount));
    }

    // post exist
    private PostEntity getPostEntityOrException(Long postId) {
        return postRepository.findById(postId).orElseThrow(() ->
                new PreOrderServiceException(ErrorCode.POST_NOT_FOUND, String.format("%s not founded", postId)));
    }

    // user exist
    private UserAccountEntity getUserAccountEntityOrException(String email) {
        return userAccountRepository.findByEmail(email).orElseThrow(() ->
                new PreOrderServiceException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", email)));
    }

    private CommentEntity getCommentEntityOrException(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() ->
                new PreOrderServiceException(ErrorCode.COMMENT_NOT_FOUND, String.format("%s not founded", commentId)));
    }
}
