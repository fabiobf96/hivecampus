package it.hivecampuscompany.hivecampus.model;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

public class Session {
    private final int id;
    private final User user;
    private Instant timestamp;
    private int timer = 900*1000;
    public Session(User user){
        timestamp = Instant.now();
        id = Objects.hash(user.getEmail()) + Objects.hash(timestamp);
        this.user = user;
    }
    // constructor created for testing
    public Session(User user, int timer) {
        this(user);
        this.timer = timer;
    }
    public boolean isValid(){
        Instant currentTime = Instant.now();
        Duration duration = Duration.between(timestamp, currentTime);
        // Controlla se la durata è inferiore a 15 minuti
        if (duration.toMillis() < timer) {
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
