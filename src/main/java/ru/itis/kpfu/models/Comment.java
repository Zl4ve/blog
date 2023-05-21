package ru.itis.kpfu.models;

public class Comment {
    private Long id;

    private Long postId;

    private Long authorId;

    private String text;

    public Comment(Long id, Long postId, Long authorId, String text) {
        this.id = id;
        this.postId = postId;
        this.authorId = authorId;
        this.text = text;
    }

    public Comment(Long postId, Long authorId, String text) {
        this.postId = postId;
        this.authorId = authorId;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public Long getPostId() {
        return postId;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public String getText() {
        return text;
    }
}
