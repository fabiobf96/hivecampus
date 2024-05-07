package it.hivecampuscompany.hivecampus.stateCli.cli.controller;

import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.bean.HomeBean;
import it.hivecampuscompany.hivecampus.bean.RoomBean;
import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.manager.AdManager;
import it.hivecampuscompany.hivecampus.model.AdStart;
import it.hivecampuscompany.hivecampus.viewCli.controller.cli.CLIController;
import it.hivecampuscompany.hivecampus.viewCli.gui.cli.FormCliGUI;

import java.util.ArrayList;
import java.util.List;

public class ManageAdsCLIPageController extends CLIController {

    private final AdManager manager;
    private final FormCliGUI formView;

    String street;
    String streetNumber;
    String city;
    String homeType;
    String numBedrooms;
    String numBathrooms;
    String floor;
    boolean elevator;
    String homeDescription;
    String roomType;
    boolean privateBath;
    boolean balcony;
    boolean conditioner;
    boolean tvConnection;
    String roomDescription;
    int homeSurface;
    int roomSurface;
    int price;
    int monthAvailable;
    List<HomeBean> homeBeans = new ArrayList<>();
    HomeBean hBean;

    public ManageAdsCLIPageController() {
        formView = new FormCliGUI();
        manager = new AdManager();
    }

    @Override
    public void homePage() {
        formView.clean();
        formView.displayWelcomeMessage(properties.getProperty("MANAGE_ADS_MSG").toUpperCase());
    }

    public void showManageAdsOptions(SessionBean sessionBean) throws InvalidSessionException {
        viewAds(sessionBean);
        formView.displayMessage("\n1. " + properties.getProperty("CREATE_AD_MSG"));
        formView.displayMessage("2. " + properties.getProperty("EDIT_AD_MSG"));
        formView.displayMessage("3. " + properties.getProperty("DELETE_AD_MSG"));
        formView.displayMessage("4. " + properties.getProperty("GO_BACK_MSG"));
    }

    public int getChoice() {
        return formView.getIntUserInput(properties.getProperty("CHOICE_MSG"));
    }

    public boolean createAdOptions(SessionBean sessionBean) throws InvalidSessionException {
        formView.clean();
        formView.displayWelcomeMessage(properties.getProperty("CREATE_AD_MSG").toUpperCase());

        int res = viewHomes(sessionBean);

        if (res >= 0 && res < homeBeans.size()) {
            // ho scelto di utilizzare una casa esistente
            hBean = homeBeans.get(res);
            // controllo se posso aggiungere un'altra stanza
            if (manager.isMaxRoomsReached(hBean)){
                formView.displayMessage(properties.getProperty("MAX_ROOMS_REACHED_MSG"));
                pause();
                return false;
            }
            return adCreationForm(hBean);
        }
        else if (res == homeBeans.size()) {
            // creo una nuova casa
            return adCreationForm(null);
        }
        else // go back
            return false;

    }

    public boolean adCreationForm(HomeBean homeBean) {
        formView.clean();
        formView.displayWelcomeMessage(properties.getProperty("CREATE_AD_FORM_MSG").toUpperCase());

        if (homeBean == null) {
            formView.displayMessage(properties.getProperty("HOME_INFORMATION_MSG") + "\n");

            street = getField(properties.getProperty("STREET_FIELD_REQUEST_MSG"), false);
            streetNumber = getField(properties.getProperty("STREET_NUMBER_FIELD_REQUEST_MSG"), false);
            city = getField(properties.getProperty("CITY_FIELD_REQUEST_MSG"), false);

            formView.displayMessage(properties.getProperty("TYPE_FIELD_REQUEST_MSG"));
            formView.displayTypesHome();
            homeType = convertTypeHome(getField(properties.getProperty("TYPE_CHOICE_MSG"), false));

            homeSurface = Integer.parseInt(getField(properties.getProperty("SURFACE_FIELD_REQUEST_MSG"), false));
            numBathrooms = getField(properties.getProperty("NUM_BATHROOMS_FIELD_REQUEST_MSG"), false);
            floor = getField(properties.getProperty("FLOOR_FIELD_REQUEST_MSG"), false);
            elevator = getBooleanInput(properties.getProperty("ELEVATOR_FIELD_REQUEST_MSG"));
            homeDescription = getField(properties.getProperty("DESCRIPTION_FIELD_REQUEST_MSG"), false);
        }

        else {
            formView.displayMessage(properties.getProperty("EXISTING_HOME_INFORMATION_MSG") + "\n");
            formView.displayMessage(homeBean.toString());
        }

        formView.displayMessage("\n" + properties.getProperty("ROOM_INFORMATION_MSG") + "\n");

        formView.displayMessage(properties.getProperty("TYPE_FIELD_REQUEST_MSG"));
        formView.displayTypesRoom();
        roomType = convertTypeRoom(getField(properties.getProperty("TYPE_CHOICE_MSG"), false));

        roomSurface = Integer.parseInt(getField(properties.getProperty("SURFACE_FIELD_REQUEST_MSG"), false));
        privateBath = getBooleanInput(properties.getProperty("PRIVATE_BATH_FIELD_REQUEST_MSG"));
        balcony = getBooleanInput(properties.getProperty("BALCONY_FIELD_REQUEST_MSG"));
        conditioner = getBooleanInput(properties.getProperty("CONDITIONER_FIELD_REQUEST_MSG"));
        tvConnection = getBooleanInput(properties.getProperty("TV_CONNECTION_FIELD_REQUEST_MSG"));
        price = Integer.parseInt(getField(properties.getProperty("PRICE_FIELD_REQUEST_MSG"), false));
        formView.displayMessage(properties.getProperty("MONTH_AVAILABLE_FIELD_REQUEST_MSG"));
        formView.displayMonths();
        monthAvailable = Integer.parseInt(getField(properties.getProperty("MONTH_CHOICE_MSG"), false));
        roomDescription = getField(properties.getProperty("DESCRIPTION_FIELD_REQUEST_MSG"), false);

        formView.displayMessage("1. " + properties.getProperty("PUBLISH_AD_MSG"));
        formView.displayMessage("2. " + properties.getProperty("CANCEL_GO_BACK_MSG"));

        int choice = formView.getIntUserInput(properties.getProperty("PUBLISH_CHOICE_MSG"));

        return choice == 1;
    }

    public void publishAd(SessionBean sessionBean) {
        HomeBean homeBean;

        if (hBean == null) {
            String address = street + ", " + streetNumber + ", " + city;
            int lift = this.elevator ? 1 : 0;
            Integer[] features = new Integer[]{Integer.parseInt(numBedrooms), Integer.parseInt(numBathrooms), Integer.parseInt(floor), lift};
            homeBean = new HomeBean(address, homeType, homeSurface, features, homeDescription);
        }
        else homeBean = hBean;

        boolean[] services = new boolean[]{privateBath, balcony, conditioner, tvConnection};
        RoomBean roomBean = new RoomBean(roomType, roomSurface, services, roomDescription);

        boolean res  = manager.publishAd(sessionBean, homeBean, roomBean, price, AdStart.fromInt(monthAvailable));

        if (res) {
            formView.displayMessage(properties.getProperty("AD_PUBLISHED_MSG"));
        }
        else formView.displayMessage(properties.getProperty("FAILED_PUBLISH_AD"));
        pause();
    }

    public void viewAds(SessionBean sessionBean) throws InvalidSessionException {
        List<AdBean> adBeans = manager.searchAdsByOwner(sessionBean, new AdBean(null));
        for (AdBean adBean : adBeans) {
            formView.displayMessage(adBean.toString());
        }
    }

    public int viewHomes(SessionBean sessionBean) throws InvalidSessionException {
        homeBeans = manager.getHomesByOwner(sessionBean);
        int i;
        for (i = 0; i < homeBeans.size(); i++) {
            formView.displayMessage(i + ") " + homeBeans.get(i).toString());
        }
        formView.displayMessage(i + ") " + properties.getProperty("CREATE_NEW_HOME_MSG"));
        formView.displayMessage(i+1 + ") " + properties.getProperty("GO_BACK_MSG"));
        return formView.getIntUserInput(properties.getProperty("CHOICE_MSG"));
    }

    public String convertTypeHome(String type) {
        switch (type) {
            case "1" -> {
                numBedrooms = "1";
                return properties.getProperty("STUDIO_APARTMENT_MSG"); // Monolocale
            }
            case "2" -> {
                numBedrooms = "2";
                return properties.getProperty("ONE_BEDROOM_APARTMENT_MSG"); // Bilocale
            }
            case "3" -> {
                numBedrooms = "3";
                return properties.getProperty("TWO_BEDROOM_APARTMENT_MSG"); // Trilocale
            }
            case "4" -> {
                numBedrooms = "4";
                return properties.getProperty("THREE_BEDROOM_APARTMENT_MSG"); // Quadrilocale
            }
            default -> {
                numBedrooms = null;
                return null;
            }
        }
    }

    public String convertTypeRoom(String type) {
        return switch (type) {
            case "1" -> properties.getProperty("SINGLE_ROOM_MSG"); // Singola
            case "2" -> properties.getProperty("DOUBLE_ROOM_MSG"); // Doppia
            default -> null;
        };
    }
}
