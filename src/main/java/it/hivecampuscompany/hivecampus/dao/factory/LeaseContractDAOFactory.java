package it.hivecampuscompany.hivecampus.dao.factory;


import it.hivecampuscompany.hivecampus.dao.LeaseContractDAO;
import it.hivecampuscompany.hivecampus.dao.csv.LeaseContractDAOCSV;
import it.hivecampuscompany.hivecampus.dao.facade.PersistenceType;
import it.hivecampuscompany.hivecampus.dao.mysql.LeaseContractDAOMySql;

public class LeaseContractDAOFactory implements Factory {
    @Override
    public LeaseContractDAO getDAO(PersistenceType persistenceType) {
        return switch (persistenceType) {
            case CSV -> createLeaseContractDAOCSV();
            case MYSQL -> createLeaseContractDAOMySql();
        };
    }

    public LeaseContractDAO createLeaseContractDAOCSV() {
        return new LeaseContractDAOCSV();
    }

    public LeaseContractDAO createLeaseContractDAOMySql() {
        return new LeaseContractDAOMySql();
    }

}
