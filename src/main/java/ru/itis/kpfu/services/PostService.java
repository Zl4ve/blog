package ru.itis.kpfu.services;

import ru.itis.kpfu.models.Post;
import ru.itis.kpfu.parsers.PostTextParser;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface PostService {
    void createPost(Post post);

    void saveContent(List<String> parsedText, Long postId);

    Post getPostById(Long id);

    Long getMaxPostId();

    List<String> getPostContentByPostId(Long id);

    List<Post> getSuggestedPosts();

    String getFirstPostImageUri(Long postId, ServletContext context);

    void acceptPost(Long postId);

    List<String> replaceNewLineSymbolsWithHtmlNewLineTag(List<String> postContent);

    void deletePost(Long postId);

    List<Post> getAllAcceptedPosts();

    PostTextParser getParser();

    boolean validateParsedPostContent(List<String> parsedContent);

    void setTextError(List<String> parsedContent, HttpServletRequest request);

    List<Post> getPostsByAuthorId(Long authorId);


}
