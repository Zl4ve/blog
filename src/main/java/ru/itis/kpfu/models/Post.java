package ru.itis.kpfu.models;

public class Post {
    private Long id;
    private Long authorId;
    private String title;
    private String status;

    public Post(Long id, Long authorId, String title, String status) {
        this.id = id;
        this.authorId = authorId;
        this.title = title;
        this.status = status;
    }

    public Post(Long authorId, String title, String status) {
        this.authorId = authorId;
        this.title = title;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public String getTitle() {
        return title;
    }

    public String getStatus() {
        return status;
    }
}
