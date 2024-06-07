package it.hivecampuscompany.hivecampus.dao.factory;

import it.hivecampuscompany.hivecampus.dao.UniversityDAO;
import it.hivecampuscompany.hivecampus.dao.csv.UniversityDAOCSV;
import it.hivecampuscompany.hivecampus.dao.facade.PersistenceType;
import it.hivecampuscompany.hivecampus.dao.mysql.UniversityDAOMySql;

/**
 * The UniversityDAOFactory class is a factory class responsible for creating
 * instances of the UniversityDAO interface based on the specified persistence type.
 *
 * @author Fabio Barchiesi
 */
public class UniversityDAOFactory {

    /**
     * Creates an instance of UniversityDAO based on the given persistence type.
     *
     * @param persistenceType the type of persistence storage (CSV or MYSQL)
     * @return an instance of UniversityDAO corresponding to the specified persistence type
     * @author Fabio Barchiesi
     */
    public UniversityDAO getUniversityDAO(PersistenceType persistenceType) {
        return switch (persistenceType) {
            case CSV -> createUniversityDAOCSV();
            case MYSQL -> createUniversityDAOMySql();
        };
    }

    /**
     * Creates an instance of UniversityDAOCSV, which is the CSV implementation of UniversityDAO.
     *
     * @return an instance of UniversityDAOCSV
     * @author Fabio Barchiesi
     */
    public UniversityDAO createUniversityDAOCSV() {
        return new UniversityDAOCSV();
    }

    /**
     * Creates an instance of UniversityDAOMySql, which is the MySQL implementation of UniversityDAO.
     *
     * @return an instance of UniversityDAOMySql
     * @author Fabio Barchiesi
     */
    public UniversityDAO createUniversityDAOMySql() {
        return new UniversityDAOMySql();
    }
}
