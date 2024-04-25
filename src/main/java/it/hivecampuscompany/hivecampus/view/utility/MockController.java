package it.hivecampuscompany.hivecampus.view.utility;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.http.client.fluent.Request;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.logging.Logger;

public class MockController extends Application {

    private static final Logger LOGGER = Logger.getLogger(MockController.class.getName());

    private byte[] getImage() {
        try {
            return Request.Get("http://localhost:8080/get-image")
                    .execute().returnContent().asBytes();
        } catch (IOException e) {
            LOGGER.warning("Il server non è raggiungibile, prego riprovare più tardi");
        }
        return new byte[0];
    }

    @Override
    public void start(Stage stage) {

        // Creazione del pulsante per selezionare l'immagine
        Button btnImage= new Button("Visualizza immagine");

        // Creazione di un VBox per image
        VBox imageVbox = new VBox();
        imageVbox.setPrefSize(200, 200);
        imageVbox.setStyle("-fx-border-color: black; -fx-border-width: 1;");

        VBox vbox = new VBox();

        // Azione quando viene cliccato il pulsante
        btnImage.setOnAction(e -> {

            byte[] imgByte = getImage();

            // Creazione di un'immagine dall'array di byte
            Image img = new Image(new ByteArrayInputStream(imgByte));

            // Creazione di un'ImageView per visualizzare l'immagine
            javafx.scene.image.ImageView imageView = new javafx.scene.image.ImageView(img);

            imageView.setPreserveRatio(false);
            imageView.setFitHeight(200);
            imageView.setFitWidth(200);

            imageVbox.setAlignment(javafx.geometry.Pos.CENTER);
            imageVbox.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
            imageVbox.getChildren().clear();

            imageVbox.getChildren().add(imageView);
            // Ferma il server

        });

        vbox.setPadding(new javafx.geometry.Insets(20));
        vbox.setAlignment(javafx.geometry.Pos.CENTER);
        vbox.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        vbox.setSpacing(20);

        vbox.getChildren().addAll(imageVbox, btnImage);

        Stage primaryStage = new Stage();
        // Creazione di una nuova scena con l'immagine visualizzata
        Scene scene = new Scene(vbox);
        // Impostazione della scena sulla finestra principale
        primaryStage.setScene(scene);
        // Mostra la finestra
        primaryStage.show();
    }
}