package com.bokyoung.newsFeedService.service;

import com.bokyoung.newsFeedService.exception.ErrorCode;
import com.bokyoung.newsFeedService.exception.PreOrderServiceException;
import com.bokyoung.newsFeedService.model.Comment;
import com.bokyoung.newsFeedService.model.NewsFeedArgs;
import com.bokyoung.newsFeedService.model.NewsFeedType;
import com.bokyoung.newsFeedService.model.Post;
import com.bokyoung.newsFeedService.model.entity.*;
import com.bokyoung.newsFeedService.repository.*;
import com.bokyoung.newsFeedService.repository.LikePostRepository;
import com.bokyoung.newsFeedService.repository.PostRepository;
import com.bokyoung.newsFeedService.repository.UserAccountRepository;
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
    private final NewsFeedRepository newsFeedRepository;
    private final FollowRepository followRepository;

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

    public Page<Post> follow(Pageable pageable, String email) {
        //user find
        UserAccountEntity userAccountEntity = getUserAccountEntityOrException(email);

        List<FollowEntity> followeeUsers = followRepository.findByFollower(userAccountEntity);

        return postRepository.findAllByUserAccountInOrderByCreatedAtDesc(followeeUsers, pageable).map(Post::fromAccount);
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
        //내 글에 좋아요를 누르면 -> 뉴스피드 저장
        newsFeedRepository.save(NewsFeedEntity.of(postEntity.getUserAccount(), NewsFeedType.NEW_LIKE_ON_POST, new NewsFeedArgs(userAccountEntity.getNickname(), postEntity.getTitle())));
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
        UserAccountEntity userAccountEntity = getUserAccountEntityOrException(email);

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
        //나의 포스트에 남겨진 댓글 -> 뉴스피드 저장
        NewsFeedArgs newsFeedArgs = new NewsFeedArgs(userAccountEntity.getNickname(), postEntity.getTitle());
        newsFeedRepository.save(NewsFeedEntity.of(postEntity.getUserAccount(), NewsFeedType.NEW_COMMENT_ON_POST, newsFeedArgs));
        //TODO : implement
        //Get followers -> save news feed for each follower
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
        //내 댓글에 좋아요가 눌리면 -> 뉴스피드 저장
        newsFeedRepository.save(NewsFeedEntity.of(commentEntity.getUserAccount(), NewsFeedType.NEW_LIKE_ON_COMMENT, new NewsFeedArgs(userAccountEntity.getNickname(), commentEntity.getUserAccount().getNickname())));
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
    private UserAccountEntity getUserAccountEntityOrException(String email) {
        return userAccountRepository.findByEmail(email).orElseThrow(() ->
                new PreOrderServiceException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", email)));
    }

    private CommentEntity getCommentEntityOrException(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() ->
                new PreOrderServiceException(ErrorCode.COMMENT_NOT_FOUND, String.format("%s not founded", commentId)));
    }
}
