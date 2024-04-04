package it.hivecampuscompany.hivecampus.model;

public enum LeaseRequestStatus {
    ACCEPTED(1), PROCESSING(2), REJECTED(3);

    private final int id;

    LeaseRequestStatus(int id) {
        this.id = id;
    }

    public static LeaseRequestStatus fromInt(int id) {
        for (LeaseRequestStatus type : values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        return null;
    }

    public int getId() {
        return id;
    }
}
