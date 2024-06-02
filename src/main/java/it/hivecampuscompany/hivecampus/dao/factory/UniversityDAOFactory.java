package it.hivecampuscompany.hivecampus.dao.factory;

import it.hivecampuscompany.hivecampus.dao.UniversityDAO;
import it.hivecampuscompany.hivecampus.dao.csv.UniversityDAOCSV;
import it.hivecampuscompany.hivecampus.dao.facade.PersistenceType;
import it.hivecampuscompany.hivecampus.dao.mysql.UniversityDAOMySql;

public class UniversityDAOFactory implements Factory {
    @Override
    public UniversityDAO getDAO(PersistenceType persistenceType) {
        return switch (persistenceType) {
            case CSV -> createUniversityDAOCSV();
            case MYSQL -> createUniversityDAOMySql();
        };
    }

    public UniversityDAO createUniversityDAOCSV() {
        return new UniversityDAOCSV();
    }

    public UniversityDAO createUniversityDAOMySql() {
        return new UniversityDAOMySql();
    }
}
