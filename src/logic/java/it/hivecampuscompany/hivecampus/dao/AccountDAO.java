package it.hivecampuscompany.hivecampus.dao;

import it.hivecampuscompany.hivecampus.bean.AccountBean;
import it.hivecampuscompany.hivecampus.model.Account;

public interface AccountDAO {
    void saveAccount(Account account);
}
