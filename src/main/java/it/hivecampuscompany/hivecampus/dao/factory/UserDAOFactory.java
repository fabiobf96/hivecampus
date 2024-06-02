package it.hivecampuscompany.hivecampus.dao.factory;

import it.hivecampuscompany.hivecampus.dao.UserDAO;
import it.hivecampuscompany.hivecampus.dao.csv.UserDAOCSV;
import it.hivecampuscompany.hivecampus.dao.facade.PersistenceType;
import it.hivecampuscompany.hivecampus.dao.mysql.UserDAOMySql;

public class UserDAOFactory implements Factory {
    @Override
    public UserDAO getDAO(PersistenceType persistenceType) throws IllegalArgumentException {
        return switch (persistenceType) {
            case CSV -> createUserDAOCSV();
            case MYSQL -> createUserDAOMySql();
        };
    }

    public UserDAO createUserDAOCSV() {
        return new UserDAOCSV();
    }

    public UserDAO createUserDAOMySql() {
        return new UserDAOMySql();
    }
}
