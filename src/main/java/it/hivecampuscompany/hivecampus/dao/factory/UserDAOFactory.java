package it.hivecampuscompany.hivecampus.dao.factory;

import it.hivecampuscompany.hivecampus.dao.UserDAO;
import it.hivecampuscompany.hivecampus.dao.csv.UserDAOCSV;
import it.hivecampuscompany.hivecampus.dao.mysql.UserDAOMySql;

public class UserDAOFactory implements Factory {
    @Override
    public UserDAO getDAO(String typePersistence) throws IllegalArgumentException {
        return switch (typePersistence) {
            case "csv" -> createUserDAOCSV();
            case "mysql" -> createUserDAOMySql();
            default -> throw new IllegalArgumentException("Unsupported persistence type: " + typePersistence);
        };
    }

    public UserDAO createUserDAOCSV() {
        return new UserDAOCSV();
    }

    public UserDAO createUserDAOMySql() {
        return new UserDAOMySql();
    }
}
