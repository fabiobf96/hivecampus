package it.hivecampuscompany.hivecampus.dao.factory;


import it.hivecampuscompany.hivecampus.dao.AccountDAO;
import it.hivecampuscompany.hivecampus.dao.csv.AccountDAOCSV;
import it.hivecampuscompany.hivecampus.dao.facade.PersistenceType;
import it.hivecampuscompany.hivecampus.dao.mysql.AccountDAOMySql;

public class AccountDAOFactory implements Factory {
    @Override
    public AccountDAO getDAO(PersistenceType persistenceType) {
        return switch (persistenceType) {
            case CSV -> createAccountDAOCSV();
            case MYSQL -> createAccountDAOMySql();
        };
    }

    public AccountDAO createAccountDAOCSV() {
        return new AccountDAOCSV();
    }

    public AccountDAO createAccountDAOMySql() {
        return new AccountDAOMySql();
    }
}
