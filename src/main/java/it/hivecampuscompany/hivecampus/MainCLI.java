package it.hivecampuscompany.hivecampus;

import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.cli.InitialCLIPage;

/**
 * Main class for the command-line interface (CLI) application.
 * This class initializes the application by setting the initial state to the initial CLI page.
 * It then requests the initial state to display the initial page.
 * The application will continue to run until the user exits the application.
 */

public class MainCLI {
    public static void main(String[] args){
        Context context = new Context();
        context.setState(new InitialCLIPage(context));
        context.request();
    }
}
