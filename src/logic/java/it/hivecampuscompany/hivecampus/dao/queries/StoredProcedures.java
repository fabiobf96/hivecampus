package it.hivecampuscompany.hivecampus.dao.queries;

public class StoredProcedures {
    private StoredProcedures(){
        //Default constructor
    }

    public static final String INSERT_ACCOUNT = "{CALL insertAccount(?, ?, ?, ?)}";
    public static final String INSERT_USER = "{CALL insertUser(?, ?, ?)}";
    public static final String RETRIEVE_USER_BY_EMAIL = "{CALL retrieveUserByEmail(?)}";
    public static final String CHECK_USER_EXIST = "{CALL checkUserExist(?)}";
}
