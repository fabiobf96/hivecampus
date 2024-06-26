package it.hivecampuscompany.hivecampus.bean;

import it.hivecampuscompany.hivecampus.exception.EmptyFieldsException;
import it.hivecampuscompany.hivecampus.exception.InvalidEmailException;
import it.hivecampuscompany.hivecampus.exception.PasswordMismatchException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserBean {
    private String email;
    private String password;
    private String role;
    private static final String EMAIL_REGEX;

    static {
        EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    }
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public void setEmail(String email) throws InvalidEmailException {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new InvalidEmailException("INVALID_EMAIL");
        }
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNewPassword(String password, String confPass) throws PasswordMismatchException {
        if (!password.equals(confPass)){
            throw new PasswordMismatchException("PSW_MISMATCH");
        }
        this.password = password;
    }

    public void setRole(String role) throws EmptyFieldsException {
        if (role == null || role.isEmpty()) {
            throw new EmptyFieldsException("EMPTY_ROLE");
        }

        this.role = role;
    }
}
