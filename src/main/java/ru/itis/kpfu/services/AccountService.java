package ru.itis.kpfu.services;

import ru.itis.kpfu.models.Account;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public interface AccountService {
    void signUp(Account account);

    boolean isValid(Account account);

    boolean checkIfExistsByEmail(String email);

    boolean checkIfExistsByUsername(String username);

    Account findByUsernameAndPassword(String username, String password);

    void setSignUpErrors(HttpServletRequest request, Account account);

    Account getAccountById(Long id);

    void subscribe(Long accountId, Long subscriberId);

    List<Account> getSubscribers(Long accountId);

    boolean isSubscribed(Long accountId, Long subscriberId);

    void unsubscribe(Long accountId, Long subscriberId);
}
