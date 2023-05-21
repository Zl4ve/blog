package ru.itis.kpfu.repositories;

import ru.itis.kpfu.models.Account;

import java.util.List;
import java.util.Optional;

public interface AccountsRepository {
    void save(Account account);

    Optional<Account> findByUsername(String username);

    Optional<Account> findByEmail(String email);

    Optional<Account> findByUsernameAndPassword(String username, String password);

    Optional<Account> findById(Long id);

    void saveSubscription(Long accountId, Long subscriberId);

    List<Account> getSubscribers(Long accountId);

    boolean isSubscribed(Long accountId, Long subscriberId);

    void deleteSubscription(Long accountId, Long subscriberId);
}
