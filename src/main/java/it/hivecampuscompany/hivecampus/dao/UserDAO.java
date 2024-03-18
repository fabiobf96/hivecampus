package it.hivecampuscompany.hivecampus.dao;

import it.hivecampuscompany.hivecampus.exception.DuplicateRowException;
import it.hivecampuscompany.hivecampus.exception.InvalidEmailException;
import it.hivecampuscompany.hivecampus.model.User;

public interface UserDAO {
    void saveUser(User user) throws DuplicateRowException;
    User retrieveUserByEmail(String email) throws InvalidEmailException;
}
