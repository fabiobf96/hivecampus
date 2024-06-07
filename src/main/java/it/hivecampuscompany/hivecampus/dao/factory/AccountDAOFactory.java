package it.hivecampuscompany.hivecampus.dao.factory;


import it.hivecampuscompany.hivecampus.dao.AccountDAO;
import it.hivecampuscompany.hivecampus.dao.csv.AccountDAOCSV;
import it.hivecampuscompany.hivecampus.dao.facade.PersistenceType;
import it.hivecampuscompany.hivecampus.dao.mysql.AccountDAOMySql;

/**
 * The AccountDAOFactory class is a factory class responsible for creating
 * instances of the AccountDAO interface based on the specified persistence type.
 */
public class AccountDAOFactory {

    /**
     * Creates an instance of AccountDAO based on the given persistence type.
     *
     * @param persistenceType the type of persistence storage (CSV or MYSQL)
     * @return an instance of AccountDAO corresponding to the specified persistence type
     * @author Fabio Barchiesi
     */
    public AccountDAO getAccountDAO(PersistenceType persistenceType) {
        return switch (persistenceType) {
            case CSV -> createAccountDAOCSV();
            case MYSQL -> createAccountDAOMySql();
        };
    }

    /**
     * Creates an instance of AccountDAOCSV, which is the CSV implementation of AccountDAO.
     *
     * @return an instance of AccountDAOCSV
     * @author Fabio Barchiesi
     */
    public AccountDAO createAccountDAOCSV() {
        return new AccountDAOCSV();
    }

    /**
     * Creates an instance of AccountDAOMySql, which is the MySQL implementation of AccountDAO.
     *
     * @return an instance of AccountDAOMySql
     * @author Fabio Barchiesi
     */
    public AccountDAO createAccountDAOMySql() {
        return new AccountDAOMySql();
    }
}
