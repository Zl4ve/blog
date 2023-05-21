package ru.itis.kpfu.services;

import ru.itis.kpfu.models.Post;
import ru.itis.kpfu.validators.SignUpDataErrorMessageGenerator;
import ru.itis.kpfu.hashers.PasswordHasher;
import ru.itis.kpfu.models.Account;
import ru.itis.kpfu.repositories.AccountsRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public class AccountServiceImpl implements AccountService {
    private AccountsRepository accountsRepository;

    private SignUpDataErrorMessageGenerator errorsGenerator;

    private PasswordHasher hasher;

    public AccountServiceImpl(AccountsRepository accountsRepository, SignUpDataErrorMessageGenerator errorsGenerator, PasswordHasher hasher) {
        this.accountsRepository = accountsRepository;
        this.errorsGenerator = errorsGenerator;
        this.hasher = hasher;
    }

    @Override
    public void signUp(Account account) {
        account.setPassword(hasher.hash(account.getPassword()));
        accountsRepository.save(account);
    }

    @Override
    public boolean isValid(Account account) {
        return errorsGenerator.generateUsernameErrorMessage(account.getUsername()) == null &&
                errorsGenerator.generatePasswordErrorMessage(account.getPassword()) == null &&
                errorsGenerator.generateEmailErrorMessage(account.getEmail()) == null;
    }

    @Override
    public boolean checkIfExistsByEmail(String email) {
        return accountsRepository.findByEmail(email).isPresent();
    }

    @Override
    public boolean checkIfExistsByUsername(String username) {
        return accountsRepository.findByUsername(username).isPresent();
    }

    @Override
    public Account findByUsernameAndPassword(String username, String password) {
        return accountsRepository.findByUsernameAndPassword(username, hasher.hash(password)).orElse(null);
    }

    @Override
    public Account getAccountById(Long id) {
        Optional<Account> account = accountsRepository.findById(id);
        return account.orElse(null);
    }

    @Override
    public void setSignUpErrors(HttpServletRequest request, Account account) {
        request.setAttribute("usernameError", errorsGenerator.generateUsernameErrorMessage(account.getUsername()));
        request.setAttribute("passwordError", errorsGenerator.generatePasswordErrorMessage(account.getPassword()));
        request.setAttribute("emailError", errorsGenerator.generateEmailErrorMessage(account.getEmail()));
    }

    @Override
    public void subscribe(Long accountId, Long subscriberId) {
        accountsRepository.saveSubscription(accountId, subscriberId);
    }

    @Override
    public List<Account> getSubscribers(Long accountId) {
        return accountsRepository.getSubscribers(accountId);
    }

    @Override
    public boolean isSubscribed(Long accountId, Long subscriberId) {
        return accountsRepository.isSubscribed(accountId, subscriberId);
    }

    @Override
    public void unsubscribe(Long accountId, Long subscriberId) {
        accountsRepository.deleteSubscription(accountId, subscriberId);
    }
}
