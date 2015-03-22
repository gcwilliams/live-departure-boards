package uk.co.gcwilliams.ldb.model;

import java.io.Serializable;

/**
 * The ID, a strongly typed ID
 *
 * @author Gareth Williams (466567)
 */
public class Id<T> implements Serializable {

    private final String id;

    /**
     * The ID
     *
     * @param id The ID
     */
    public Id(String id) {
        this.id = id;
    }

    /**
     * Gets the ID
     *
     * @return The ID
     */
    public String get() {
        return id;
    }
}
