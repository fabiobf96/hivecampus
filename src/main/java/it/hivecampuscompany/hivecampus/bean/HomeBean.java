package it.hivecampuscompany.hivecampus.bean;

public class HomeBean {
    private final int id;
    private final String address;
    public HomeBean(int id, String address) {
        this.id = id;
        this.address = address;
    }

    public int getId() {
        return id;
    }
    @Override
    public String toString(){
        return address;
    }
}
