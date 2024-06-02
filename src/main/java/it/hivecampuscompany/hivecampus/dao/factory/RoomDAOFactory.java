package it.hivecampuscompany.hivecampus.dao.factory;


import it.hivecampuscompany.hivecampus.dao.RoomDAO;
import it.hivecampuscompany.hivecampus.dao.csv.RoomDAOCSV;
import it.hivecampuscompany.hivecampus.dao.facade.PersistenceType;
import it.hivecampuscompany.hivecampus.dao.mysql.RoomDAOMySql;

public class RoomDAOFactory implements Factory {
    @Override
    public RoomDAO getDAO(PersistenceType persistenceType) {
        return switch (persistenceType) {
            case CSV -> createRoomDAOCSV();
            case MYSQL -> createRoomDAOMySql();
        };
    }

    private RoomDAO createRoomDAOCSV() {
        return new RoomDAOCSV();
    }

    public RoomDAO createRoomDAOMySql() {
        return new RoomDAOMySql();
    }
}
