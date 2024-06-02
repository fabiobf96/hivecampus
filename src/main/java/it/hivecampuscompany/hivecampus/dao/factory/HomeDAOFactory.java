package it.hivecampuscompany.hivecampus.dao.factory;

import it.hivecampuscompany.hivecampus.dao.HomeDAO;
import it.hivecampuscompany.hivecampus.dao.csv.HomeDAOCSV;
import it.hivecampuscompany.hivecampus.dao.facade.PersistenceType;
import it.hivecampuscompany.hivecampus.dao.mysql.HomeDAOMySql;

public class HomeDAOFactory implements Factory {
    public HomeDAO getDAO(PersistenceType persistenceType) {
        return switch (persistenceType) {
            case CSV -> createHomeDAOCSV();
            case MYSQL -> createHomeDAOMySql();
        };
    }

    public HomeDAO createHomeDAOCSV() {
        return new HomeDAOCSV();
    }

    public HomeDAO createHomeDAOMySql() {
        return new HomeDAOMySql();
    }
}
