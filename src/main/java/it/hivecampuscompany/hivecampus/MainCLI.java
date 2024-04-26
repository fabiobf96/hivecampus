package it.hivecampuscompany.hivecampus;

import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.cli.InitialCLIPage;

public class MainCLI {
    public static void main(String[] args){
        Context context = new Context();
        context.setState(new InitialCLIPage(context));
        context.request();
    }
}
