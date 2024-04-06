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
    public Account(String email, String name, String surname, String phoneNumber) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
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

    public AccountBean toBasicBean() {
        return new AccountBean(email, name, surname, phoneNumber);
    }
}
