package it.hivecampuscompany.hivecampus;

import it.hivecampuscompany.hivecampus.stateCli.Context;
import it.hivecampuscompany.hivecampus.stateCli.cli.InitialCLIPage;

public class MainCLI {
    public static void main(String[] args){
        Context context = new Context();
        context.setState(new InitialCLIPage(context));
        context.request();
    }
}
