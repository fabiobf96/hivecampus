package it.hivecampuscompany.hivecampus.logic.dao;

import it.hivecampuscompany.hivecampus.logic.model.Home;

import java.util.List;

public interface HomeDAO {
    List<Home> retrieveHomesByEmail(String email);
    List<Home> retrieveHomesByUniversity(String universityName);
}
