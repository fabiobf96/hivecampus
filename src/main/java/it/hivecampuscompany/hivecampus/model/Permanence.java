package it.hivecampuscompany.hivecampus.model;


import it.hivecampuscompany.hivecampus.state.utility.LanguageLoader;

import java.util.Properties;

public enum Permanence {
    SIX_MONTHS(6), TWELVE_MONTHS(12), TWENTY_FOUR_MONTHS(24), THIRTY_SIX_MONTHS(36);
    private final int duration;

    Permanence(int duration) {
        this.duration = duration;
    }

    public static Permanence fromInt(int duration) {
        for (Permanence type : values()) {
            if (type.getPermanence() == duration) {
                return type;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        Properties properties = LanguageLoader.getLanguageProperties();
        return switch (this) {
            case SIX_MONTHS -> properties.getProperty("SIX_MONTHS_MSG");
            case TWELVE_MONTHS -> properties.getProperty("TWELVE_MONTHS_MSG");
            case TWENTY_FOUR_MONTHS -> properties.getProperty("TWENTY_FOUR_MONTHS_MSG");
            case THIRTY_SIX_MONTHS -> properties.getProperty("THIRTY_SIX_MONTHS_MSG");
        };
    }

    public int getPermanence() {
        return duration;
    }
}
