package it.hivecampuscompany.hivecampus.view.utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LanguageLoader {

    // Impediamo l'istanziazione della classe con un costruttore privato
    private LanguageLoader() {
    }

    // Variabile per tenere traccia delle properties caricate
    private static Properties properties;
    // Logger per registrare messaggi di errore o informazioni
    private static final Logger LOGGER = Logger.getLogger(LanguageLoader.class.getName());

    /**
     * Carica il file di properties in base alla lingua selezionata.
     *
     * @param language codice della lingua (0 per inglese, 1 per italiano)
     */
    public static void loadLanguage(int language) throws InputMismatchException{
        // Inizializza il percorso del file di properties in base alla lingua
        String languagePath;
        switch (language) {
            case 0 -> languagePath = "properties/language_EN.properties";
            case 1 -> languagePath = "properties/language_IT.properties";
            default -> throw new InputMismatchException("Invalid language selection");
        }

        // Usa il classloader per caricare il file dalle risorse
        try (InputStream input = new FileInputStream(languagePath)) {
            properties = new Properties();
            properties.load(input);
        } catch (IOException e) {
            // Log dell'errore e terminazione del programma
            LOGGER.log(Level.SEVERE, "Failed to load language properties: ", e);
            System.exit(1);
        }
    }

    /**
     * Restituisce le properties della lingua caricata.
     * Se le properties non sono state ancora caricate, carica quelle inglesi per default.
     *
     * @return properties della lingua
     */
    public static Properties getLanguageProperties() {
        if (properties == null) {
            loadLanguage(0); // Carica inglese per default
        }
        return properties;
    }
}