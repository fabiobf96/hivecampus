package it.hivecampuscompany.hivecampus.bean;

import it.hivecampuscompany.hivecampus.model.Account;

public class AccountBean {
    private String email;
    private String name;
    private String surname;
    private String phoneNumber;

    public AccountBean(){
        // Default constructor
    }

    public AccountBean(Account account){
        this.email = account.getEmail();
        this.name = account.getName();
        this.surname = account.getSurname();
        this.phoneNumber = account.getPhoneNumber();
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

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDetails() {
        return  " - Name: " + name + '\n' +
                " - Surname: " + surname + '\n' +
                " - Email: " + email +  '\n' +
                " - Telephone: " + phoneNumber;
    }
}
