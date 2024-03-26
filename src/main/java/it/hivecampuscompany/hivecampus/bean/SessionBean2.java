package it.hivecampuscompany.hivecampus.bean;

import it.hivecampuscompany.hivecampus.model.LeaseRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SessionBean2 {
    // hashmap chiave valore dove la chiave è l'id e il valore è un oggetto che contiene informazioni quali ruolo, info utente e info ligua, \
    private final HashMap<String, Object> sessionInfo;

    public SessionBean2() {
        sessionInfo = new HashMap<>();
    }

    public void setSessionInfo(String key, Object value) {
        sessionInfo.put(key, value);
    }

    public Object getSessionInfo(String key) {
        return sessionInfo.get(key);
    }

    public void removeSessionInfo(String key) {
        sessionInfo.remove(key);
    }

    public static void main(String[] args) {
        // Creazione di un oggetto SessionBean
        SessionBean2 sessionBean = new SessionBean2();

        List<String> leaseRequests = new ArrayList<>();
        leaseRequests.add("LeaseRequest1");
        leaseRequests.add("LeaseRequest2");
        leaseRequests.add("LeaseRequest3");

        // Aggiunta di informazioni alla sessione
        sessionBean.setSessionInfo("role", "owner");
        sessionBean.setSessionInfo("user", "Pippo Pluto");
        sessionBean.setSessionInfo("language", "en");
        sessionBean.setSessionInfo("leaseRequests", leaseRequests);

        // Recupero delle informazioni dell'utente dal bean di sessione
        String retrievedUser = (String) sessionBean.getSessionInfo("user");

        // Stampa delle informazioni dell'utente
        System.out.println("User: " + retrievedUser);
    }
}