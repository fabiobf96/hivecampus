package it.hivecampuscompany.hivecampus.logic.model;

import it.hivecampuscompany.hivecampus.logic.bean.UserBean;
import it.hivecampuscompany.hivecampus.logic.dao.UserDAO;
import it.hivecampuscompany.hivecampus.logic.dao.csv.UserDAOCSV;
import it.hivecampuscompany.hivecampus.logic.exception.DuplicateRowException;
import it.hivecampuscompany.hivecampus.logic.exception.InvalidEmailException;
import it.hivecampuscompany.hivecampus.logic.exception.PasswordMismatchException;

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
        password = userBean.getPassword();
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
}
