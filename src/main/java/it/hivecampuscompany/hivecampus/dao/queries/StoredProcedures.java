package it.hivecampuscompany.hivecampus.dao.queries;

import it.hivecampuscompany.hivecampus.model.AdStatus;

public class StoredProcedures {
    private StoredProcedures() {
        //Default constructor
    }

    // AccountDAO
    public static final String INSERT_ACCOUNT = "{CALL insertAccount(?, ?, ?, ?)}"; // saveAccount(Account account)
    public static final String RETRIEVE_ACCOUNT_BY_EMAIL = "{CALL retrieveAccountInfoByEmail(?)}"; // retrieveAccountInformationByEmail(String email)

    // UserDAO
    public static final String INSERT_USER = "{CALL insertUser(?, ?, ?)}"; // saveUser(User user)
    public static final String RETRIEVE_USER_BY_CREDENTIALS = "SELECT * FROM Users WHERE email = ? AND password = ?";

    // AdDAO
    public static String retrieveAdsByOwner(AdStatus adStatus) {
        String sql = """
                SELECT *, CASE availability
                                WHEN 'available' THEN 1
                                WHEN 'processing' THEN 2
                                WHEN 'reserved' THEN 3
                                WHEN 'leased' THEN 4
                                WHEN 'unavailable' THEN 5
                                ELSE 0
                                END AS availability_int
                FROM Ad
                WHERE home IN (SELECT idHome FROM home WHERE owner = ?)""";
        if (adStatus != null) {
            sql += " AND availability = ?";
        }
        return sql;
    } // retrieveAdsByOwner(SessionBean sessionBean, AdStatus adStatus)

    public static final String RETRIEVE_AD_BY_ID = "SELECT idAd, home, room, price FROM Ad WHERE idAd = ?"; // retrieveAdByID(int id)
    public static final String UPDATE_AD = "UPDATE Ad SET availability = ?, price = ? WHERE idAd = ?"; // updateAd(Ad ad)
    public static final String RETRIEVE_ADS_BY_FILTERS = "{CALL retrieveAdsByFilters(?, ?, ?)}";
    public static final String PUBLISH_AD = "{CALL publishAd(?, ?, ?, ?, ?)}"; // publishAd(Ad ad)

    // HomeDAO
    public static final String RETRIEVE_HOME_BY_ID =
            "SELECT idHome, ST_Y(coordinates) AS latitude,ST_X(coordinates) AS longitude, address, homeType, homeSurface, homeDescription " +
                    "FROM home " +
                    "WHERE idHome = ?"; // retrieveHomeByID(int id)
    public static final String RETRIEVE_HOMES = "{CALL retrieveHomes()}";
    public static final String RETRIEVE_HOMES_BY_OWNER = "{CALL retrieveHomesByOwner(?)}"; // retrieveHomesByOwner(String ownerEmail)
    public static final String SAVE_HOME = "{CALL saveHome(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}"; // saveHome(HomeBean homeBean, String ownerEmail)
    public static final String SAVE_HOME_IMAGE = "{CALL saveHomeImage(?, ?, ?, ?)}"; // saveHomeImage(String imageName, String imageType, byte[] byteArray, int idHome)
    public static final String IS_HOME_ALREADY_EXISTS = "{CALL isHomeAlreadyExists(?, ?, ?, ?, ?, ?, ?, ?)}"; // isHomeAlreadyExists(HomeBean homeBean)
    public static final String RETRIEVE_HOME_IMAGE = "{CALL retrieveHomeImage(?)}"; // getHomeImage(int idHome)

    // RoomDAO
    public static final String RETRIEVE_ROOM_BY_ID = "SELECT * FROM Room WHERE home = ? AND idRoom = ?"; // retrieveRoomByID(int homeID, int roomID)
    public static final String RETRIEVE_ROOMS_BY_FILTERS = "{CALL retrieveRoomsByFilters(?, ?, ?, ?, ?)}"; // retrieveRoomsByFilters(int homeID, FiltersBean filtersBean)
    public static final String SAVE_ROOM = "{CALL saveRoom(?, ?, ?, ?, ?, ?, ?, ?, ?)}"; // saveRoom(int homeID, RoomBean roomBean)
    public static final String SAVE_ROOM_IMAGE = "{CALL saveRoomImage(?, ?, ?, ?, ?)}"; // saveRoomImage(String imageName, String imageType, byte[] byteArray, int idRoom, int idHome)
    public static final String RETRIEVE_ROOM_IMAGE = "{CALL retrieveRoomImage(?, ?)}"; // getRoomImage(int idRoom, int idHome)
    public static final String GET_ROOMS_ALREADY_PRESENT = "{CALL getRoomsAlreadyPresent(?, ?)}"; // getRoomsAlreadyPresent(int homeID)

    // UniversityDAO
    public static final String RETRIEVE_UNIVERSITY_COORDINATES = "{CALL retrieveUniversityCoordinates(?)}"; // getUniversityCoordinates(String universityName)

    // LeaseRequestDAO
    public static final String RETRIEVE_LEASE_REQUESTS_BY_AD_ID =
            "SELECT idRequest, tenant, startPermanence, typePermanence, message " +
                    "FROM Lease_Request " +
                    "WHERE ad = ? AND requestStatus = ?"; // retrieveLeaseRequestsByAdID(AdBean adBean)
    public static final String RETRIEVE_LEASE_REQUEST_BY_ID =
            "SELECT * " +
                    "FROM Lease_Request " +
                    "WHERE idRequest = ?"; // retrieveLeaseRequestByID(LeaseRequestBean leaseRequestBean)
    public static final String UPDATE_LEASE_REQUEST = "UPDATE Lease_Request SET requestStatus = ? WHERE idRequest = ?"; // updateLeaseRequest(LeaseRequest leaseRequest)
    public static final String SAVE_LEASE_REQUEST = "{CALL saveLeaseRequest(?, ?, ?, ?, ?, ?)}"; // saveLeaseRequest(LeaseRequest leaseRequest)
    public static final String VALID_REQUEST = "{CALL validRequest(?, ?, ?)}"; // validRequest(String email, int id)
    public static final String RETRIEVE_LEASE_REQUESTS_BY_TENANT = "{CALL retrieveLeaseRequestsByTenant(?)}"; // retrieveLeaseRequestsByTenant(SessionBean sessionBean)
    public static final String DELETE_LEASE_REQUEST = "{CALL deleteLeaseRequest(?)}"; // deleteLeaseRequest(LeaseRequest leaseRequest)

    // LeaseDAO
    public static final String SAVE_LEASE = "INSERT INTO Contract (ad, tenant, startPermanence, typePermanence, active, data) VALUES (?, ?, ?, ?, ?, ?)"; // saveLease(Lease lease)
    public static final String RETRIEVE_UNSIGNED_LEASE_BY_TENANT = "SELECT * FROM Contract WHERE tenant = ? AND active = FALSE"; // retrieveUnsignedLeaseByTenant(String email)
    public static final String UPDATE_LEASE = "UPDATE Contract SET active = ? WHERE idContract = ?"; // updateLease(Lease lease)
}
