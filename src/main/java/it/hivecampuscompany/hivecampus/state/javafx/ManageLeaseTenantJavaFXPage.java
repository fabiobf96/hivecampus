package it.hivecampuscompany.hivecampus.state.javafx;

import it.hivecampuscompany.hivecampus.bean.LeaseBean;
import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.exception.MockOpenAPIException;
import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.ManageLeasePage;
import it.hivecampuscompany.hivecampus.state.javafx.ui.component.BasicAd;
import it.hivecampuscompany.hivecampus.state.javafx.ui.decoration.CssDecoration;
import it.hivecampuscompany.hivecampus.state.javafx.ui.decoration.LeaseDecorator;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ManageLeaseTenantJavaFXPage extends ManageLeasePage {
    public ManageLeaseTenantJavaFXPage(Context context) {
        super(context);
    }

    @Override
    public void handle() throws InvalidSessionException {
        LeaseBean leaseBean = getUnSignedLease();
        BasicAd basicAd = new BasicAd(leaseBean.getAdBean(), context);
        LeaseDecorator leaseDecorator = new LeaseDecorator(basicAd, LeaseDecorator.Type.TENANT);
        CssDecoration cssDecoration = new CssDecoration(leaseDecorator);
        Node root = cssDecoration.setup();
        Button btnDownload = (Button) root.lookup("#btnDownload");
        btnDownload.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            File selectedFile = fileChooser.showSaveDialog(context.getStage());
            if (selectedFile != null) {
                String path = selectedFile.getPath() + ".pdf";
                try (FileOutputStream fos = new FileOutputStream(path)) {
                    fos.write(leaseBean.getContract());
                } catch (IOException e) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                }
            }
        });
        Button btnSign = (Button) root.lookup("#btnSign");
        btnSign.setOnAction(event -> {
            try {
                signContract();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Firma avvenuta con successo");
                alert.showAndWait();
                context.request();
            } catch (InvalidSessionException e) {
                context.invalidSessionExceptionHandle();
            } catch (MockOpenAPIException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        });
        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setPadding(new Insets(10));
        scrollPane.fitToWidthProperty().setValue(true);
        context.getTab(2).setContent(scrollPane);
    }
}
