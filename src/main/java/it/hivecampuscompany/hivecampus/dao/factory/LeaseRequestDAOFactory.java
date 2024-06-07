package it.hivecampuscompany.hivecampus.dao.factory;


import it.hivecampuscompany.hivecampus.dao.LeaseRequestDAO;
import it.hivecampuscompany.hivecampus.dao.csv.LeaseRequestDAOCSV;
import it.hivecampuscompany.hivecampus.dao.facade.PersistenceType;
import it.hivecampuscompany.hivecampus.dao.mysql.LeaseRequestDAOMySql;

/**
 * The LeaseRequestDAOFactory class is a factory class responsible for creating
 * instances of the LeaseRequestDAO interface based on the specified persistence type.
 *
 * @author Fabio Barchiesi
 */
public class LeaseRequestDAOFactory {

    /**
     * Creates an instance of LeaseRequestDAO based on the given persistence type.
     *
     * @param persistenceType the type of persistence storage (CSV or MYSQL)
     * @return an instance of LeaseRequestDAO corresponding to the specified persistence type
     * @author Fabio Barchiesi
     */
    public LeaseRequestDAO getLeaseRequestDAO(PersistenceType persistenceType) {
        return switch (persistenceType) {
            case CSV -> createLeaseRequestDAOCSV();
            case MYSQL -> createLeaseRequestDAOMySql();
        };
    }

    /**
     * Creates an instance of LeaseRequestDAOCSV, which is the CSV implementation of LeaseRequestDAO.
     *
     * @return an instance of LeaseRequestDAOCSV
     * @author Fabio Barchiesi
     */
    public LeaseRequestDAO createLeaseRequestDAOCSV() {
        return new LeaseRequestDAOCSV();
    }

    /**
     * Creates an instance of LeaseRequestDAOMySql, which is the MySQL implementation of LeaseRequestDAO.
     *
     * @return an instance of LeaseRequestDAOMySql
     * @author Fabio Barchiesi
     */
    public LeaseRequestDAO createLeaseRequestDAOMySql() {
        return new LeaseRequestDAOMySql();
    }
}