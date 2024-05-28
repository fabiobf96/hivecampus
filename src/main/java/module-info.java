module it.hivecampuscompany.hivecampus {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires com.opencsv;
    requires java.sql;
    requires java.desktop;
    requires wiremock.standalone;
    requires org.apache.httpcomponents.httpclient;
    requires org.apache.httpcomponents.httpclient.fluent;
    requires com.fasterxml.jackson.databind;
    requires java.net.http;

    opens it.hivecampuscompany.hivecampus.images;

    exports it.hivecampuscompany.hivecampus;
    opens it.hivecampuscompany.hivecampus to javafx.fxml;

    exports it.hivecampuscompany.hivecampus.state.utility;
    opens it.hivecampuscompany.hivecampus.state.utility to javafx.fxml;

    exports it.hivecampuscompany.hivecampus.state.javafx.ui.component;
    opens it.hivecampuscompany.hivecampus.state.javafx.ui.component to javafx.fxml;

    exports it.hivecampuscompany.hivecampus.state.javafx.ui.decoration;
    opens it.hivecampuscompany.hivecampus.state.javafx.ui.decoration to javafx.fxml;

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

    exports it.hivecampuscompany.hivecampus.state.cli;
    opens it.hivecampuscompany.hivecampus.state.cli to javafx.fxml;

    exports it.hivecampuscompany.hivecampus.state.javafx;
    opens it.hivecampuscompany.hivecampus.state.javafx to javafx.fxml;
    exports it.hivecampuscompany.hivecampus.state.javafx.ui.composite;
    opens it.hivecampuscompany.hivecampus.state.javafx.ui.composite to javafx.fxml;
}