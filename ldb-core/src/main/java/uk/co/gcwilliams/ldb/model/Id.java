package uk.co.gcwilliams.ldb.model;

import java.io.Serializable;

/**
 * The ID, a strongly typed ID
 *
 * @author Gareth Williams
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

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this || !(obj == null || (!(obj instanceof Id))) && id.equals(((Id<?>) obj).id);
    }
}