package ru.itis.kpfu.repositories;

import ru.itis.kpfu.models.Comment;

import java.util.List;

public interface CommentRepository {
    void saveComment(Comment comment);

    List<Comment> getCommentsByPostId(Long postId);
}
