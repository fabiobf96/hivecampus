package it.hivecampuscompany.hivecampus.manager;

import it.hivecampuscompany.hivecampus.bean.AccountBean;
import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.bean.UserBean;
import it.hivecampuscompany.hivecampus.dao.AccountDAO;
import it.hivecampuscompany.hivecampus.dao.UserDAO;
import it.hivecampuscompany.hivecampus.dao.csv.AccountDAOCSV;
import it.hivecampuscompany.hivecampus.dao.csv.UserDAOCSV;
import it.hivecampuscompany.hivecampus.dao.mysql.AccountDAOMySql;
import it.hivecampuscompany.hivecampus.dao.mysql.UserDAOMySql;
import it.hivecampuscompany.hivecampus.exception.AuthenticateException;
import it.hivecampuscompany.hivecampus.exception.DuplicateRowException;
import it.hivecampuscompany.hivecampus.model.Account;
import it.hivecampuscompany.hivecampus.model.Session;
import it.hivecampuscompany.hivecampus.model.User;

import java.security.NoSuchAlgorithmException;

public class LoginManager {
    public void signup(UserBean userBean, AccountBean accountBean) throws DuplicateRowException, NoSuchAlgorithmException {
        AccountDAO accountDAO = new AccountDAOMySql(); // AccountDAOMySql() or AccountDAOCSV()
        UserDAO userDAO = new UserDAOMySql(); // UserDAOMySql() or UserDAOCSV()

        User user = new User(userBean);
        userDAO.saveUser(user);

        Account account = new Account(accountBean);
        accountDAO.saveAccount(account);
    }

    public SessionBean login(UserBean userBean) throws AuthenticateException, NoSuchAlgorithmException {
        UserDAO userDAO = new UserDAOMySql(); // UserDAOMySql() or UserDAOCSV()

        User user = userDAO.verifyCredentials(new User(userBean));

        SessionManager sessionManager = SessionManager.getInstance();
        Session session = sessionManager.createSession(user);
        return new SessionBean(session);
    }
}
