package it.hivecampuscompany.hivecampus.state;

/**
 * The ManageRentRatesPage abstract class serves as a base class for pages related to managing rent rates in the application.
 * It implements the State interface and provides methods for navigating to the home page.
 * @author Fabio Barchiesi
 */
public abstract class ManageRentRatesPage implements State {
    protected Context context;
    /**
     * Creates a new ManageRentRatesPage.
     * At the moment, this functionality is not implemented.
     *
     * @param context The context of the application.
     * @author Fabio Barchiesi
     */
    protected ManageRentRatesPage(Context context) {
        this.context = context;
    }
}
