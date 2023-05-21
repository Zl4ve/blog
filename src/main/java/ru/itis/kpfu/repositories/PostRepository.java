package ru.itis.kpfu.repositories;

import ru.itis.kpfu.models.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    Optional<Post> findById(Long id);

    List<String> getPostContent(Long postId);

    void saveContent(String text, String picture, Long postId);

    void savePost(Post post);

    Long getMaxPostId();

    List<Post> getSuggestedPosts();

    void updatePostStatusAccepted(Long postId);

    void deletePost(Long postId);

    void deletePostContent(Long postId);

    List<Post> getAllAcceptedPosts();

    List<Post> getPostsByAuthorId(Long authorId);
}
