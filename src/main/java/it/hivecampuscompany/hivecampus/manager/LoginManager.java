package it.hivecampuscompany.hivecampus.manager;

import it.hivecampuscompany.hivecampus.bean.AccountBean;
import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.bean.UserBean;
import it.hivecampuscompany.hivecampus.dao.AccountDAO;
import it.hivecampuscompany.hivecampus.dao.UserDAO;
import it.hivecampuscompany.hivecampus.dao.facade.DAOFactoryFacade;
import it.hivecampuscompany.hivecampus.dao.mysql.AccountDAOMySql;
import it.hivecampuscompany.hivecampus.exception.AuthenticateException;
import it.hivecampuscompany.hivecampus.exception.DuplicateRowException;
import it.hivecampuscompany.hivecampus.model.Account;
import it.hivecampuscompany.hivecampus.model.Session;
import it.hivecampuscompany.hivecampus.model.User;

import java.security.NoSuchAlgorithmException;

public class LoginManager {
    /**
     * Signs up a new user and creates an account.
     *
     * @param userBean    The user bean containing the user information.
     * @param accountBean The account bean containing the account information.
     * @throws DuplicateRowException    If attempting to create a user or account that already exists.
     * @throws NoSuchAlgorithmException If an algorithm required for password hashing is not available.
     * @author Fabio Barchiesi
     */
    public void signup(UserBean userBean, AccountBean accountBean) throws DuplicateRowException, NoSuchAlgorithmException {
        DAOFactoryFacade daoFactoryFacade = DAOFactoryFacade.getInstance();
        AccountDAO accountDAO = daoFactoryFacade.getAccountDAO();
        UserDAO userDAO = daoFactoryFacade.getUserDAO();

        User user = new User(userBean);
        userDAO.saveUser(user);

        Account account = new Account(accountBean);
        accountDAO.saveAccount(account);
    }

    /**
     * Logs in a user and creates a session.
     *
     * @param userBean The user bean containing the user credentials.
     * @return A session bean representing the user's session.
     * @throws AuthenticateException    If the user authentication fails.
     * @throws NoSuchAlgorithmException If an algorithm required for password hashing is not available.
     * @author Fabio Barchiesi
     */
    public SessionBean login(UserBean userBean) throws AuthenticateException, NoSuchAlgorithmException {
        DAOFactoryFacade daoFactoryFacade = DAOFactoryFacade.getInstance();
        UserDAO userDAO = daoFactoryFacade.getUserDAO();

        User user = userDAO.verifyCredentials(new User(userBean));

        SessionManager sessionManager = SessionManager.getInstance();
        Session session = sessionManager.createSession(user);
        return new SessionBean(session);
    }


    /**
     * Method to retrieve the account information of the user.
     * It uses the email of the user to retrieve the account information.
     * Then it converts the account information into a bean.
     *
     * @param sessionBean The session of the user.
     * @return The account information of the user.
     * @author Marina Sotiropoulos
     */

    public AccountBean getAccountInfo(SessionBean sessionBean) {
        AccountDAO accountDAO = new AccountDAOMySql(); // AccountDAOMySql() or AccountDAOCSV()
        Account account = accountDAO.retrieveAccountInformationByEmail(sessionBean.getEmail());
        return account.toBean();
    }
}
