package it.hivecampuscompany.hivecampus.bean;

public class AccountBean {
    private String email;
    private String name;
    private String surname;
    private String phoneNumber;

    public AccountBean() {
    }

    public AccountBean(String email, String name, String surname, String phoneNumber) {
        setEmail(email);
        setName(name);
        setSurname(surname);
        setPhoneNumber(phoneNumber);
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
    @Override
    public String toString() {
        return email + ", " + name + ", " + surname + ", " + phoneNumber;
    }
}
