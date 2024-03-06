package it.hivecampuscompany.hivecampus.control;

import it.hivecampuscompany.hivecampus.bean.AccountBean;
import it.hivecampuscompany.hivecampus.bean.UserBean;
import it.hivecampuscompany.hivecampus.dao.AccountDAO;
import it.hivecampuscompany.hivecampus.dao.UserDAO;
import it.hivecampuscompany.hivecampus.dao.csv.AccountDAOCSV;
import it.hivecampuscompany.hivecampus.dao.csv.UserDAOCSV;
import it.hivecampuscompany.hivecampus.exception.DuplicateRowException;
import it.hivecampuscompany.hivecampus.model.Account;
import it.hivecampuscompany.hivecampus.model.User;
import it.hivecampuscompany.hivecampus.utility.EncryptionPassword;

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
