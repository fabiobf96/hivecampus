package it.hivecampuscompany.hivecampus.state.javafx;

import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.bean.LeaseBean;
import it.hivecampuscompany.hivecampus.bean.LeaseRequestBean;
import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.ManageLeasePage;
import it.hivecampuscompany.hivecampus.state.javafx.ui.component.BasicRequest;
import it.hivecampuscompany.hivecampus.state.javafx.ui.decoration.CssDecoration;
import it.hivecampuscompany.hivecampus.state.javafx.ui.decoration.LeaseDecorator;
import it.hivecampuscompany.hivecampus.state.javafx.ui.decoration.PreviewRoomDecorator;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ManageLeaseOwnerJavaFXPage extends ManageLeasePage {
    public ManageLeaseOwnerJavaFXPage(Context context) {
        super(context);
    }

    @Override
    public void handle() throws InvalidSessionException {
        List<AdBean> adBeanList = getProcessingAds();
        ScrollPane scrollPane = new ScrollPane();
        for (AdBean adBean : adBeanList) {
            LeaseRequestBean leaseRequestBean = getLeaseRequestInformation(adBean);
            BasicRequest basicRequest = new BasicRequest(leaseRequestBean);
            LeaseDecorator leaseDecorator = new LeaseDecorator(basicRequest, LeaseDecorator.Type.OWNER);
            CssDecoration cssDecoration = new CssDecoration(leaseDecorator);
            PreviewRoomDecorator previewRoomDecorator = new PreviewRoomDecorator(cssDecoration, adBean, context);
            CssDecoration decoration = new CssDecoration(previewRoomDecorator);
            Node root = decoration.setup();
            Button btnUpload = (Button) root.lookup("#btnUpload");
            btnUpload.setOnAction(e -> {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Seleziona un contratto");
                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Documenti", "*.pdf"));
                File selectedFile = fileChooser.showOpenDialog(context.getStage());
                leaseRequestBean.setAdBean(adBean);
                try {
                    LeaseBean leaseBean = new LeaseBean(leaseRequestBean, selectedFile.getPath());
                    uploadLease(leaseBean);
                    context.request();
                } catch (IOException ex) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText(ex.getMessage());
                    alert.showAndWait();
                } catch (InvalidSessionException ex) {
                    context.invalidSessionExceptionHandle();
                }
            });
            scrollPane.setContent(root);
        }
        scrollPane.fitToWidthProperty().setValue(true);
        context.getTab(2).setContent(scrollPane);
    }
}
