package it.hivecampuscompany.hivecampus.state.cli.ui;

import it.hivecampuscompany.hivecampus.state.utility.LanguageLoader;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class provides methods for displaying welcome messages,
 * obtaining input from the user, and clearing the console. It includes
 * functionality for requesting and validating string and integer inputs.
 *
 * @author Fabio Barchiesi
 * @version 1.3
 * @since 2024-02-18
 */

public class CliGUI {

    private static final Logger LOGGER = Logger.getLogger(CliGUI.class.getName());
    protected Properties properties = LanguageLoader.getLanguageProperties();

    /**
     * Displays a welcome message within a frame. The message title is centered within the frame,
     * which has a fixed total width. If the title is longer than the frame can accommodate,
     * the title will not be correctly centered.
     *
     * @param title The title or message to be displayed at the center of the frame.
     */
    public void displayWelcomeMessage(String title) {
        int totalWidth = 40;
        int titleLength = title.length();
        int spaceForTitle = totalWidth - 2;
        int padding = (spaceForTitle - titleLength) / 2;
        int extraSpace = (spaceForTitle - titleLength) % 2;

        String frame = "+" + "-".repeat(spaceForTitle) + "+";
        String leftPadding = " ".repeat(padding);
        String rightPadding = " ".repeat(padding + extraSpace);
        String formattedTitle = "|" + leftPadding + title + rightPadding + "|";

        displayMessage(frame);
        displayMessage(formattedTitle);
        displayMessage(frame);
        displayMessage("\n");
    }

    /**
     * Prompts the user with a specified message and returns the user's input as a String.
     * This method does not validate the input; it directly returns whatever the user types.
     *
     * @param prompt The message displayed to the user as a prompt for input.
     * @return The string input received from the user.
     */
    public String getStringUserInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        printMessage(prompt + ": ");
        return scanner.nextLine();
    }

    /**
     * Prompts the user with a specified message and attempts to return the user's input as an integer.
     * If the user enters input that is not a valid integer, an InputMismatchException is thrown.
     *
     * @param prompt The message displayed to the user as a prompt for input.
     * @return The integer input received from the user.
     * @throws InputMismatchException if the next token does not match the Integer regular expression,
     *                                or is out of range for the Integer type.
     */
    public int getIntUserInput(String prompt) {
        try {
            Scanner scanner = new Scanner(System.in);
            printMessage(prompt + ": ");
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            return -1;
        }
    }

    /**
     * Clears the console screen based on the OS environment.
     * Utilizes "cls" command for Windows or "clear" for Unix/Linux/macOS. If "TERM" environment
     * variable is not set, simulates clearing by printing 50 new lines as a fallback.
     * Handles {@link IOException} for command execution issues and {@link InterruptedException}
     * for thread interruptions, re-interrupting the thread in the latter case.
     */
    public void clean() {
        String term = System.getenv("TERM");
        if (term == null || term.isEmpty()) {
            term = System.getenv("PROMPT");
        }

        if (term != null && !term.isEmpty()) {
            try {
                String operatingSystem = System.getProperty("os.name"); // Controlla il sistema operativo

                if (operatingSystem.contains("Windows")) {
                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor(); // Comando per Windows
                } else {
                    new ProcessBuilder("clear").inheritIO().start().waitFor(); // Comando per Unix/Linux/macOS
                }
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "An I/O error occurred during terminal cleanup: ", e);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                LOGGER.log(Level.SEVERE, "The thread was interrupted during terminal cleanup: ", e);
            }
        } else {
            for (int i = 0; i < 50; i++) displayMessage("");
        }
    }

    /**
     * Prints a message to the standard output with a newline at the end.
     *
     * @param message The message to be displayed to the user.
     */
    public void displayMessage(String message) {
        System.out.println(message);
    }

    /**
     * Prints a message to the standard output without adding a newline at the end. This method is used
     * for printing prompts to which the user is expected to provide an input on the same line.
     *
     * @param message The message to be displayed to the user.
     */
    private void printMessage(String message) {
        System.out.print(message);
    }

}
