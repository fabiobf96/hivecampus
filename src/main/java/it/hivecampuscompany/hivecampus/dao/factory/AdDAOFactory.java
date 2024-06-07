package it.hivecampuscompany.hivecampus.dao.factory;

import it.hivecampuscompany.hivecampus.dao.AdDAO;
import it.hivecampuscompany.hivecampus.dao.csv.AdDAOCSV;
import it.hivecampuscompany.hivecampus.dao.facade.PersistenceType;
import it.hivecampuscompany.hivecampus.dao.mysql.AdDAOMySql;

/**
 * The AdDAOFactory class is a factory class responsible for creating
 * instances of the AdDAO interface based on the specified persistence type.
 */
public class AdDAOFactory {

    /**
     * Creates an instance of AdDAO based on the given persistence type.
     *
     * @param persistenceType the type of persistence storage (CSV or MYSQL)
     * @return an instance of AdDAO corresponding to the specified persistence type
     * @author Fabio Barchiesi
     */
    public AdDAO getAdDAO(PersistenceType persistenceType) {
        return switch (persistenceType) {
            case CSV -> createAdDAOCSV();
            case MYSQL -> createAdDAOMySql();
        };
    }

    /**
     * Creates an instance of AdDAOCSV, which is the CSV implementation of AdDAO.
     *
     * @return an instance of AdDAOCSV
     * @author Fabio Barchiesi
     */
    public AdDAO createAdDAOCSV() {
        return new AdDAOCSV();
    }

    /**
     * Creates an instance of AdDAOMySql, which is the MySQL implementation of AdDAO.
     *
     * @return an instance of AdDAOMySql
     * @author Fabio Barchiesi
     */
    public AdDAO createAdDAOMySql() {
        return new AdDAOMySql();
    }
}
