package it.hivecampuscompany.hivecampus.dao.factory;

import it.hivecampuscompany.hivecampus.dao.HomeDAO;
import it.hivecampuscompany.hivecampus.dao.csv.HomeDAOCSV;
import it.hivecampuscompany.hivecampus.dao.mysql.HomeDAOMySql;

public class HomeDAOFactory implements Factory {
    public HomeDAO getDAO(String typePersistence) throws IllegalArgumentException {
        return switch (typePersistence) {
            case "csv" -> createHomeDAOCSV();
            case "mysql" -> createHomeDAOMySql();
            default -> throw new IllegalArgumentException("Unsupported persistence type: " + typePersistence);
        };
    }

    public HomeDAO createHomeDAOCSV() {
        return new HomeDAOCSV();
    }

    public HomeDAO createHomeDAOMySql() {
        return new HomeDAOMySql();
    }
}
