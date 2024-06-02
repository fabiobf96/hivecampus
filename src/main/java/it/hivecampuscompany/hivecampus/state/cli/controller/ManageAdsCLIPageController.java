package it.hivecampuscompany.hivecampus.state.cli.controller;

import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.bean.HomeBean;
import it.hivecampuscompany.hivecampus.bean.RoomBean;
import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.manager.AdManager;
import it.hivecampuscompany.hivecampus.model.Month;
import it.hivecampuscompany.hivecampus.state.cli.ui.FormCliGUI;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for the Manage Ads CLI Page. It extends the CLIController class and uses the AdManager class to manage ads.
 * It provides methods to display the home page, show manage ads options, get user's choice, create ad options, ad creation form,
 * publish ad, view ads, view homes.
 */

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
    String homeImagePath;
    String roomType;
    boolean privateBath;
    boolean balcony;
    boolean conditioner;
    boolean tvConnection;
    String roomDescription;
    String roomImagePath;
    int homeSurface;
    int roomSurface;
    int price;
    int monthAvailable;
    List<HomeBean> homeBeans = new ArrayList<>();
    HomeBean hBean;

    /**
     * Constructor for the ManageAdsCLIPageController class.
     */

    public ManageAdsCLIPageController() {
        formView = new FormCliGUI();
        manager = new AdManager();
    }

    /**
     * Method to display the home page. It displays a welcome message.
     * @author Marina Sotiropoulos
     */

    @Override
    public void homePage() {
        formView.clean();
        formView.displayWelcomeMessage(properties.getProperty("MANAGE_ADS_MSG").toUpperCase());
    }

    /**
     * Method to show manage ads options.
     * @param sessionBean SessionBean object.
     * @throws InvalidSessionException If the session is invalid.
     * @author Marina Sotiropoulos
     */

    public void showManageAdsOptions(SessionBean sessionBean) throws InvalidSessionException {
        viewAds(sessionBean);
        formView.displayMessage("\n1. " + properties.getProperty("CREATE_AD_MSG"));
        formView.displayMessage("2. " + properties.getProperty("EDIT_AD_MSG"));
        formView.displayMessage("3. " + properties.getProperty("DELETE_AD_MSG"));
        formView.displayMessage("4. " + properties.getProperty("GO_BACK_MSG"));
    }

    /**
     * Method to get the user's choice.
     * @return int value that represents the user's choice.
     * @author Marina Sotiropoulos
     */

    public int getChoice() {
        return formView.getIntUserInput(properties.getProperty("CHOICE_MSG"));
    }

    /**
     * Method to create ad options. It displays the view homes page and the ad options.
     * The user can choose to use an existing home, create a new one or go back.
     *
     * @param sessionBean SessionBean object.
     * @return boolean value that represents if the ad is created.
     * @throws InvalidSessionException If the session is invalid.
     * @author Marina Sotiropoulos
     */

    public boolean createAdOptions(SessionBean sessionBean) throws InvalidSessionException {
        formView.clean();
        formView.displayWelcomeMessage(properties.getProperty("CREATE_AD_MSG").toUpperCase());

        int res = viewHomes(sessionBean);

        if (res >= 0 && res < homeBeans.size()) {

            hBean = homeBeans.get(res);

            if (manager.isMaxRoomsReached(hBean)){
                formView.displayMessage(properties.getProperty("MAX_ROOMS_REACHED_MSG"));
                pause();
                return false;
            }
            return adCreationForm(hBean);
        }
        else if (res == homeBeans.size()) {

            return adCreationForm(null);
        }
        else
            return false;

    }

    /**
     * Method to create the ad creation form.
     * It displays the form to create a new ad.
     *
     * @param homeBean HomeBean object.
     * @return boolean value that represents if the ad is created.
     * @author Marina Sotiropoulos
     */

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

            homeImagePath = getPath(properties.getProperty("IMAGE_PATH_REQUEST_MSG"));
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

        roomImagePath = getPath(properties.getProperty("IMAGE_PATH_REQUEST_MSG"));

        formView.displayMessage("1. " + properties.getProperty("PUBLISH_AD_MSG"));
        formView.displayMessage("2. " + properties.getProperty("CANCEL_GO_BACK_MSG"));

        int choice = formView.getIntUserInput(properties.getProperty("PUBLISH_CHOICE_MSG"));

        return choice == 1;
    }

    /**
     * Method to publish the ad.
     * It publishes the ad using the AdManager class.
     *
     * @param sessionBean SessionBean object.
     * @author Marina Sotiropoulos
     */

    public void publishAd(SessionBean sessionBean) {
        HomeBean homeBean;

        if (hBean == null) {
            String address = street + ", " + streetNumber + ", " + city;
            int lift = this.elevator ? 1 : 0;
            Integer[] features = new Integer[]{Integer.parseInt(numBedrooms), Integer.parseInt(numBathrooms), Integer.parseInt(floor), lift};
            homeBean = new HomeBean(address, homeType, homeSurface, features, homeDescription);
        }
        else homeBean = hBean;

        // Set the image of the home
        setImage(homeBean, homeImagePath);

        boolean[] services = new boolean[]{privateBath, balcony, conditioner, tvConnection};
        RoomBean roomBean = new RoomBean(roomType, roomSurface, services, roomDescription);

        // Set the image of the room
        setImage(roomBean, roomImagePath);

        boolean res  = manager.publishAd(sessionBean, homeBean, roomBean, price, Month.fromInt(monthAvailable));

        if (res) {
            formView.displayMessage(properties.getProperty("AD_PUBLISHED_MSG"));
        }
        else formView.displayMessage(properties.getProperty("FAILED_PUBLISH_AD"));
        pause();
    }

    private void setImage(Object bean, String path) {
        try {
            Path p = Paths.get(path);
            if (bean instanceof RoomBean roomBean) {
                roomBean.setImage(Files.readAllBytes(p));
                roomBean.setImageName(new File(path).getName());
            }
            else if (bean instanceof HomeBean homeBean) {
                homeBean.setImage(Files.readAllBytes(p));
                homeBean.setImageName(new File(path).getName());
            }
        } catch (IOException e) {
            formView.displayMessage(properties.getProperty("FAILED_IMAGE_FOUND"));
        }
    }

    /**
     * Method to view ads.
     * It retrieves the ads from the database and displays them.
     *
     * @param sessionBean SessionBean object.
     * @throws InvalidSessionException If the session is invalid.
     * @author Marina Sotiropoulos
     */

    public void viewAds(SessionBean sessionBean) throws InvalidSessionException {
        List<AdBean> adBeans = manager.searchAdsByOwner(sessionBean, new AdBean(null));
        for (AdBean adBean : adBeans) {
            formView.displayMessage(adBean.toString());
        }
    }

    /**
     * Method to view homes.
     * It retrieves the homes from the database and displays them.
     *
     * @param sessionBean SessionBean object.
     * @return int value that represents the user's choice.
     * @throws InvalidSessionException If the session is invalid.
     * @author Marina Sotiropoulos
     */

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

    /**
     * Method to convert the type of home. The types are: Studio Apartment, Two-Bedroom Apartment,
     * Three-Bedroom Apartment, Four-Bedroom Apartment.
     *
     * @param type String value that specifies the type of house
     *             and allows you to associate the correct number of rooms.
     * @return String value that represents the type of home.
     * @author Marina Sotiropoulos
     */

    public String convertTypeHome(String type) {
        switch (type) {
            case "1" -> {
                numBedrooms = "1";
                return properties.getProperty("STUDIO_APARTMENT_MSG");
            }
            case "2" -> {
                numBedrooms = "2";
                return properties.getProperty("TWO_BEDROOM_APARTMENT_MSG");
            }
            case "3" -> {
                numBedrooms = "3";
                return properties.getProperty("THREE_BEDROOM_APARTMENT_MSG");
            }
            case "4" -> {
                numBedrooms = "4";
                return properties.getProperty("FOUR_BEDROOM_APARTMENT_MSG");
            }
            default -> {
                numBedrooms = null;
                return null;
            }
        }
    }

    /**
     * Method to convert the type of room. The types are: Single Room, Double Room.
     *
     * @param type String value that specifies the type of room.
     * @return String value that represents the type of room.
     * @author Marina Sotiropoulos
     */

    public String convertTypeRoom(String type) {
        return switch (type) {
            case "1" -> properties.getProperty("SINGLE_ROOM_MSG");
            case "2" -> properties.getProperty("DOUBLE_ROOM_MSG");
            default -> null;
        };
    }
}