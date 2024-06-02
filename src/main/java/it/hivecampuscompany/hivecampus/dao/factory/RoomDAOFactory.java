package it.hivecampuscompany.hivecampus.dao.factory;


import it.hivecampuscompany.hivecampus.dao.RoomDAO;
import it.hivecampuscompany.hivecampus.dao.csv.RoomDAOCSV;
import it.hivecampuscompany.hivecampus.dao.mysql.RoomDAOMySql;

public class RoomDAOFactory implements Factory {
    @Override
    public RoomDAO getDAO(String typePersistence) throws IllegalArgumentException {
        return switch (typePersistence) {
            case "csv" -> createRoomDAOCSV();
            case "mysql" -> createRoomDAOMySql();
            default -> throw new IllegalArgumentException("Unsupported persistence type: " + typePersistence);
        };
    }

    private RoomDAO createRoomDAOCSV() {
        return new RoomDAOCSV();
    }

    public RoomDAO createRoomDAOMySql() {
        return new RoomDAOMySql();
    }
}
