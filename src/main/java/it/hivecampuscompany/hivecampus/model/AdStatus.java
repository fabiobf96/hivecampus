package it.hivecampuscompany.hivecampus.model;

public enum AdStatus {
    AVAILABLE(1), PROCESSING(2), RESERVED(3), LEASED(4), UNAVAILABLE(5);

    private final int id;

    AdStatus(int id) {
        this.id = id;
    }

    public static AdStatus fromInt(int id) {
        for (AdStatus type : values()) {
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