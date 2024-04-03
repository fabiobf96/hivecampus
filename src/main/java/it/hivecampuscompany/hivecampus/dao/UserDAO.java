package it.hivecampuscompany.hivecampus.dao;

import it.hivecampuscompany.hivecampus.exception.AuthenticateException;
import it.hivecampuscompany.hivecampus.exception.DuplicateRowException;
import it.hivecampuscompany.hivecampus.model.User;

public interface UserDAO {
    void saveUser(User user) throws DuplicateRowException;
    User verifyCredentials(User user) throws AuthenticateException;
}