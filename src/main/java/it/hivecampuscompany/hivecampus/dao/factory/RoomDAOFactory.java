package it.hivecampuscompany.hivecampus.dao.factory;


import it.hivecampuscompany.hivecampus.dao.RoomDAO;
import it.hivecampuscompany.hivecampus.dao.csv.RoomDAOCSV;
import it.hivecampuscompany.hivecampus.dao.facade.PersistenceType;
import it.hivecampuscompany.hivecampus.dao.mysql.RoomDAOMySql;

/**
 * The RoomDAOFactory class is a factory class responsible for creating
 * instances of the RoomDAO interface based on the specified persistence type.
 *
 * @author Fabio Barchiesi
 */
public class RoomDAOFactory {

    /**
     * Creates an instance of RoomDAO based on the given persistence type.
     *
     * @param persistenceType the type of persistence storage (CSV or MYSQL)
     * @return an instance of RoomDAO corresponding to the specified persistence type
     * @author Fabio Barchiesi
     */
    public RoomDAO getRoomDAO(PersistenceType persistenceType) {
        return switch (persistenceType) {
            case CSV -> createRoomDAOCSV();
            case MYSQL -> createRoomDAOMySql();
        };
    }

    /**
     * Creates an instance of RoomDAOCSV, which is the CSV implementation of RoomDAO.
     *
     * @return an instance of RoomDAOCSV
     * @author Fabio Barchiesi
     */
    private RoomDAO createRoomDAOCSV() {
        return new RoomDAOCSV();
    }

    /**
     * Creates an instance of RoomDAOMySql, which is the MySQL implementation of RoomDAO.
     *
     * @return an instance of RoomDAOMySql
     * @author Fabio Barchiesi
     */
    public RoomDAO createRoomDAOMySql() {
        return new RoomDAOMySql();
    }
}

