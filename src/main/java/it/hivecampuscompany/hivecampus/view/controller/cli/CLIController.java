package it.hivecampuscompany.hivecampus.view.controller.cli;

import it.hivecampuscompany.hivecampus.view.gui.cli.CliGUI;
import it.hivecampuscompany.hivecampus.view.utility.LanguageLoader;
import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.manager.SessionManager;

import java.util.Properties;

/**
 * Abstract class representing a controller for a command-line interface (CLI) application.
 * This class provides common functionalities for handling user interactions such as processing invalid choices,
 * retrieving user input, managing session, and exiting the application. It leverages a properties file for
 * localized messages and a session bean for session management.
 *
 *  @author Fabio Barchiesi
 *  @version 1.1
 *  @since 2024-02-18
 */
public abstract class CLIController {
    protected CliGUI view;
    protected Properties properties;
    protected SessionBean sessionBean;

    /**
     * Constructor for CLIController. It initializes the properties object with
     * language-specific messages.
     */
    protected CLIController(){
        properties = LanguageLoader.getLanguageProperties();
    }

    /**
     * Handles invalid input scenarios by notifying the user of the invalid input,
     * prompts the user to press any key to continue, clears the screen, and
     * then returns to the home page. This method is intended to be used when
     * a user makes a selection that is not recognized by the program.
     */
    protected void invalidChoice() {
        view.displayMessage(properties.getProperty("INVALID_OPTION_MSG"));
        // ricordati di mettorlo dentro al file properties
        view.getStringUserInput("press any key to continue");
        view.clean();
        homePage();
    }

    /**
     * Retrieves a user input string for a specified field. This method prompts the user for input until a non-empty string is entered,
     * unless null inputs are permitted by the method's parameters.
     *
     * @param nameField The name of the field for which the input is being requested. This name is used to prompt the user.
     * @param isNull A boolean value indicating whether null (or empty) inputs are allowed. If true, the method returns immediately after the first input,
     * even if it is empty. If false, the method will repeatedly prompt the user until a non-empty string is entered.
     *
     * @return The user input string for the specified field. If isNull is true and the user enters an empty string, it returns the empty string.
     * Otherwise, it returns the first non-empty string entered by the user.
     */
    protected String getField(String nameField, boolean isNull){
        String field;
        boolean first = true;
        do {
            if(!first){
                view.displayMessage(properties.getProperty("EMPTY_FIELD_MSG"));
            }
            first = false;
            field = view.getStringUserInput(nameField);
            if (isNull){
                break;
            }
        } while (field.isEmpty());
        return field;
    }

    /**
     * Safely exits the application after performing any necessary session cleanup.
     * If a sessionBean is present, it deletes the session before displaying a goodbye message
     * and terminating the application.
     */
    protected void exit(){
        if (sessionBean != null){
            SessionManager sessionManager = SessionManager.getInstance();
            sessionManager.deleteSession(sessionBean);
        }
        view.displayMessage(properties.getProperty("GOODBYE_MSG"));
        System.exit(0);
    }

    protected Boolean getBooleanInput(String message) {
        return view.getStringUserInput(message).equalsIgnoreCase("y");
    }



    /**
     * Abstract method that defines the behavior of the home page in the CLI application.
     * Implementing classes should provide the specific logic to display and handle the home page.
     */
    public abstract void homePage();
}
