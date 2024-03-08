package it.hivecampuscompany.hivecampus.logic.manager;

import it.hivecampuscompany.hivecampus.logic.bean.AccountBean;
import it.hivecampuscompany.hivecampus.logic.bean.SessionBean;
import it.hivecampuscompany.hivecampus.logic.bean.UserBean;
import it.hivecampuscompany.hivecampus.logic.exception.DuplicateRowException;
import it.hivecampuscompany.hivecampus.logic.exception.InvalidEmailException;
import it.hivecampuscompany.hivecampus.logic.exception.PasswordMismatchException;
import it.hivecampuscompany.hivecampus.logic.model.Account;
import it.hivecampuscompany.hivecampus.logic.model.Session;
import it.hivecampuscompany.hivecampus.logic.model.User;
import it.hivecampuscompany.hivecampus.logic.utility.EncryptionPassword;

public class LoginManager {
    public void signup(UserBean userBean, AccountBean accountBean) throws DuplicateRowException {
        userBean.setPassword(EncryptionPassword.hashPasswordMD5(userBean.getPassword()));
        User user = new User(userBean);
        user.createUser();
        Account account = new Account(accountBean);
        account.createAccount();
    }

    public SessionBean login(UserBean userBean) throws InvalidEmailException, PasswordMismatchException {

        userBean.setPassword(EncryptionPassword.hashPasswordMD5(userBean.getPassword()));
        User user = new User(userBean);
        user.verifyCredentials();
        SessionManager sessionManager = SessionManager.getInstance();
        Session session = sessionManager.createSession(user);
        return new SessionBean(session);
    }
}
