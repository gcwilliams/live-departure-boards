package uk.co.gcwilliams.ldb.model.builder;

/**
 * The builder interface
 *
 * @author Gareth Williams
 */
public interface Builder<T> {

    /**
     * Builds the specified model
     *
     * @return The model
     */
    T build();
}
