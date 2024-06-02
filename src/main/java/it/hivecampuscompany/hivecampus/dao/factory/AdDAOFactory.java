package it.hivecampuscompany.hivecampus.dao.factory;

import it.hivecampuscompany.hivecampus.dao.AdDAO;
import it.hivecampuscompany.hivecampus.dao.csv.AdDAOCSV;
import it.hivecampuscompany.hivecampus.dao.facade.PersistenceType;
import it.hivecampuscompany.hivecampus.dao.mysql.AdDAOMySql;

public class AdDAOFactory implements Factory {
    @Override
    public AdDAO getDAO(PersistenceType persistenceType) {
        return switch (persistenceType) {
            case CSV -> createAdDAOCSV();
            case MYSQL -> createAdDAOMySql();
        };
    }

    public AdDAO createAdDAOCSV() {
        return new AdDAOCSV();
    }

    public AdDAO createAdDAOMySql() {
        return new AdDAOMySql();
    }
}
