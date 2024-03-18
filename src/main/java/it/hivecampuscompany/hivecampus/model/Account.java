package it.hivecampuscompany.hivecampus.model;

import it.hivecampuscompany.hivecampus.bean.AccountBean;
import it.hivecampuscompany.hivecampus.dao.AccountDAO;
import it.hivecampuscompany.hivecampus.dao.csv.AccountDAOCSV;

public class Account {
    private String email;
    private String name;
    private String surname;
    private String phoneNumber;
    private final AccountDAO accountDAO;
    public Account (){
        accountDAO = new AccountDAOCSV();
        //accountDAO = new AccountDAOMySql();
    }

    public Account(AccountBean accountBean){
        this();
        email = accountBean.getEmail();
        name = accountBean.getName();
        surname = accountBean.getSurname();
        phoneNumber = accountBean.getPhoneNumber();
    }

    public void createAccount(){
        accountDAO.saveAccount(this);
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
