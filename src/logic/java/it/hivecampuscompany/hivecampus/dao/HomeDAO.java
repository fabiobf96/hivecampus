package it.hivecampuscompany.hivecampus.dao;

import it.hivecampuscompany.hivecampus.model.Home;

import java.util.List;

public interface HomeDAO {
    List<Home> retrieveHomesByEmail(String email);
    List<Home> retrieveHomesByUniversity(String universityName);
}
