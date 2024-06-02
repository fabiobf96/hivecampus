package it.hivecampuscompany.hivecampus;

import it.hivecampuscompany.hivecampus.dao.facade.DAOFactoryFacade;
import it.hivecampuscompany.hivecampus.dao.facade.PersistenceType;
import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.cli.InitialCLIPage;
import it.hivecampuscompany.hivecampus.state.javafx.InitialJavaFXPage;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    // use "csv cli" as arguments for launch the application in cli version with csv persistence
    // use "csv javafx" as arguments for launch the application in javafx version with csv persistence
    // use "mysql cli" as arguments for launch the application in cli version with mysql persistence
    // use "mysql javafx" as arguments for launch the application in javafx version with mysql persistence

    public static void main(String[] args) {
        DAOFactoryFacade daoFactoryFacade = DAOFactoryFacade.getInstance();

        // Set default persistence and interface types
        String persistenceType = args.length > 0 ? args[0].toLowerCase() : "csv";
        String interfaceType = args.length > 1 ? args[1].toLowerCase() : "cli";

        // Set the persistence type
        if ("mysql".equals(persistenceType)) {
            daoFactoryFacade.setPersistenceType(PersistenceType.MYSQL);
        } else {
            daoFactoryFacade.setPersistenceType(PersistenceType.CSV);
        }

        // Launch the appropriate interface
        if ("javafx".equals(interfaceType)) {
            launch(args);
        } else {
            Context context = new Context();
            context.setState(new InitialCLIPage(context));
            context.request();
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        Context context = new Context();
        context.setStage(stage);
        context.setState(new InitialJavaFXPage(context));
        context.request();
    }
}
