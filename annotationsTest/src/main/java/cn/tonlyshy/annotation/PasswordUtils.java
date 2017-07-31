package cn.tonlyshy.annotation;

import java.util.List;

/**
 * Created by liaowm5 on 17/7/31.
 */
public class PasswordUtils {
    @UseCase(id = 1, description = "validatePassword")
    public boolean validatePassword(String password) {
        return (password.matches("\\w*\\d\\w*"));
    }

    @UseCase(id = 2, description = "encryptPasswrod")
    public String encryptPasswrod(String password) {
        return new StringBuilder(password).reverse().toString();
    }

    @UseCase(id = 3, description = "checkForNewPassword")
    public boolean checkForNewPassword(List<String> prevPasswords, String password) {
        return !prevPasswords.contains(password);
    }
}
