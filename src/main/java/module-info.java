module it.hivecampuscompany.hivecampus {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires com.opencsv;
    requires java.sql;
    requires java.desktop;

    opens it.hivecampuscompany.hivecampus.images;

    exports it.hivecampuscompany.hivecampus;
    opens it.hivecampuscompany.hivecampus to javafx.fxml;

    exports it.hivecampuscompany.hivecampus.view.utility;
    opens it.hivecampuscompany.hivecampus.view.utility to javafx.fxml;

    exports it.hivecampuscompany.hivecampus.view.gui.javafx;
    opens it.hivecampuscompany.hivecampus.view.gui.javafx to javafx.fxml;

    exports it.hivecampuscompany.hivecampus.view.controller.javafx;
    opens it.hivecampuscompany.hivecampus.view.controller.javafx to javafx.fxml;

    exports it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.component;
    opens it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.component to javafx.fxml;

    exports it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.decoration;
    opens it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.decoration to javafx.fxml;

    exports it.hivecampuscompany.hivecampus.bean;
    opens it.hivecampuscompany.hivecampus.bean to javafx.fxml;

    exports it.hivecampuscompany.hivecampus.model;
    opens it.hivecampuscompany.hivecampus.model to javafx.fxml;

    exports it.hivecampuscompany.hivecampus.exception;
    opens it.hivecampuscompany.hivecampus.exception to javafx.fxml;

    exports it.hivecampuscompany.hivecampus.state.javafx.controller;
    opens it.hivecampuscompany.hivecampus.state.javafx.controller to javafx.fxml;

    exports it.hivecampuscompany.hivecampus.state;
    opens it.hivecampuscompany.hivecampus.state to javafx.fxml;

    exports it.hivecampuscompany.hivecampus.state.javafx;
    opens it.hivecampuscompany.hivecampus.state.javafx to javafx.fxml;
}