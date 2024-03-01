package com.bokyoung.activityService.post.application;

import com.bokyoung.activityService.comment.dao.CommentRepository;
import com.bokyoung.activityService.comment.dao.LikeCommentRepository;
import com.bokyoung.activityService.post.dao.LikePostRepository;
import com.bokyoung.activityService.post.dao.PostRepository;
import com.bokyoung.activityService.comment.domain.entity.CommentEntity;
import com.bokyoung.activityService.comment.domain.entity.LikeCommentEntity;
import com.bokyoung.activityService.post.domain.entity.LikePostEntity;
import com.bokyoung.activityService.post.domain.entity.PostEntity;
import com.bokyoung.activityService.common.global.exception.ErrorCode;
import com.bokyoung.activityService.common.global.exception.PreOrderServiceException;
import com.bokyoung.activityService.common.infra.newsFeed.NewsFeedArgs;
import com.bokyoung.activityService.common.infra.newsFeed.NewsFeedCreateRequest;
import com.bokyoung.activityService.common.infra.newsFeed.NewsFeedFeignClient;
import com.bokyoung.activityService.common.infra.newsFeed.NewsFeedType;
import com.bokyoung.activityService.common.infra.userAccount.UserAccountFeignClient;
import com.bokyoung.activityService.comment.domain.model.Comment;
import com.bokyoung.activityService.post.domain.model.Post;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.AllArgsConstructor;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final LikePostRepository likePostRepository;
    private final CommentRepository commentRepository;
    private final LikeCommentRepository likeCommentRepository;
    private final NewsFeedFeignClient newsFeedFeignClient;
    private final UserAccountFeignClient userAccountFeignClient;

    private final CircuitBreakerFactory circuitBreakerFactory;
    private final RetryRegistry retryRegistry;

    @Transactional
    public void create(String title, String content, Long userId) {
        postRepository.save(PostEntity.of(title, content, userId));
        
        // TODO : 팔로우 사용자가 포스트를 작성하면 -> 뉴스피드 생성
    }

    @Transactional
    public Post modify(String title, String content, Long userId, Long postId) {
        PostEntity postEntity = getPostEntityOrException(postId);
        //post permission
        if (postEntity.getUserId() != userId) {
            throw new PreOrderServiceException(ErrorCode.INVALID_PERMISSION, String.format("%s has no permission with %s", userId, postId));
        }

        postEntity.setTitle(title);
        postEntity.setContent(content);

        return Post.fromAccount(postRepository.save(postEntity));
    }

    @Transactional
    public void delete(Long userId, Long postId) {
        PostEntity postEntity = getPostEntityOrException(postId);
        //post permission
        if (postEntity.getUserId() != userId) {
            throw new PreOrderServiceException(ErrorCode.INVALID_PERMISSION, String.format("%s has no permission with %s", userId, postId));
        }

        postRepository.delete(postEntity);
    }

    public Page<Post> list(Pageable pageable) {
        return postRepository.findAll(pageable).map(Post::fromAccount);
    }

    public Page<Post> my(Long userId, Pageable pageable) {

        return postRepository.findAllByUserId(userId, pageable).map(Post::fromAccount);
    }

    @Transactional
    public void postLike(Long postId, Long userId) {
        PostEntity postEntity = getPostEntityOrException(postId);

        //check like -> throw
        likePostRepository.findByUserIdAndPost(userId, postEntity).ifPresent(it -> {
            throw new PreOrderServiceException(ErrorCode.ALREADY_LIKED_POST, String.format("userEmail %s already like post %d", userId, postId));
        });

        //like save
        likePostRepository.save(LikePostEntity.of(userId, postEntity));

        //내 글에 좋아요를 누르면 -> 뉴스피드 저장
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
        Retry retry = retryRegistry.retry("retry");
        String fromUserNickname = circuitBreaker.run(() -> Retry.decorateFunction(retry, s ->
                getUserByPrincipalId(userId)).apply(1), throwable -> "failure");

        circuitBreaker.run(() -> Retry.decorateFunction(retry, s ->
                        newsFeedFeignClient.createNewsFeed(new NewsFeedCreateRequest(postEntity.getUserId(), NewsFeedType.NEW_LIKE_ON_POST, new NewsFeedArgs(fromUserNickname, null, postEntity.getTitle())))).apply(1),
                throwable -> "failure");

        //TODO : 팔로우한 사용자가 좋아요를 누르면 -> 뉴스피드 저장
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
    public int postLikeCount(Long postId, Long userId) {
        PostEntity postEntity = getPostEntityOrException(postId);

        //count like
//        List<LikePostEntity> likePostEntities = likePostRepository.findAllByPost(postEntity);
//
//        return likePostEntities.size();

        return likePostRepository.countByPost(postEntity);
    }

    @Transactional
    public void comment(Long postId, Long userId, String comment) {
        PostEntity postEntity = getPostEntityOrException(postId);

        //comment save
        commentRepository.save(CommentEntity.of(userId, postEntity, comment));

        //나의 포스트에 남겨진 댓글 -> 뉴스피드 저장
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
        Retry retry = retryRegistry.retry("retry");
        String fromUserNickname = circuitBreaker.run(() -> Retry.decorateFunction(retry, s ->
                getUserByPrincipalId(userId)).apply(1), throwable -> "failure");

        circuitBreaker.run(() -> Retry.decorateFunction(retry, s ->
                        newsFeedFeignClient.createNewsFeed(new NewsFeedCreateRequest(postEntity.getUserId(), NewsFeedType.NEW_COMMENT_ON_POST, new NewsFeedArgs(fromUserNickname, null, postEntity.getTitle())))).apply(1),
                throwable -> "failure");

        //TODO : 팔로우한 사용자가 댓글을 남기면 -> 뉴스피드 저장
    }

    @Transactional
    public void commentLike(Long commentId, Long userId) {
        CommentEntity commentEntity = getCommentEntityOrException(commentId);

        //check like -> throw
        likeCommentRepository.findByUserIdAndComment(userId, commentEntity).ifPresent(it -> {
            throw new PreOrderServiceException(ErrorCode.ALREADY_LIKED_COMMENT, String.format("userEmail %s already like comment %d", userId, commentId));
        });

        //like save
        likeCommentRepository.save(LikeCommentEntity.of(userId, commentEntity));

        //내 댓글에 좋아요가 눌리면 -> 뉴스피드 저장
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
        Retry retry = retryRegistry.retry("retry");
        String fromUserNickname = circuitBreaker.run(() -> Retry.decorateFunction(retry, s ->
                getUserByPrincipalId(userId)).apply(1), throwable -> "failure");
        String toUserNickname = circuitBreaker.run(() -> Retry.decorateFunction(retry, s ->
                getUserByCommentUserId(commentEntity.getUserId())).apply(1), throwable -> "failure");

        circuitBreaker.run(() -> Retry.decorateFunction(retry, s ->
                    newsFeedFeignClient.createNewsFeed(new NewsFeedCreateRequest(commentEntity.getUserId(), NewsFeedType.NEW_LIKE_ON_COMMENT, new NewsFeedArgs(fromUserNickname, toUserNickname, null)))).apply(1),
                throwable -> "failure");

        //TODO : 팔로우한 사용자가 댓글을 좋아요 하면 -> 뉴스피드 저장
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

    private CommentEntity getCommentEntityOrException(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() ->
                new PreOrderServiceException(ErrorCode.COMMENT_NOT_FOUND, String.format("%s not founded", commentId)));
    }

    private String getUserByPrincipalId(Long userId) {
        return userAccountFeignClient.getUserAccountByPrincipalId(userId)
                .getResult()
                .getNickname();
    }

    private String getUserByCommentUserId(Long userId) {
        return userAccountFeignClient.getUserAccountByUserId(userId)
                .getResult()
                .getNickname();
    }
}
