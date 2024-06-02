package it.hivecampuscompany.hivecampus.dao.facade;


import it.hivecampuscompany.hivecampus.dao.*;
import it.hivecampuscompany.hivecampus.dao.factory.*;

/**
 * DAOFactoryFacade is a singleton class responsible for providing
 * instances of various Data Access Objects (DAOs) based on the
 * persistence type specified in the configuration file.
 *
 * <p>This class reads the persistence type from a configuration file
 * and initializes the appropriate DAO factory for each DAO type.
 * The available persistence types are "PERSISTENCE_MYSQL" and "PERSISTENCE_CSV".</p>
 *
 * <p>Usage example:
 * <pre>{@code
 * DAOFactoryFacade daoFactoryFacade = DAOFactoryFacade.getInstance();
 * AccountDAO accountDAO = daoFactoryFacade.getAccountDAO();
 * }</pre></p>
 *
 * @author Fabio Barchiesi
 */
public class DAOFactoryFacade {
    private static final DAOFactoryFacade instance = new DAOFactoryFacade();
    private PersistenceType persistenceType;
    private AccountDAO accountDAO;
    private AdDAO adDAO;
    private HomeDAO homeDAO;
    private LeaseContractDAO leaseDAO;
    private LeaseRequestDAO leaseRequestDAO;
    private RoomDAO roomDAO;
    private UniversityDAO universityDAO;
    private UserDAO userDAO;

    /**
     * Private constructor that loads the persistence type from the configuration file.
     * If the configuration file cannot be loaded, the application exits with an error.
     *
     * @author Fabio Barchiesi
     */

    private DAOFactoryFacade() {

    }

    /**
     * Returns the singleton instance of DAOFactoryFacade.
     *
     * @return the singleton instance of DAOFactoryFacade
     * @author Fabio Barchiesi
     */
    public static DAOFactoryFacade getInstance() {
        return instance;
    }

    public void setPersistenceType(PersistenceType persistenceType) {
        this.persistenceType = persistenceType;
    }

    /**
     * Returns an instance of AccountDAO based on the configured persistence type.
     *
     * @return an instance of AccountDAO
     * @throws IllegalArgumentException if the DAO cannot be created
     * @author Fabio Barchiesi
     */
    public AccountDAO getAccountDAO() throws IllegalArgumentException {
        if (accountDAO == null) {
            AccountDAOFactory accountDAOFactory = new AccountDAOFactory();
            accountDAO = accountDAOFactory.getDAO(persistenceType);
        }
        return accountDAO;
    }

    /**
     * Returns an instance of AdDAO based on the configured persistence type.
     *
     * @return an instance of AdDAO
     * @throws IllegalArgumentException if the DAO cannot be created
     * @author Fabio Barchiesi
     */
    public AdDAO getAdDAO() throws IllegalArgumentException {
        if (adDAO == null) {
            AdDAOFactory adDAOFactory = new AdDAOFactory();
            adDAO = adDAOFactory.getDAO(persistenceType);
        }
        return adDAO;
    }

    /**
     * Returns an instance of HomeDAO based on the configured persistence type.
     *
     * @return an instance of HomeDAO
     * @throws IllegalArgumentException if the DAO cannot be created
     * @author Fabio Barchiesi
     */
    public HomeDAO getHomeDAO() throws IllegalArgumentException {
        if (homeDAO == null) {
            HomeDAOFactory homeDAOFactory = new HomeDAOFactory();
            homeDAO = homeDAOFactory.getDAO(persistenceType);
        }
        return homeDAO;
    }

    /**
     * Returns an instance of LeaseContractDAO based on the configured persistence type.
     *
     * @return an instance of LeaseContractDAO
     * @throws IllegalArgumentException if the DAO cannot be created
     * @author Fabio Barchiesi
     */
    public LeaseContractDAO getLeaseContractDAO() throws IllegalArgumentException {
        if (leaseDAO == null) {
            LeaseContractDAOFactory leaseContractDAOFactory = new LeaseContractDAOFactory();
            leaseDAO = leaseContractDAOFactory.getDAO(persistenceType);
        }
        return leaseDAO;
    }

    /**
     * Returns an instance of LeaseRequestDAO based on the configured persistence type.
     *
     * @return an instance of LeaseRequestDAO
     * @throws IllegalArgumentException if the DAO cannot be created
     * @author Fabio Barchiesi
     */
    public LeaseRequestDAO getLeaseRequestDAO() throws IllegalArgumentException {
        if (leaseRequestDAO == null) {
            LeaseRequestDAOFactory leaseRequestDAOFactory = new LeaseRequestDAOFactory();
            leaseRequestDAO = leaseRequestDAOFactory.getDAO(persistenceType);
        }
        return leaseRequestDAO;
    }

    /**
     * Returns an instance of RoomDAO based on the configured persistence type.
     *
     * @return an instance of RoomDAO
     * @throws IllegalArgumentException if the DAO cannot be created
     * @author Fabio Barchiesi
     */
    public RoomDAO getRoomDAO() throws IllegalArgumentException {
        if (roomDAO == null) {
            RoomDAOFactory roomDAOFactory = new RoomDAOFactory();
            roomDAO = roomDAOFactory.getDAO(persistenceType);
        }
        return roomDAO;
    }

    /**
     * Returns an instance of UniversityDAO based on the configured persistence type.
     *
     * @return an instance of UniversityDAO
     * @throws IllegalArgumentException if the DAO cannot be created
     * @author Fabio Barchiesi
     */
    public UniversityDAO getUniversityDAO() throws IllegalArgumentException {
        if (universityDAO == null) {
            UniversityDAOFactory universityDAOFactory = new UniversityDAOFactory();
            universityDAO = universityDAOFactory.getDAO(persistenceType);
        }
        return universityDAO;
    }

    /**
     * Returns an instance of UserDAO based on the configured persistence type.
     *
     * @return an instance of UserDAO
     * @throws IllegalArgumentException if the DAO cannot be created
     * @author Fabio Barchiesi
     */
    public UserDAO getUserDAO() throws IllegalArgumentException {
        if (userDAO == null) {
            UserDAOFactory userDAOFactory = new UserDAOFactory();
            userDAO = userDAOFactory.getDAO(persistenceType);
        }
        return userDAO;
    }
}
