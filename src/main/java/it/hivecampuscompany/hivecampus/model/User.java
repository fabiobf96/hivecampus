package it.hivecampuscompany.hivecampus.model;

import it.hivecampuscompany.hivecampus.bean.UserBean;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {
    private final String email;
    private final String password;
    private static final String ALGORITHM = "MD5";
    private final String role;

    public User(String email, String password, String role){
        this.email = email;
        this.password = password;
        this.role = role;
    }
    public User(UserBean userBean) throws NoSuchAlgorithmException{
        email = userBean.getEmail();
        password = hashPassword(userBean.getPassword());
        role = userBean.getRole();
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

    private String hashPassword(String password) throws NoSuchAlgorithmException{
        StringBuilder sb = new StringBuilder();
        MessageDigest md = MessageDigest.getInstance(ALGORITHM);
        byte[] hashedBytes = md.digest(password.getBytes());

        for (byte b : hashedBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
