package it.hivecampuscompany.hivecampus.state.javafx.controller;

import it.hivecampuscompany.hivecampus.state.javafx.InitialJavaFXPage;
import it.hivecampuscompany.hivecampus.state.javafx.LoginJavaFXPage;
import it.hivecampuscompany.hivecampus.state.javafx.SignUpJavaFXPage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class InitialJavaFXPageController {

    @FXML
    Button btnLoginChoice;

    @FXML
    Button btnSignUpChoice;

    public void initialize(InitialJavaFXPage initialJavaFXPage) {

        btnLoginChoice.setOnAction(e -> initialJavaFXPage.goToLoginPage(new LoginJavaFXPage(initialJavaFXPage.getContext())));

        btnSignUpChoice.setOnAction(e -> initialJavaFXPage.goToSignUpPage(new SignUpJavaFXPage(initialJavaFXPage.getContext())));
    }

}
