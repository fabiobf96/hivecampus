package it.hivecampuscompany.hivecampus.model;

import it.hivecampuscompany.hivecampus.bean.UserBean;
import it.hivecampuscompany.hivecampus.dao.UserDAO;
import it.hivecampuscompany.hivecampus.dao.csv.UserDAOCSV;
import it.hivecampuscompany.hivecampus.exception.DuplicateRowException;
import it.hivecampuscompany.hivecampus.exception.InvalidEmailException;
import it.hivecampuscompany.hivecampus.exception.PasswordMismatchException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {
    private final String email;
    private final String password;
    private static final String ALGORITHM = "MD5";
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
            password = hashPassword(userBean.getPassword());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        role = userBean.getRole();
        userDAO = new UserDAOCSV();
        //userDAO = new UserDAOMySql();
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
            throw new PasswordMismatchException("WRONG_PSW");
        }
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
