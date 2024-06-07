package it.hivecampuscompany.hivecampus.dao.facade;


import it.hivecampuscompany.hivecampus.dao.*;
import it.hivecampuscompany.hivecampus.dao.factory.*;

/**
 * DAOFactoryFacade is a singleton class responsible for providing
 * instances of various Data Access Objects (DAOs) based on the
 * persistence type specified at runtime.
 *
 * <p>This class allows the persistence type to be set dynamically,
 * and provides methods to obtain instances of different DAOs. The
 * persistence type can be one of the predefined types in {@link PersistenceType}.</p>
 *
 * <p>Usage example:
 * <pre>{@code
 * DAOFactoryFacade daoFactoryFacade = DAOFactoryFacade.getInstance();
 * daoFactoryFacade.setPersistenceType(PersistenceType.MYSQL);
 * AccountDAO accountDAO = daoFactoryFacade.getAccountDAO();
 * }</pre></p>
 *
 * @author Fabio Baarchiesi
 */
public class DAOFactoryFacade {
    private static DAOFactoryFacade instance;
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
     * Private constructor to prevent instantiation.
     */
    private DAOFactoryFacade() {
    }

    /**
     * Returns the singleton instance of DAOFactoryFacade.
     *
     * @return the singleton instance of DAOFactoryFacade
     * @author Fabio Baarchiesi
     */
    public static synchronized DAOFactoryFacade getInstance() {
        if (instance == null) {
            instance = new DAOFactoryFacade();
        }
        return instance;
    }

    /**
     * Sets the persistence type to be used by the DAO factories.
     *
     * @param persistenceType the persistence type to set
     * @author Fabio Baarchiesi
     */
    public void setPersistenceType(PersistenceType persistenceType) {
        this.persistenceType = persistenceType;
    }

    /**
     * Returns an instance of {@link AccountDAO} based on the configured persistence type.
     *
     * @return an instance of {@link AccountDAO}
     * @author Fabio Baarchiesi
     */
    public AccountDAO getAccountDAO() {
        if (accountDAO == null) {
            AccountDAOFactory accountDAOFactory = new AccountDAOFactory();
            accountDAO = accountDAOFactory.getAccountDAO(persistenceType);
        }
        return accountDAO;
    }

    /**
     * Returns an instance of {@link AdDAO} based on the configured persistence type.
     *
     * @return an instance of {@link AdDAO}
     * @author Fabio Baarchiesi
     */
    public AdDAO getAdDAO() {
        if (adDAO == null) {
            AdDAOFactory adDAOFactory = new AdDAOFactory();
            adDAO = adDAOFactory.getAdDAO(persistenceType);
        }
        return adDAO;
    }

    /**
     * Returns an instance of {@link HomeDAO} based on the configured persistence type.
     *
     * @return an instance of {@link HomeDAO}
     * @author Fabio Baarchiesi
     */
    public HomeDAO getHomeDAO() {
        if (homeDAO == null) {
            HomeDAOFactory homeDAOFactory = new HomeDAOFactory();
            homeDAO = homeDAOFactory.getHomeDAO(persistenceType);
        }
        return homeDAO;
    }

    /**
     * Returns an instance of {@link LeaseContractDAO} based on the configured persistence type.
     *
     * @return an instance of {@link LeaseContractDAO}
     * @author Fabio Baarchiesi
     */
    public LeaseContractDAO getLeaseContractDAO() {
        if (leaseDAO == null) {
            LeaseContractDAOFactory leaseContractDAOFactory = new LeaseContractDAOFactory();
            leaseDAO = leaseContractDAOFactory.getLeaseContractDAO(persistenceType);
        }
        return leaseDAO;
    }

    /**
     * Returns an instance of {@link LeaseRequestDAO} based on the configured persistence type.
     *
     * @return an instance of {@link LeaseRequestDAO}
     * @author Fabio Baarchiesi
     */
    public LeaseRequestDAO getLeaseRequestDAO() {
        if (leaseRequestDAO == null) {
            LeaseRequestDAOFactory leaseRequestDAOFactory = new LeaseRequestDAOFactory();
            leaseRequestDAO = leaseRequestDAOFactory.getLeaseRequestDAO(persistenceType);
        }
        return leaseRequestDAO;
    }

    /**
     * Returns an instance of {@link RoomDAO} based on the configured persistence type.
     *
     * @return an instance of {@link RoomDAO}
     * @author Fabio Baarchiesi
     */
    public RoomDAO getRoomDAO() {
        if (roomDAO == null) {
            RoomDAOFactory roomDAOFactory = new RoomDAOFactory();
            roomDAO = roomDAOFactory.getRoomDAO(persistenceType);
        }
        return roomDAO;
    }

    /**
     * Returns an instance of {@link UniversityDAO} based on the configured persistence type.
     *
     * @return an instance of {@link UniversityDAO}
     * @author Fabio Baarchiesi
     */
    public UniversityDAO getUniversityDAO() {
        if (universityDAO == null) {
            UniversityDAOFactory universityDAOFactory = new UniversityDAOFactory();
            universityDAO = universityDAOFactory.getUniversityDAO(persistenceType);
        }
        return universityDAO;
    }

    /**
     * Returns an instance of {@link UserDAO} based on the configured persistence type.
     *
     * @return an instance of {@link UserDAO}
     * @author Fabio Baarchiesi
     */
    public UserDAO getUserDAO() {
        if (userDAO == null) {
            UserDAOFactory userDAOFactory = new UserDAOFactory();
            userDAO = userDAOFactory.getUserDAO(persistenceType);
        }
        return userDAO;
    }
}

