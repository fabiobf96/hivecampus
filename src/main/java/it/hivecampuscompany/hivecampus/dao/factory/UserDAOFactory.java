package it.hivecampuscompany.hivecampus.dao.factory;

import it.hivecampuscompany.hivecampus.dao.UserDAO;
import it.hivecampuscompany.hivecampus.dao.csv.UserDAOCSV;
import it.hivecampuscompany.hivecampus.dao.facade.PersistenceType;
import it.hivecampuscompany.hivecampus.dao.mysql.UserDAOMySql;

/**
 * The UserDAOFactory class is a factory class responsible for creating
 * instances of the UserDAO interface based on the specified persistence type.
 *
 * @author Fabio Barchiesi
 */
public class UserDAOFactory {

    /**
     * Creates an instance of UserDAO based on the given persistence type.
     *
     * @param persistenceType the type of persistence storage (CSV or MYSQL)
     * @return an instance of UserDAO corresponding to the specified persistence type
     * @author Fabio Barchiesi
     */
    public UserDAO getUserDAO(PersistenceType persistenceType) {
        return switch (persistenceType) {
            case CSV -> createUserDAOCSV();
            case MYSQL -> createUserDAOMySql();
        };
    }

    /**
     * Creates an instance of UserDAOCSV, which is the CSV implementation of UserDAO.
     *
     * @return an instance of UserDAOCSV
     * @author Fabio Barchiesi
     */
    public UserDAO createUserDAOCSV() {
        return new UserDAOCSV();
    }

    /**
     * Creates an instance of UserDAOMySql, which is the MySQL implementation of UserDAO.
     *
     * @return an instance of UserDAOMySql
     * @author Fabio Barchiesi
     */
    public UserDAO createUserDAOMySql() {
        return new UserDAOMySql();
    }
}
