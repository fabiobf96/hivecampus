package it.hivecampuscompany.hivecampus.state.javafx.controller;

import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.view.controller.javafx.JavaFxController;
import it.hivecampuscompany.hivecampus.view.controller.javafx.TabInitializerController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

public class ManageAdsJavaFXPageController extends JavaFxController implements TabInitializerController{

    @FXML
    Button btnCreate;

    @Override
    public void initialize(Context context) {
        btnCreate.setOnAction(event -> handleCreateAd());
    }

    private void handleCreateAd() {
        // Solo per provare il caricamento di un'altra pagina
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/it/hivecampuscompany/hivecampus/createAdForm-view.fxml"));

    }
}
