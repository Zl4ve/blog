package ru.itis.kpfu.hashers;

public interface PasswordHasher {
    String hash(String password);
}
