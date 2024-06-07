package it.hivecampuscompany.hivecampus.dao.factory;

import it.hivecampuscompany.hivecampus.dao.HomeDAO;
import it.hivecampuscompany.hivecampus.dao.csv.HomeDAOCSV;
import it.hivecampuscompany.hivecampus.dao.facade.PersistenceType;
import it.hivecampuscompany.hivecampus.dao.mysql.HomeDAOMySql;

/**
 * The HomeDAOFactory class is a factory class responsible for creating
 * instances of the HomeDAO interface based on the specified persistence type.
 *
 * @author Fabio Barchiesi
 */
public class HomeDAOFactory {

    /**
     * Creates an instance of HomeDAO based on the given persistence type.
     *
     * @param persistenceType the type of persistence storage (CSV or MYSQL)
     * @return an instance of HomeDAO corresponding to the specified persistence type
     * @author Fabio Barchiesi
     */
    public HomeDAO getHomeDAO(PersistenceType persistenceType) {
        return switch (persistenceType) {
            case CSV -> createHomeDAOCSV();
            case MYSQL -> createHomeDAOMySql();
        };
    }

    /**
     * Creates an instance of HomeDAOCSV, which is the CSV implementation of HomeDAO.
     *
     * @return an instance of HomeDAOCSV
     * @author Fabio Barchiesi
     */
    public HomeDAO createHomeDAOCSV() {
        return new HomeDAOCSV();
    }

    /**
     * Creates an instance of HomeDAOMySql, which is the MySQL implementation of HomeDAO.
     *
     * @return an instance of HomeDAOMySql
     * @author Fabio Barchiesi
     */
    public HomeDAO createHomeDAOMySql() {
        return new HomeDAOMySql();
    }
}