package it.hivecampuscompany.hivecampus.state.cli.controller;

import it.hivecampuscompany.hivecampus.bean.AccountBean;
import it.hivecampuscompany.hivecampus.manager.LoginManager;
import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.cli.ui.CliGUI;

import java.util.logging.Logger;

/**
 *  Controller class for the Account Settings CLI page.
 *  It extends the CLIController class and provides methods to display the home page.
 */

public class AccountSettingsCLIController extends CLIController {

    Context context;
    private static final Logger LOGGER = Logger.getLogger(AccountSettingsCLIController.class.getName());

    public AccountSettingsCLIController(Context context) {
        this.context = context;
        view = new CliGUI();
    }

    /**
     * Method to display the home page.
     * It displays a welcome message and the options to change the language,
     * view the profile, or go back.
     *
     * @author Marina Sotiropoulos
     */

    @Override
    public void homePage() {
        view.clean();
        view.displayWelcomeMessage(properties.getProperty("ACCOUNT_SETTINGS_MSG"));
        view.displayMessage("1. " + properties.getProperty("CHANGE_LANGUAGE_MSG"));
        view.displayMessage("2. " + properties.getProperty("VIEW_PROFILE_MSG"));
        view.displayMessage("3. " + properties.getProperty("GO_BACK_MSG"));

        switch (view.getIntUserInput(properties.getProperty("CHOICE_MSG"))) {
            case 1 -> {
                LanguageCLIController languageCLIController = new LanguageCLIController();
                languageCLIController.homePage();
            }
            case 2 -> {
                accountSettingsPage(context);
                homePage();
            }
            case 3 -> view.clean();
            default -> invalidChoice();
        }
    }

    /**
     * Method to display the account settings page.
     * It displays the user's account information and options to change the password or go back.
     *
     * @param context The context object for the account settings page.
     *                It contains the session bean with the user's information.
     *
     * @author Marina Sotiropoulos
     */

    public void accountSettingsPage(Context context) {

        view.clean();
        view.displayWelcomeMessage(properties.getProperty("VIEW_PROFILE_MSG"));

        LoginManager manager = new LoginManager();
        AccountBean accountBean = manager.getAccountInfo(context.getSessionBean());

        view.displayMessage("\n" + properties.getProperty("NAME_MSG") + ": " + accountBean.getName());
        view.displayMessage(properties.getProperty("SURNAME_MSG") + ": " + accountBean.getSurname());
        view.displayMessage(properties.getProperty("EMAIL_MSG") + ": " + accountBean.getEmail());
        view.displayMessage(properties.getProperty("PASSWORD_MSG") + ": " + "********");
        view.displayMessage(properties.getProperty("ROLE_MSG") + ": " + context.getSessionBean().getRole());
        view.displayMessage(properties.getProperty("PHONE_MSG") + ": " + accountBean.getPhoneNumber());

        view.displayMessage("\n1." + properties.getProperty("CHANGE_PSW_MSG"));
        view.displayMessage("2." + properties.getProperty("GO_BACK_MSG"));

        try {
            int choice = view.getIntUserInput(properties.getProperty("CHOICE_MSG"));

            switch (choice) {
                case 1 -> {
                    notImplementedYet();
                    accountSettingsPage(context);
                }
                case 2 -> homePage();

                default -> {
                    invalidChoice();
                    accountSettingsPage(context);
                }
            }
        } catch (Exception e) {
            LOGGER.severe(properties.getProperty("INVALID_INPUT_MSG"));
        }
    }
}
