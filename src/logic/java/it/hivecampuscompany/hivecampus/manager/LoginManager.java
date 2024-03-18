package it.hivecampuscompany.hivecampus.manager;

import it.hivecampuscompany.hivecampus.bean.AccountBean;
import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.bean.UserBean;
import it.hivecampuscompany.hivecampus.exception.DuplicateRowException;
import it.hivecampuscompany.hivecampus.exception.InvalidEmailException;
import it.hivecampuscompany.hivecampus.exception.PasswordMismatchException;
import it.hivecampuscompany.hivecampus.model.Account;
import it.hivecampuscompany.hivecampus.model.Session;
import it.hivecampuscompany.hivecampus.model.User;

public class LoginManager {
    public void signup(UserBean userBean, AccountBean accountBean) throws DuplicateRowException {

        User user = new User(userBean);
        user.createUser();
        Account account = new Account(accountBean);
        account.createAccount();

    }

    public SessionBean login(UserBean userBean) throws InvalidEmailException, PasswordMismatchException {
        User user = new User(userBean);
        user.verifyCredentials();
        SessionManager sessionManager = SessionManager.getInstance();
        Session session = sessionManager.createSession(user);
        return new SessionBean(session);
    }

}
