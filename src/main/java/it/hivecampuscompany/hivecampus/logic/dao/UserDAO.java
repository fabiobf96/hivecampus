package it.hivecampuscompany.hivecampus.logic.dao;

import it.hivecampuscompany.hivecampus.logic.exception.DuplicateRowException;
import it.hivecampuscompany.hivecampus.logic.model.User;

public interface UserDAO {
    void saveUser(User user) throws DuplicateRowException;
}
