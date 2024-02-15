package com.bokyoung.activityService.service;

import com.bokyoung.activityService.client.NewsFeedArgs;
import com.bokyoung.activityService.client.NewsFeedCreateRequest;
import com.bokyoung.activityService.client.NewsFeedFeignClient;
import com.bokyoung.activityService.client.NewsFeedType;
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

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final LikePostRepository likePostRepository;
    private final CommentRepository commentRepository;
    private final LikeCommentRepository likeCommentRepository;
    private final NewsFeedFeignClient newsFeedFeignClient;

    @Transactional
    public void create(String title, String content, Long userId) {
        postRepository.save(PostEntity.of(title, content, userId));
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

        /**
         * 내 글에 좋아요를 누르면 -> 뉴스피드 저장
         */
        newsFeedFeignClient.createNewsFeed(new NewsFeedCreateRequest(postEntity.getUserId(), NewsFeedType.NEW_LIKE_ON_POST, new NewsFeedArgs("fromUser", null, postEntity.getTitle())));

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

        /**
         * 나의 포스트에 남겨진 댓글 -> 뉴스피드 저장
         */
        newsFeedFeignClient.createNewsFeed(new NewsFeedCreateRequest(postEntity.getUserId(), NewsFeedType.NEW_COMMENT_ON_POST, new NewsFeedArgs("fromuser", null, postEntity.getTitle())));

        //TODO : implement
        //Get followers -> save news feed for each follower
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

        /**
         * 내 댓글에 좋아요가 눌리면 -> 뉴스피드 저장
         */
        newsFeedFeignClient.createNewsFeed(new NewsFeedCreateRequest(commentEntity.getUserId(), NewsFeedType.NEW_LIKE_ON_COMMENT, new NewsFeedArgs("fromuser", "touser", null)));
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

    private CommentEntity getCommentEntityOrException(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() ->
                new PreOrderServiceException(ErrorCode.COMMENT_NOT_FOUND, String.format("%s not founded", commentId)));
    }
}
