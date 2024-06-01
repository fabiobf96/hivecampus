package it.hivecampuscompany.hivecampus.dao.factory;

/**
 * Factory is an interface for creating Data Access Object (DAO) instances
 * based on the specified persistence type.
 *
 * <p>This interface defines a single method, {@code getDAO}, which returns
 * a DAO instance for the given persistence type. Implementations of this
 * interface should provide the logic for creating the appropriate DAO
 * instances based on different persistence mechanisms (e.g., MySQL, CSV).</p>
 *
 * <p>Usage example:
 * <pre>{@code
 * Factory factory = new SomeDAOFactory();
 * Object dao = factory.getDAO("PERSISTENCE_MYSQL");
 * }</pre></p>
 *
 * @author Fabio Barchiesi
 */
public interface Factory {

    /**
     * Returns a Data Access Object (DAO) instance based on the specified
     * persistence type.
     *
     * @param typePersistence the type of persistence (e.g., "PERSISTENCE_MYSQL", "PERSISTENCE_CSV")
     * @return a DAO instance corresponding to the specified persistence type
     * @throws IllegalArgumentException if the persistence type is not supported
     *
     * @author Fabio Barchiesi
     */
    Object getDAO(String typePersistence) throws IllegalArgumentException;
}
