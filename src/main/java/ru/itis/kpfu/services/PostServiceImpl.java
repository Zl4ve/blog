package ru.itis.kpfu.services;

import ru.itis.kpfu.models.Post;
import ru.itis.kpfu.parsers.PostTextParser;
import ru.itis.kpfu.repositories.PostRepository;
import ru.itis.kpfu.validators.PostCreateDataErrorMessageGenerator;
import ru.itis.kpfu.validators.SignUpDataErrorMessageGenerator;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    private PostTextParser parser;

    private PostCreateDataErrorMessageGenerator errorsGenerator;

    public PostServiceImpl(PostRepository postRepository, PostTextParser parser, PostCreateDataErrorMessageGenerator errorsGenerator) {
        this.postRepository = postRepository;
        this.parser = parser;
        this.errorsGenerator = errorsGenerator;
    }

    @Override
    public void createPost(Post post) {
        postRepository.savePost(post);
    }

    @Override
    public Post getPostById(Long id) {
        Optional<Post> post = postRepository.findById(id);
        return post.orElse(null);
    }

    @Override
    public void saveContent(List<String> parsedText, Long postId) {
        for (int i = 0; i < parsedText.size(); i += 2) {
            postRepository.saveContent(parsedText.get(i), parsedText.get(i + 1), postId);
        }
    }

    @Override
    public Long getMaxPostId() {
        return postRepository.getMaxPostId();
    }

    @Override
    public List<String> getPostContentByPostId(Long id) {
        return postRepository.getPostContent(id);
    }

    @Override
    public List<Post> getSuggestedPosts() {
        return postRepository.getSuggestedPosts();
    }

    @Override
    public String getFirstPostImageUri(Long postId, ServletContext context) {
        List<String> postContent = getPostContentByPostId(postId);
        for (String elem : postContent) {
            if (elem.startsWith((String) context.getAttribute("postImagesPath"))) {
                return elem;
            }
        }
        return "";
    }

    @Override
    public void acceptPost(Long postId) {
        postRepository.updatePostStatusAccepted(postId);
    }

    @Override
    public List<String> replaceNewLineSymbolsWithHtmlNewLineTag(List<String> postContent) {
        for (int i = 0; i < postContent.size(); i += 2) {
           postContent.set(i, postContent.get(i).replaceAll("\n", "<br>"));
        }
        return postContent;
    }

    @Override
    public void deletePost(Long postId) {
        postRepository.deletePostContent(postId);
        postRepository.deletePost(postId);
    }

    @Override
    public List<Post> getAllAcceptedPosts() {
        return postRepository.getAllAcceptedPosts();
    }

    @Override
    public PostTextParser getParser() {
        return this.parser;
    }

    @Override
    public boolean validateParsedPostContent(List<String> parsedContent) {
        return errorsGenerator.generateError(parsedContent) == null;
    }

    @Override
    public void setTextError(List<String> parsedContent, HttpServletRequest request) {
        request.setAttribute("textError", errorsGenerator.generateError(parsedContent));
    }

    @Override
    public List<Post> getPostsByAuthorId(Long authorId) {
        return postRepository.getPostsByAuthorId(authorId);
    }
}
