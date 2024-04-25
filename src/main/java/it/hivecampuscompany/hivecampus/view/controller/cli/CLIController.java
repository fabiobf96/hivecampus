package it.hivecampuscompany.hivecampus.view.controller.cli;

import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.manager.SessionManager;
import it.hivecampuscompany.hivecampus.view.gui.cli.CliGUI;
import it.hivecampuscompany.hivecampus.view.utility.LanguageLoader;

import java.util.List;
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
    private static final String PRESS_ANY_KEY = "Press any key to continue";
    private static final String INVALID_CHOICE = "Invalid choice, please try again.";

    /**
     * Constructor for CLIController. It initializes the properties object with
     * language-specific messages.
     */
    protected CLIController(){
        properties = LanguageLoader.getLanguageProperties();
        view = new CliGUI();
    }

    /**
     * Handles invalid input scenarios by notifying the user of the invalid input,
     * prompts the user to press any key to continue, clears the screen, and
     * then returns to the home page. This method is intended to be used when
     * a user makes a selection that is not recognized by the program.
     */
    public void invalidChoice() {
        view.displayMessage(properties.getProperty("INVALID_OPTION_MSG"));
        // ricordati di mettorlo dentro al file properties
        view.getStringUserInput(PRESS_ANY_KEY);
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
        } while (field.isBlank());
        return field;
    }

    /**
     * Safely exits the application after performing any necessary session cleanup.
     * If a sessionBean is present, it deletes the session before displaying a goodbye message
     * and terminating the application.
     */
    public void exit(){
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
     * Presents a list of options to the user and allows them to select one.
     * <p>
     * This method displays a given message followed by a numbered list of options
     * based on the {@code itemList} provided. The user is prompted to make a selection
     * by entering the number corresponding to their choice. If the selected item is
     * {@code null}, or the user selects the last item in the list, it signifies a
     * command to "Go Back", returning {@code null}. This allows the user to optionally
     * exit the selection process.
     * <p>
     * The method continuously prompts the user until a valid choice is made. Invalid
     * selections result in the display of an "invalid choice" message.
     *
     * @param <T> the type of elements in the itemList
     * @param itemList a List of items of type {@code T} from which the user can choose.
     *                 The list must not be {@code null} and should include at least one item.
     * @return the item of type {@code T} selected by the user, or {@code null} if the user
     *         chooses to "Go Back" by selecting the last option or if the selected item is {@code null}.
     */
    protected <T> T selectFromList(List<T> itemList, String format) {
        itemList.add(null);
        while (true) {
            for (int i = 0; i < itemList.size()-1 ; i++) {
                if (itemList.get(i) instanceof AdBean adBean && !format.isEmpty()){
                        view.displayMessage(i + ") " + (adBean.toFormatString(format)));
                } else {
                        view.displayMessage(i + ") " + itemList.get(i).toString());
                }
            }
            view.displayMessage(itemList.size() - 1 + ") " + properties.getProperty("GO_BACK_MSG"));
            int choice = view.getIntUserInput("Choice");
            if (choice >= 0 && choice <= itemList.size() - 1) {
                return itemList.get(choice);
            }  else {
                view.displayMessage(INVALID_CHOICE);
            }
        }
    }

    public void displayError (String message) {
        view.displayMessage(message);
        view.getStringUserInput(PRESS_ANY_KEY);
        view.clean();
        homePage();
    }

    public void notImplementedYet () {
        view.displayMessage("Not Implemented Yet!");
        view.getStringUserInput(PRESS_ANY_KEY);
    }

    public void pause() {
        view.getStringUserInput("\n" + PRESS_ANY_KEY);
    }

    /**
     * Abstract method that defines the behavior of the home page in the CLI application.
     * Implementing classes should provide the specific logic to display and handle the home page.
     */
    public abstract void homePage();
}
