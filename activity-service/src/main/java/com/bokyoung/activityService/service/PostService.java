package com.bokyoung.activityService.service;

import com.bokyoung.activityService.controller.response.UserAccountResponse;
import com.bokyoung.activityService.exception.ErrorCode;
import com.bokyoung.activityService.exception.PreOrderServiceException;
import com.bokyoung.activityService.model.Comment;
import com.bokyoung.activityService.model.Post;
import com.bokyoung.activityService.model.entity.*;
import com.bokyoung.activityService.repository.*;
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
    private final LikePostRepository likePostRepository;
    private final CommentRepository commentRepository;
    private final LikeCommentRepository likeCommentRepository;
    private final FollowRepository followRepository;

    @Transactional
    public void create(String title, String content, String email) {
        UserAccountResponse userAccountResponse = getUserAccountEntityOrException(email);
        postRepository.save(PostEntity.of(title, content, userAccountResponse.getId()));
    }

    @Transactional
    public Post modify(String title, String content, String email, Long postId) {
        UserAccountResponse userAccountResponse = getUserAccountEntityOrException(email);
        PostEntity postEntity = getPostEntityOrException(postId);
        //post permission
        if (postEntity.getUserId() != userAccountResponse.getId()) {
            throw new PreOrderServiceException(ErrorCode.INVALID_PERMISSION, String.format("%s has no permission with %s", email, postId));
        }

        postEntity.setTitle(title);
        postEntity.setContent(content);

        return Post.fromAccount(postRepository.save(postEntity));
    }

    @Transactional
    public void delete(String email, Long postId) {
        UserAccountResponse userAccountResponse = getUserAccountEntityOrException(email);
        PostEntity postEntity = getPostEntityOrException(postId);
        //post permission
        if (postEntity.getUserId() != userAccountResponse.getId()) {
            throw new PreOrderServiceException(ErrorCode.INVALID_PERMISSION, String.format("%s has no permission with %s", email, postId));
        }

        postRepository.delete(postEntity);
    }

    public Page<Post> list(Pageable pageable) {
        return postRepository.findAll(pageable).map(Post::fromAccount);
    }

    public Page<Post> follow(Pageable pageable, String email) {
        //user find
        UserAccountResponse userAccountResponse = getUserAccountEntityOrException(email);

        List<FollowEntity> followeeUsers = followRepository.findByFollowerId(userAccountResponse.getId());

        return postRepository.findAllByUserIdInOrderByCreatedAtDesc(followeeUsers, pageable).map(Post::fromAccount);
    }

    public Page<Post> my(String email, Pageable pageable) {
        //user find
        UserAccountResponse userAccountResponse = getUserAccountEntityOrException(email);

        return postRepository.findAllByUserId(userAccountResponse.getId(), pageable).map(Post::fromAccount);
    }

    @Transactional
    public void postLike(Long postId, String email) {
        PostEntity postEntity = getPostEntityOrException(postId);
        UserAccountResponse userAccountResponse = getUserAccountEntityOrException(email);

        //check like -> throw
        likePostRepository.findByUserIdAndPost(userAccountResponse.getId(), postEntity).ifPresent(it -> {
            throw new PreOrderServiceException(ErrorCode.ALREADY_LIKED_POST, String.format("userEmail %s already like post %d", email, postId));
        });

        //like save
        likePostRepository.save(LikePostEntity.of(userAccountResponse.getId(), postEntity));
        //TODO : Implement the rest of the functionality
        //내 글에 좋아요를 누르면 -> 뉴스피드 저장
//        newsFeedRepository.save(NewsFeedEntity.of(postEntity.getUserAccount(), NewsFeedType.NEW_LIKE_ON_POST, new NewsFeedArgs(userAccountEntity.getNickname(), postEntity.getTitle())));
        //TODO : implement
//        //Get followers -> save news feed for each follower
//        List<FollowEntity> followerEntities = followRepository.findByFollowee(userAccountEntity);
//
//        List<NewsFeedEntity> followerNewsFeeds = followerEntities.stream()
//                .map(follow -> NewsFeedEntity.of(
//                        follow.getFollower(), NewsFeedType.NEW_LIKE_ON_POST_FROM_FOLLOW, new NewsFeedArgs(userAccountEntity.getNickname(), postEntity.getUserAccount().getNickname())))
//                .collect(Collectors.toList());
//
//        newsFeedRepository.saveAll(followerNewsFeeds);
    }

    @Transactional
    public int postLikeCount(Long postId, String email) {
        PostEntity postEntity = getPostEntityOrException(postId);
        UserAccountResponse userAccountResponse = getUserAccountEntityOrException(email);

        //count like
//        List<LikePostEntity> likePostEntities = likePostRepository.findAllByPost(postEntity);
//
//        return likePostEntities.size();

        return likePostRepository.countByPost(postEntity);
    }

    @Transactional
    public void comment(Long postId, String email, String comment) {
        PostEntity postEntity = getPostEntityOrException(postId);
        UserAccountResponse userAccountResponse = getUserAccountEntityOrException(email);

        //comment save
        commentRepository.save(CommentEntity.of(userAccountResponse.getId(), userAccountResponse.getNickName(), postEntity, comment));
        //TODO : Implement the rest of the functionality
        //나의 포스트에 남겨진 댓글 -> 뉴스피드 저장
//        NewsFeedArgs newsFeedArgs = new NewsFeedArgs(userAccountEntity.getNickname(), postEntity.getTitle());
//        newsFeedRepository.save(NewsFeedEntity.of(postEntity.getUserAccount(), NewsFeedType.NEW_COMMENT_ON_POST, newsFeedArgs));
        //TODO : implement
        //Get followers -> save news feed for each follower
    }

    @Transactional
    public void commentLike(Long commentId, String email) {
        CommentEntity commentEntity = getCommentEntityOrException(commentId);
        UserAccountResponse userAccountResponse = getUserAccountEntityOrException(email);

        //check like -> throw
        likeCommentRepository.findByUserIdAndComment(userAccountResponse.getId(), commentEntity).ifPresent(it -> {
            throw new PreOrderServiceException(ErrorCode.ALREADY_LIKED_COMMENT, String.format("userEmail %s already like comment %d", email, commentId));
        });

        //like save
        likeCommentRepository.save(LikeCommentEntity.of(userAccountResponse.getId(), commentEntity));
        //TODO : Implement the rest of the functionality
        //내 댓글에 좋아요가 눌리면 -> 뉴스피드 저장
//        newsFeedRepository.save(NewsFeedEntity.of(commentEntity.getUserAccount(), NewsFeedType.NEW_LIKE_ON_COMMENT, new NewsFeedArgs(userAccountEntity.getNickname(), commentEntity.getUserAccount().getNickname())));
        //TODO : implement
        //Get followers -> save news feed for each follower
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
    private UserAccountResponse getUserAccountEntityOrException(String email) {
        //TODO: Implement the rest of the functionality
        return null;
//        return userAccountRepository.findByEmail(email).orElseThrow(() ->
//                new PreOrderServiceException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", email)));
    }

    private CommentEntity getCommentEntityOrException(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() ->
                new PreOrderServiceException(ErrorCode.COMMENT_NOT_FOUND, String.format("%s not founded", commentId)));
    }
}
