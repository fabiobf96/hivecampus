package it.hivecampuscompany.hivecampus.dao.factory;

import it.hivecampuscompany.hivecampus.dao.UniversityDAO;
import it.hivecampuscompany.hivecampus.dao.csv.UniversityDAOCSV;
import it.hivecampuscompany.hivecampus.dao.mysql.UniversityDAOMySql;

public class UniversityDAOFactory implements Factory {
    @Override
    public UniversityDAO getDAO(String typePersistence) throws IllegalArgumentException {
        return switch (typePersistence) {
            case "csv" -> createUniversityDAOCSV();
            case "mysql" -> createUniversityDAOMySql();
            default -> throw new IllegalArgumentException("Unsupported persistence type: " + typePersistence);
        };
    }

    public UniversityDAO createUniversityDAOCSV() {
        return new UniversityDAOCSV();
    }

    public UniversityDAO createUniversityDAOMySql() {
        return new UniversityDAOMySql();
    }
}
