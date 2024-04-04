package it.hivecampuscompany.hivecampus.model;

public enum AdStatus {
    AVAILABLE(1), PROCESSING(2), LEASED(3), UNAVAILABLE(4);

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
