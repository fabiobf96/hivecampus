package it.hivecampuscompany.hivecampus.dao.factory;


import it.hivecampuscompany.hivecampus.dao.LeaseContractDAO;
import it.hivecampuscompany.hivecampus.dao.csv.LeaseContractDAOCSV;
import it.hivecampuscompany.hivecampus.dao.mysql.LeaseContractDAOMySql;

public class LeaseContractDAOFactory implements Factory {
    @Override
    public LeaseContractDAO getDAO(String typePersistence) throws IllegalArgumentException {
        return switch (typePersistence) {
            case "csv" -> createLeaseContractDAOCSV();
            case "mysql" -> createLeaseContractDAOMySql();
            default -> throw new IllegalArgumentException("Unsupported persistence type: " + typePersistence);
        };
    }

    public LeaseContractDAO createLeaseContractDAOCSV() {
        return new LeaseContractDAOCSV();
    }

    public LeaseContractDAO createLeaseContractDAOMySql() {
        return new LeaseContractDAOMySql();
    }

}
