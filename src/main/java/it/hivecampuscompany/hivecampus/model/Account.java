package it.hivecampuscompany.hivecampus.model;

import it.hivecampuscompany.hivecampus.bean.AccountBean;

public class Account {
    private final String email;
    private final String name;
    private final String surname;
    private final String phoneNumber;

    public Account(AccountBean accountBean){
        email = accountBean.getEmail();
        name = accountBean.getName();
        surname = accountBean.getSurname();
        phoneNumber = accountBean.getPhoneNumber();
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
