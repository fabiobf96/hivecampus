package it.hivecampuscompany.hivecampus.cli.view;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CLIView {
    public void displayWelcomeMessage(String title) {
        int totalWidth = 40;
        int titleLength = title.length();
        int spaceForTitle = totalWidth - 2; // Sottrae lo spazio per le barre verticali
        int padding = (spaceForTitle - titleLength) / 2; // Calcola lo spazio vuoto su entrambi i lati del titolo
        int extraSpace = (spaceForTitle - titleLength) % 2; // Gestisce il caso in cui lo spazio non sia divisibile esattamente per 2

        String frame = "+" + "-".repeat(spaceForTitle) + "+";
        String leftPadding = " ".repeat(padding); // Aggiunge spazio per la barra verticale sinistra
        String rightPadding = " ".repeat(padding + extraSpace); // Aggiusta lo spazio a destra se necessario per centramento
        String formattedTitle = "|" + leftPadding + title + rightPadding + "|";

        displayMessage(frame);
        displayMessage(formattedTitle);
        displayMessage(frame);
        displayMessage("\n");
    }
    public String getStringUserInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        printMessage(prompt + ": ");
        return scanner.nextLine();
    }
    public int getIntUserInput(String prompt) throws InputMismatchException {
        Scanner scanner = new Scanner(System.in);
        printMessage(prompt + ": ");
        return scanner.nextInt();
    }
    public void displayMessage(String message) {
        System.out.println(message);
    }
    private void printMessage(String message){
            System.out.print(message);
        }
}

