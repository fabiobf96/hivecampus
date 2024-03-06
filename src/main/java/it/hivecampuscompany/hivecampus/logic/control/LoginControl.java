package it.hivecampuscompany.hivecampus.logic.control;

import it.hivecampuscompany.hivecampus.logic.bean.AccountBean;
import it.hivecampuscompany.hivecampus.logic.bean.UserBean;
import it.hivecampuscompany.hivecampus.logic.dao.AccountDAO;
import it.hivecampuscompany.hivecampus.logic.dao.UserDAO;
import it.hivecampuscompany.hivecampus.logic.dao.csv.AccountDAOCSV;
import it.hivecampuscompany.hivecampus.logic.dao.csv.UserDAOCSV;
import it.hivecampuscompany.hivecampus.logic.exception.DuplicateRowException;
import it.hivecampuscompany.hivecampus.logic.model.Account;
import it.hivecampuscompany.hivecampus.logic.model.User;
import it.hivecampuscompany.hivecampus.logic.utility.EncryptionPassword;

import java.security.NoSuchAlgorithmException;

public class LoginControl {
    private final UserDAO userDAO;
    private final AccountDAO accountDAO;
    public LoginControl(){
        userDAO = new UserDAOCSV();
        accountDAO = new AccountDAOCSV();
    }
    public void signup(UserBean userBean, AccountBean accountBean) throws DuplicateRowException {
        try {
            userBean.setPassword(EncryptionPassword.hashPasswordMD5(userBean.getPassword()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        User user = new User(userBean);
        user.saveUser();
        Account account = new Account(accountBean);
        account.saveAccount();
    }
    
}
