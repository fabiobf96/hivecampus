package it.hivecampuscompany.hivecampus.dao.factory;


import it.hivecampuscompany.hivecampus.dao.LeaseContractDAO;
import it.hivecampuscompany.hivecampus.dao.csv.LeaseContractDAOCSV;
import it.hivecampuscompany.hivecampus.dao.facade.PersistenceType;
import it.hivecampuscompany.hivecampus.dao.mysql.LeaseContractDAOMySql;

/**
 * The LeaseContractDAOFactory class is a factory class responsible for creating
 * instances of the LeaseContractDAO interface based on the specified persistence type.
 *
 * @author Fabio Barchiesi
 */
public class LeaseContractDAOFactory {

    /**
     * Creates an instance of LeaseContractDAO based on the given persistence type.
     *
     * @param persistenceType the type of persistence storage (CSV or MYSQL)
     * @return an instance of LeaseContractDAO corresponding to the specified persistence type
     * @author Fabio Barchiesi
     */
    public LeaseContractDAO getLeaseContractDAO(PersistenceType persistenceType) {
        return switch (persistenceType) {
            case CSV -> createLeaseContractDAOCSV();
            case MYSQL -> createLeaseContractDAOMySql();
        };
    }

    /**
     * Creates an instance of LeaseContractDAOCSV, which is the CSV implementation of LeaseContractDAO.
     *
     * @return an instance of LeaseContractDAOCSV
     * @author Fabio Barchiesi
     */
    public LeaseContractDAO createLeaseContractDAOCSV() {
        return new LeaseContractDAOCSV();
    }

    /**
     * Creates an instance of LeaseContractDAOMySql, which is the MySQL implementation of LeaseContractDAO.
     *
     * @return an instance of LeaseContractDAOMySql
     */
    public LeaseContractDAO createLeaseContractDAOMySql() {
        return new LeaseContractDAOMySql();
    }
}

