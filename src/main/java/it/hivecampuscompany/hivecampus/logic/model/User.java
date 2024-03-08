package it.hivecampuscompany.hivecampus.logic.model;

import it.hivecampuscompany.hivecampus.logic.bean.UserBean;
import it.hivecampuscompany.hivecampus.logic.dao.UserDAO;
import it.hivecampuscompany.hivecampus.logic.dao.csv.UserDAOCSV;
import it.hivecampuscompany.hivecampus.logic.exception.DuplicateRowException;
import it.hivecampuscompany.hivecampus.logic.exception.InvalidEmailException;
import it.hivecampuscompany.hivecampus.logic.exception.PasswordMismatchException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {
    private final String email;
    private final String password;
    private String role;
    private UserDAO userDAO;

    public User(String email, String password, String role){
        this.email = email;
        this.password = password;
        this.role = role;
    }
    public User(UserBean userBean){
        email = userBean.getEmail();
        try {
            password = hashPasswordMD5(userBean.getPassword());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        role = userBean.getRole();
        userDAO = new UserDAOCSV();
    }

    public void createUser() throws DuplicateRowException {
        userDAO.saveUser(this);
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

    public void verifyCredentials() throws InvalidEmailException, PasswordMismatchException {
        User storedUser = userDAO.retrieveUserByEmail(email);
        if (storedUser.getPassword().equals(password)){
            role = storedUser.getRole();
        }
        else {
            throw new PasswordMismatchException("");
        }
    }

    private String hashPasswordMD5(String password) throws NoSuchAlgorithmException{
        StringBuilder sb = new StringBuilder();
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hashedBytes = md.digest(password.getBytes());

        for (byte b : hashedBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
