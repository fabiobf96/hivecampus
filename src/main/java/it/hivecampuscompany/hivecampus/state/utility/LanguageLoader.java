package it.hivecampuscompany.hivecampus.state.utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The LanguageLoader class is responsible for loading language properties from
 * specified files. It supports loading English and Italian language properties.
 * The class cannot be instantiated and uses static methods to manage the properties.
 *
 * @author Fabio Barchiesi
 */

public class LanguageLoader {

    // Prevent instantiation of the class with a private constructor
    private LanguageLoader() {
    }

    // Variable to keep track of the loaded properties
    private static Properties properties;
    // Logger to record error messages or information
    private static final Logger LOGGER = Logger.getLogger(LanguageLoader.class.getName());
    private static int currentLanguage = 0;

    /**
     * Loads the properties file based on the selected language.
     *
     * @param language the language code (0 for English, 1 for Italian)
     * @throws InputMismatchException if an invalid language code is provided
     *
     * @author Fabio Barchiesi
     */
    public static void loadLanguage(int language) throws InputMismatchException{
        // Initialize the path of the properties file based on the language
        String languagePath;
        switch (language) {
            case 0 -> {
                languagePath = "properties/language_EN.properties";
                currentLanguage = 0;
            }
            case 1 -> {
                languagePath = "properties/language_IT.properties";
                currentLanguage = 1;
            }
            default -> throw new InputMismatchException("Invalid language selection");
        }

        // Use the classloader to load the file from the resources
        try (InputStream input = new FileInputStream(languagePath)) {
            properties = new Properties();
            properties.load(input);
        } catch (IOException e) {
            // Log the error and terminate the program
            LOGGER.log(Level.SEVERE, "Failed to load language properties: ", e);
            System.exit(1);
        }
    }

    /**
     * Returns the properties of the loaded language.
     * If the properties have not been loaded yet, it loads the English properties by default.
     *
     * @return the properties of the loaded language
     * @author Fabio Barchiesi
     */
    public static Properties getLanguageProperties() {
        if (properties == null) {
            loadLanguage(currentLanguage); // Carica inglese per default
        }
        return properties;
    }

    /**
     * Returns the current language code.
     *
     * @return the current language code
     * @author Fabio Barchiesi
     */
    public static int getCurrentLanguage() {
        return currentLanguage;
    }
}