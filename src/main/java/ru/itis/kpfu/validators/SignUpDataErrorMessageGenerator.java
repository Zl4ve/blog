package ru.itis.kpfu.validators;

public class SignUpDataErrorMessageGenerator {

    private final String USERNAME_REGEX = "^[a-zA-Z0-9_-]*$";

    private final String PASSWORD_REGEX = "^[a-zA-Z0-9_.!@#$%^&*()-]*$";

    private final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

    public String generateUsernameErrorMessage(String username) {
        if (username == null || username.equals("")) {
            return "Enter a username";
        } else if (username.length() < 3 || username.length() > 35) {
            return "Username length must be greater than 3 and less than 35 characters";
        } else if (!username.matches(USERNAME_REGEX)) {
            return "Username must contain only uppercase and lowercase letters, numbers, _ and - characters";
        }
        return null;
    }

    public String generatePasswordErrorMessage(String password) {
        if (password == null || password.equals("")) {
            return "Enter a password";
        } else if (password.length() < 6 || password.length() > 30) {
            return "Password length must be greater than 6 and less than 30 characters";
        } else if (!password.matches(PASSWORD_REGEX)) {
            return "Password must contain only uppercase and lowercase letters, numbers and .!@#$%^&*() characters";
        }
        return null;
    }

    public String generateEmailErrorMessage(String email) {
        if (email == null || email.equals("")) {
            return "Enter email";
        } else if (!email.matches(EMAIL_REGEX)) {
            return "Incorrect email";
        }
        return null;
    }
}
