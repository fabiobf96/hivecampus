package it.hivecampuscompany.hivecampus.boundary;

import it.hivecampuscompany.hivecampus.exception.MockOpenStreetMapAPIException;
import it.hivecampuscompany.hivecampus.mockapi.MockAPI;
import it.hivecampuscompany.hivecampus.view.utility.LanguageLoader;
import org.apache.http.client.fluent.Request;

import java.io.IOException;
import java.util.Properties;

public class OpenStreetMapApiBoundary {

    private final Properties properties = LanguageLoader.getLanguageProperties();
    public String getCoordinates(String address) {
        return null;
    }

    public byte[] getMap(String address) throws MockOpenStreetMapAPIException {
        MockAPI.start();
        MockAPI.mockOpenStreetMapAPI();
        try {
            return  Request.Get("http://localhost:8080/get-map")
                    .execute().returnContent().asBytes();
        } catch (IOException e) {
            throw new MockOpenStreetMapAPIException(properties.getProperty("FAILED_TO_CONNECT_TO_SERVER") + address);
        } finally {
            MockAPI.shutdown();
        }
    }
}
