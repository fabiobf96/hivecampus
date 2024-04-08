package it.hivecampuscompany.hivecampus.model;

public enum AdStart {
    JANUARY(1), FEBRUARY(2), MARCH(3), APRIL(4), MAY(5), JUNE(6), JULY(7), AUGUST(8), SEPTEMBER(9), OCTOBER(10), NOVEMBER(11), DECEMBER(12);
    private final int month;

    AdStart(int month) {
        this.month = month;
    }

    public static AdStart fromInt(int month) {
        for (AdStart type : values()) {
            if (type.getMonth() == month) {
                return type;
            }
        }
        return null;
    }

    public int getMonth() {
        return month;
    }
}
