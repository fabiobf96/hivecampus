package it.hivecampuscompany.hivecampus.model;

import it.hivecampuscompany.hivecampus.view.utility.LanguageLoader;

import java.util.Properties;

public enum Month {
    JANUARY(1), FEBRUARY(2), MARCH(3), APRIL(4), MAY(5), JUNE(6), JULY(7), AUGUST(8), SEPTEMBER(9), OCTOBER(10), NOVEMBER(11), DECEMBER(12);
    private final int period;

    Month(int month) {
        this.period = month;
    }

    public static Month fromInt(int month) {
        for (Month type : values()) {
            if (type.getMonth() == month) {
                return type;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        Properties properties = LanguageLoader.getLanguageProperties();
        return switch (this) {
            case JANUARY -> properties.getProperty("JANUARY_MSG");
            case FEBRUARY -> properties.getProperty("FEBRUARY_MSG");
            case MARCH -> properties.getProperty("MARCH_MSG");
            case APRIL -> properties.getProperty("APRIL_MSG");
            case MAY -> properties.getProperty("MAY_MSG");
            case JUNE -> properties.getProperty("JUNE_MSG");
            case JULY -> properties.getProperty("JULY_MSG");
            case AUGUST -> properties.getProperty("AUGUST_MSG");
            case SEPTEMBER -> properties.getProperty("SEPTEMBER_MSG");
            case OCTOBER -> properties.getProperty("OCTOBER_MSG");
            case NOVEMBER -> properties.getProperty("NOVEMBER_MSG");
            case DECEMBER -> properties.getProperty("DECEMBER_MSG");
        };
    }

    public int getMonth() {
        return period;
    }
}
