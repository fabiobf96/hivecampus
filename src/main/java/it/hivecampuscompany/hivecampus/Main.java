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
    public static void main (String[] args) {
        DAOFactoryFacade daoFactoryFacade = DAOFactoryFacade.getInstance();
        if (args[0].equals("csv")){
            daoFactoryFacade.setPersistenceType(PersistenceType.CSV);
        } else {
            daoFactoryFacade.setPersistenceType(PersistenceType.MYSQL);
        }

        if (args[1].equals("javafx")){
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
