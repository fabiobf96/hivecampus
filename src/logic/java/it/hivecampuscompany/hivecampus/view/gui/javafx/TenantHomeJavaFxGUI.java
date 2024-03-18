package it.hivecampuscompany.hivecampus.view.gui.javafx;

import it.hivecampuscompany.hivecampus.view.controller.javafx.TenantHomeJavaFxController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX GUI for Tenant Home Page.
 */

public class TenantHomeJavaFxGUI extends Application {

    /**
     * This method is empty because JavaFX Application requires a start method to launch the application.
     * In this case, the GUI is started using the startWithController method.
     *
     * @param stage The primary stage for this application.
     */
    @Override
    public void start(Stage stage) {
        // This method is intentionally left empty as GUI is started using startWithController method.
    }

    /**
     * Starts the application with the provided controller.
     *
     * @param stage      The primary stage for this application.
     * @param controller The controller associated with the GUI.
     * @throws IOException if an error occurs during loading or initializing the GUI.
     */

    public void startWithTenantController(Stage stage, TenantHomeJavaFxController controller) throws IllegalArgumentException, IOException {
        if (controller == null) {
            throw new IllegalArgumentException("Controller cannot be null");
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/hivecampuscompany/hivecampus/tenantHome-view.fxml"));

        // Associate the controller instance with the FXMLLoader
        loader.setController(controller);

        try {
            Parent root = loader.load();

            Scene scene = new Scene(root, 900, 600);
            stage.setScene(scene);
            stage.setTitle("Home Page");
            stage.show();
        } catch (IOException e) {
            // Log or handle the exception as appropriate
            throw new IOException("Error loading FXML file", e);
        }
    }
}

