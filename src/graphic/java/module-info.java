module it.hivecampuscompany.hivecampus {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires com.opencsv;


    opens it.hivecampuscompany.hivecampus to javafx.fxml;
    exports it.hivecampuscompany.hivecampus;
    exports it.hivecampuscompany.hivecampus.utility;
    opens it.hivecampuscompany.hivecampus.utility to javafx.fxml;
}