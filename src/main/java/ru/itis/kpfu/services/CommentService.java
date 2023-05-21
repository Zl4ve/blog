package ru.itis.kpfu.services;

import ru.itis.kpfu.models.Comment;

import java.util.List;

public interface CommentService {
    void saveComment(Comment comment);

    List<Comment> getCommentsByPostId(Long postId);
}
