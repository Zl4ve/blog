package ru.itis.kpfu.repositories;

import ru.itis.kpfu.exceptions.DbException;
import ru.itis.kpfu.models.Comment;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class CommentRepositoryJdbcImpl implements CommentRepository {

    private final DataSource dataSource;

    private static final Function<ResultSet, Comment> commentMapper = row -> {

        try {
            Long id = row.getLong("id");
            Long postId = row.getLong("post_id");
            Long authorId = row.getLong("author_id");
            String text = row.getString("text");
            return new Comment(id, postId, authorId, text);
        } catch (SQLException e) {
            throw new DbException(e);
        }

    };


    public CommentRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    //language=SQL
    private static final String SQL_SAVE_COMMENT = "insert into comment(post_id, author_id, text) values (?, ?, ?);";

    @Override
    public void saveComment(Comment comment) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SAVE_COMMENT)) {
            statement.setLong(1, comment.getPostId());
            statement.setLong(2, comment.getAuthorId());
            statement.setString(3, comment.getText());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }

    //language=SQL
    private static final String SQL_GET_COMMENTS_BY_POST_ID = "select * from comment where post_id = ? order by id desc;";

    @Override
    public List<Comment> getCommentsByPostId(Long postId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_GET_COMMENTS_BY_POST_ID)) {
            statement.setLong(1, postId);
            ResultSet resultSet = statement.executeQuery();
            List<Comment> comments = new ArrayList<>();
            while (resultSet.next()) {
                comments.add(commentMapper.apply(resultSet));
            }
            return comments;
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }
}
