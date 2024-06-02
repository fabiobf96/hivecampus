package it.hivecampuscompany.hivecampus.dao.factory;


import it.hivecampuscompany.hivecampus.dao.LeaseRequestDAO;
import it.hivecampuscompany.hivecampus.dao.csv.LeaseRequestDAOCSV;
import it.hivecampuscompany.hivecampus.dao.facade.PersistenceType;
import it.hivecampuscompany.hivecampus.dao.mysql.LeaseRequestDAOMySql;

public class LeaseRequestDAOFactory implements Factory {
    @Override
    public LeaseRequestDAO getDAO(PersistenceType persistenceType) {
        return switch (persistenceType) {
            case CSV -> createLeaseRequestDAOCSV();
            case MYSQL -> createLeaseRequestDAOMySql();
        };
    }

    public LeaseRequestDAO createLeaseRequestDAOCSV() {
        return new LeaseRequestDAOCSV();
    }

    public LeaseRequestDAO createLeaseRequestDAOMySql() {
        return new LeaseRequestDAOMySql();
    }

}
