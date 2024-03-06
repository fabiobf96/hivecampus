package it.hivecampuscompany.hivecampus.logic.model;

import it.hivecampuscompany.hivecampus.logic.bean.UserBean;
import it.hivecampuscompany.hivecampus.logic.dao.UserDAO;
import it.hivecampuscompany.hivecampus.logic.dao.csv.UserDAOCSV;
import it.hivecampuscompany.hivecampus.logic.exception.DuplicateRowException;

public class User {
    private String email;
    private String password;
    private String role;
    private UserDAO userDAO;

    public User(){
        userDAO = new UserDAOCSV();
    }
    public User(UserBean userBean){
        this();
        email = userBean.getEmail();
        password = userBean.getPassword();
        role = userBean.getRole();
    }

    public void saveUser() throws DuplicateRowException {
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
}
