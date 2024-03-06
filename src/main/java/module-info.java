module it.hivecampuscompany.hivecampus {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires com.opencsv;


    opens it.hivecampuscompany.hivecampus to javafx.fxml;
    exports it.hivecampuscompany.hivecampus.graphic.utility;
    opens it.hivecampuscompany.hivecampus.graphic.utility to javafx.fxml;
    exports it.hivecampuscompany.hivecampus.graphic.javafx;
    opens it.hivecampuscompany.hivecampus.graphic.javafx to javafx.fxml;
    exports it.hivecampuscompany.hivecampus.graphic;
    opens it.hivecampuscompany.hivecampus.graphic to javafx.fxml;
}