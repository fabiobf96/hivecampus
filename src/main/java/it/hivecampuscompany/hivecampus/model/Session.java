package it.hivecampuscompany.hivecampus.model;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

public class Session {
    private final int id;
    private final User user;
    private Instant timestamp;
    public Session(User user){
        id = Objects.hash(user.getEmail()); //aggiungi timestamp
        this.user = user;
        timestamp = Instant.now();
    }
    public boolean isValid(){
        Instant currentTime = Instant.now();
        Duration duration = Duration.between(timestamp, currentTime);
        // Controlla se la durata è inferiore o uguale a 30 minuti
        if (duration.toMinutes() <= 30) {
            // Aggiorno il timestamp
            timestamp = Instant.now();
            return true;
        } else {
           return false;
        }
    }
    public int getId(){
        return id;
    }

    public User getUser() {
        return user;
    }
}
