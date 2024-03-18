package it.hivecampuscompany.hivecampus.model;

import java.util.List;

public class Room {
    private int idHome;
    private int idRoom;
    private float price;
    private int surface;
    private String type;
    private boolean bathroom;
    private boolean balcony;
    private boolean conditioner;
    private boolean tv;
    private boolean available;
    private String start;
    private String description;
    private List<LeaseRequest> leaseRequestList;
}
