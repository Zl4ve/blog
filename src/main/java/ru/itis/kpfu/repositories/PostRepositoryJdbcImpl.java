package ru.itis.kpfu.repositories;

import ru.itis.kpfu.exceptions.DbException;
import ru.itis.kpfu.models.Post;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class PostRepositoryJdbcImpl implements PostRepository {

    private final DataSource dataSource;

    private static final Function<ResultSet, Post> postMapper = row -> {

        try {
            Long id = row.getLong("id");
            Long authorId = row.getLong("author_id");
            String title = row.getString("title");
            String status = row.getString("status");
            return new Post(id, authorId, title, status);
        } catch (SQLException e) {
            throw new DbException(e);
        }

    };


    public PostRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    //language=SQL
    private static final String SQL_GET_POST_BY_ID = "select * from post where id = ?;";

    @Override
    public Optional<Post> findById(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_GET_POST_BY_ID)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.ofNullable(postMapper.apply(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }

    //language=SQL
    private static final String SQL_GET_POST_CONTENT = "select text, picture from post_content where post_id = ?;";

    @Override
    public List<String> getPostContent(Long postId) {
        List<String> content = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_GET_POST_CONTENT)) {

            statement.setLong(1, postId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                content.add(resultSet.getString("text"));
                content.add(resultSet.getString("picture"));
            }
            return content;
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }

    //language=SQL
    private static final String SQL_SAVE_POST_CONTENT = "insert into post_content(text, picture, post_id) values (?, ?, ?);";

    @Override
    public void saveContent(String text, String picture, Long postId) {
        try (Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SAVE_POST_CONTENT)) {
            statement.setString(1, text);
            statement.setString(2, picture);
            statement.setLong(3, postId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }

    //language=SQL
    private static final String SQL_SAVE_POST = "insert into post(author_id, title, status) values (?, ?, ?::status);";

    @Override
    public void savePost(Post post) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SAVE_POST)) {
            statement.setLong(1, post.getAuthorId());
            statement.setString(2, post.getTitle());
            statement.setString(3, post.getStatus());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }

    //language=SQL
    private static final String SQL_GET_POST_COUNT = "select max(id) as maximum from post;";

    @Override
    public Long getMaxPostId() {
        try (Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_GET_POST_COUNT)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong("maximum");
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }

    //language=SQL
    private static final String SQL_GET_SUGGESTED_POSTS = "select * from post where status = 'suggested';";

    @Override
    public List<Post> getSuggestedPosts() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_GET_SUGGESTED_POSTS)) {
            ResultSet resultSet = statement.executeQuery();
            List<Post> suggestedPosts = new ArrayList<>();
            while (resultSet.next()) {
                suggestedPosts.add(postMapper.apply(resultSet));
            }
            return suggestedPosts;
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }

    //language=SQL
    private static final String SQL_UPDATE_POST_STATUS_ACCEPTED = "update post set status = 'accepted' where id = ?;";

    @Override
    public void updatePostStatusAccepted(Long postId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_POST_STATUS_ACCEPTED)) {
            statement.setLong(1, postId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e);
        }

    }

    //language=SQL
    public static final String SQL_DELETE_POST = "delete from post where id = ?;";

    @Override
    public void deletePost(Long postId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_POST)) {

            statement.setLong(1, postId);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e);
        }

    }

    //language=SQL
    public static final String SQL_DELETE_POST_CONTENT = "delete from post_content where post_id = ?;";
    @Override
    public void deletePostContent(Long postId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_POST_CONTENT)) {

            statement.setLong(1, postId);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e);
        }

    }

    //language=SQL
    private static final String SQL_GET_ALL_POSTS = "select * from post where status = 'accepted';";

    @Override
    public List<Post> getAllAcceptedPosts() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_GET_ALL_POSTS)) {

            ResultSet resultSet = statement.executeQuery();
            List<Post> posts = new ArrayList<>();
            while (resultSet.next()) {
                posts.add(postMapper.apply(resultSet));
            }
            return posts;

        } catch (SQLException e) {
            throw new DbException(e);
        }
    }

    //language=SQL
    private static final String SQL_GET_POSTS_BY_AUTHOR_ID = "select * from post where author_id = ?";

    @Override
    public List<Post> getPostsByAuthorId(Long authorId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_GET_POST_BY_ID)) {

            statement.setLong(1, authorId);
            ResultSet resultSet = statement.executeQuery();
            List<Post> posts = new ArrayList<>();

            while (resultSet.next()) {
                posts.add(postMapper.apply(resultSet));
            }

            return posts;
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }
}
