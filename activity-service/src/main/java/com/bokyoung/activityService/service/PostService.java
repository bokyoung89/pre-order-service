package com.bokyoung.activityService.service;

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
    public void create(String title, String content, Long principalId) {
//        Long userId = getUserIdOrException(principalId);
        postRepository.save(PostEntity.of(title, content, principalId));
    }

    @Transactional
    public Post modify(String title, String content, Long principalId, Long postId) {
//        Long userId = getUserIdOrException(principalId);
        PostEntity postEntity = getPostEntityOrException(principalId);
        //post permission
        if (postEntity.getUserId() != principalId) {
            throw new PreOrderServiceException(ErrorCode.INVALID_PERMISSION, String.format("%s has no permission with %s", principalId, postId));
        }

        postEntity.setTitle(title);
        postEntity.setContent(content);

        return Post.fromAccount(postRepository.save(postEntity));
    }

    @Transactional
    public void delete(Long principalId, Long postId) {
//        Long userId = getUserIdOrException(principalId);
        PostEntity postEntity = getPostEntityOrException(postId);
        //post permission
        if (postEntity.getUserId() != principalId) {
            throw new PreOrderServiceException(ErrorCode.INVALID_PERMISSION, String.format("%s has no permission with %s", principalId, postId));
        }

        postRepository.delete(postEntity);
    }

    public Page<Post> list(Pageable pageable) {
        return postRepository.findAll(pageable).map(Post::fromAccount);
    }

    public Page<Post> follow(Pageable pageable, Long principalId) {
        //user find
//        Long userId = getUserIdOrException(principalId);

        List<FollowEntity> followeeUsers = followRepository.findByFollowerId(principalId);

        return postRepository.findAllByUserIdInOrderByCreatedAtDesc(followeeUsers, pageable).map(Post::fromAccount);
    }

    public Page<Post> my(Long principalId, Pageable pageable) {
        //user find
//        Long userId = getUserIdOrException(principalId);

        return postRepository.findAllByUserId(principalId, pageable).map(Post::fromAccount);
    }

    @Transactional
    public void postLike(Long postId, Long principalId) {
        PostEntity postEntity = getPostEntityOrException(postId);
//        Long userId = getUserIdOrException(principalId);

        //check like -> throw
        likePostRepository.findByUserIdAndPost(principalId, postEntity).ifPresent(it -> {
            throw new PreOrderServiceException(ErrorCode.ALREADY_LIKED_POST, String.format("userEmail %s already like post %d", principalId, postId));
        });

        //like save
        likePostRepository.save(LikePostEntity.of(principalId, postEntity));

        /**
         * 내 글에 좋아요를 누르면 -> 뉴스피드 저장
         * TODO : feign client로 호출한다.
         */
//        newsFeedRepository.save(NewsFeedEntity.of(postEntity.getUserId(), NewsFeedType.NEW_LIKE_ON_POST, new NewsFeedArgs(userAccountEntity.getNickname(), postEntity.getTitle())));

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
    public int postLikeCount(Long postId, Long principalId) {
        PostEntity postEntity = getPostEntityOrException(postId);
//        Long userId = getUserIdOrException(principalId);

        //count like
//        List<LikePostEntity> likePostEntities = likePostRepository.findAllByPost(postEntity);
//
//        return likePostEntities.size();

        return likePostRepository.countByPost(postEntity);
    }

    @Transactional
    public void comment(Long postId, Long principalId, String comment) {
        PostEntity postEntity = getPostEntityOrException(postId);
//        Long userId = getUserIdOrException(principalId);

        //comment save
        commentRepository.save(CommentEntity.of(principalId, postEntity, comment));

        /**
         * 나의 포스트에 남겨진 댓글 -> 뉴스피드 저장
         * TODO : feign client로 호출한다.
         */
//        NewsFeedArgs newsFeedArgs = new NewsFeedArgs(userAccountEntity.getNickname(), postEntity.getTitle());
//        newsFeedRepository.save(NewsFeedEntity.of(postEntity.getUserId(), NewsFeedType.NEW_COMMENT_ON_POST, newsFeedArgs));
        //TODO : implement
        //Get followers -> save news feed for each follower
    }

    @Transactional
    public void commentLike(Long commentId, Long principalId) {
        CommentEntity commentEntity = getCommentEntityOrException(commentId);
//        Long userId = getUserIdOrException(principalId);

        //check like -> throw
        likeCommentRepository.findByUserIdAndComment(principalId, commentEntity).ifPresent(it -> {
            throw new PreOrderServiceException(ErrorCode.ALREADY_LIKED_COMMENT, String.format("userEmail %s already like comment %d", principalId, commentId));
        });

        //like save
        likeCommentRepository.save(LikeCommentEntity.of(principalId, commentEntity));

        /**
         * 내 댓글에 좋아요가 눌리면 -> 뉴스피드 저장
         * TODO : feign client로 호출한다.
         */
//        newsFeedRepository.save(NewsFeedEntity.of(commentEntity.getUserId(), NewsFeedType.NEW_LIKE_ON_COMMENT, new NewsFeedArgs(userAccountEntity.getNickname(), commentEntity.getUserAccount().getNickname())));
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

    //TODO : USER 찾는 메소드. USER-SERVICE에 역할 이관
    // user exist
//    private Long getUserIdOrException(Long userId) {
//        return userAccountRepository.findById(userId).orElseThrow(() ->
//                new PreOrderServiceException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", userId)));
//    }

    private CommentEntity getCommentEntityOrException(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() ->
                new PreOrderServiceException(ErrorCode.COMMENT_NOT_FOUND, String.format("%s not founded", commentId)));
    }
}
