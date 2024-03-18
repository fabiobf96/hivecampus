module it.hivecampuscompany.hivecampus {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires com.opencsv;
    requires java.sql;

    opens it.hivecampuscompany.hivecampus to javafx.fxml;
    exports it.hivecampuscompany.hivecampus.view.utility;
    opens it.hivecampuscompany.hivecampus.view.utility to javafx.fxml;
    opens it.hivecampuscompany.hivecampus.view.controller.javafx to javafx.fxml;
    exports it.hivecampuscompany.hivecampus.view.gui.javafx;
    opens it.hivecampuscompany.hivecampus.view.gui.javafx to javafx.fxml;
    exports it.hivecampuscompany.hivecampus.view.controller.javafx;
    exports it.hivecampuscompany.hivecampus;
}