package ru.itis.kpfu.repositories;

import ru.itis.kpfu.exceptions.DbException;
import ru.itis.kpfu.models.Account;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class AccountsRepositoryJdbcImpl implements AccountsRepository {

    private final DataSource dataSource;

    private static final Function<ResultSet, Account> accountMapper = row -> {

        try {
            Long id = row.getLong("id");
            String username = row.getString("username");
            String password = row.getString("password");
            String email = row.getString("email");
            String role = row.getString("role");
            return new Account(id, username, password, email, role);
        } catch (SQLException e) {
            throw new DbException(e);
        }

    };

    public AccountsRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    //language=SQL
    private static final String SQL_SAVE_ACCOUNT = "insert into account(username, password, email, role) values (?, ?, ?, 'simpleuser');";

    @Override
    public void save(Account account) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SAVE_ACCOUNT)) {
            statement.setString(1, account.getUsername());
            statement.setString(2, account.getPassword());
            statement.setString(3, account.getEmail());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }

    //language=SQL
    private static final String SQL_FIND_ACCOUNT_BY_USERNAME = "select * from account where username = ?;";



    @Override
    public Optional<Account> findByUsername(String username) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_ACCOUNT_BY_USERNAME)) {

            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.ofNullable(accountMapper.apply(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }

    //language=SQL
    private static final String SQL_FIND_ACCOUNT_BY_EMAIL = "select * from account where email = ?;";

    @Override
    public Optional<Account> findByEmail(String email) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_ACCOUNT_BY_EMAIL)) {

            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.ofNullable(accountMapper.apply(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }

    //language=SQL
    private static final String SQL_FIND_ACCOUNT_BY_USERNAME_AND_PASSWORD = "select * from account where username = ? and password = ?;";

    @Override
    public Optional<Account> findByUsernameAndPassword(String username, String password) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_ACCOUNT_BY_USERNAME_AND_PASSWORD)) {

            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.ofNullable(accountMapper.apply(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }

    //language=SQL
    private static final String SQL_FIND_ACCOUNT_BY_ID = "select * from account where id = ?;";

    @Override
    public Optional<Account> findById(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_ACCOUNT_BY_ID)) {

            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.ofNullable(accountMapper.apply(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DbException(e);
        }

    }

    //language=SQL
    private static final String SQL_SAVE_SUBSCRIPTION = "insert into subscription(account_id, subscriber_id) values (?, ?);";

    @Override
    public void saveSubscription(Long accountId, Long subscriberId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SAVE_SUBSCRIPTION)) {
            statement.setLong(1, accountId);
            statement.setLong(2, subscriberId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }

    //language=SQL
    private static final String SQL_GET_SUBSCRIBERS_BY_ACCOUNT_ID = "select subscriber_id from subscription where account_id = ?;";

    @Override
    public List<Account> getSubscribers(Long accountId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_GET_SUBSCRIBERS_BY_ACCOUNT_ID)) {

            statement.setLong(1, accountId);
            ResultSet resultSet = statement.executeQuery();
            List<Long> subscribersId = new ArrayList<>();
            while (resultSet.next()) {
                subscribersId.add(resultSet.getLong("subscriber_id"));
            }

            List<Account> subscribers = new ArrayList<>();
            for (Long id : subscribersId) {
                subscribers.add(findById(id).orElse(null));
            }

            return subscribers;
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }

    //language=SQL
    private static final String SQL_FIND_SUBSCRIPTION = "select * from subscription where account_id = ? and subscriber_id = ?;";

    @Override
    public boolean isSubscribed(Long accountId, Long subscriberId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_SUBSCRIPTION)) {

            statement.setLong(1, accountId);
            statement.setLong(2, subscriberId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }

    //language=SQL
    public static final String SQL_DELETE_SUBSCRIPTION = "delete from subscription where account_id = ? and subscriber_id = ?;";

    @Override
    public void deleteSubscription(Long accountId, Long subscriberId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_SUBSCRIPTION)) {

            statement.setLong(1, accountId);
            statement.setLong(2, subscriberId);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e);
        }
    }
}
