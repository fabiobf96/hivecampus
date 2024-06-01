package it.hivecampuscompany.hivecampus.dao.factory;

import it.hivecampuscompany.hivecampus.dao.AdDAO;
import it.hivecampuscompany.hivecampus.dao.csv.AdDAOCSV;
import it.hivecampuscompany.hivecampus.dao.mysql.AdDAOMySql;

public class AdDAOFactory implements Factory {
    @Override
    public AdDAO getDAO(String typePersistence) throws IllegalArgumentException {
        return switch (typePersistence) {
            case "csv" -> createAdDAOCSV();
            case "mysql" -> createAdDAOMySql();
            default -> throw new IllegalArgumentException("Unsupported persistence type: " + typePersistence);
        };
    }

    public AdDAO createAdDAOCSV() {
        return new AdDAOCSV();
    }

    public AdDAO createAdDAOMySql() {
        return new AdDAOMySql();
    }
}
