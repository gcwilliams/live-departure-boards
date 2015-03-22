package uk.co.gcwilliams.ldb.model.builder;

import com.google.common.base.Function;

/**
 * The builder transformation for use with {@link com.google.common.collect.Iterables#transform(Iterable, com.google.common.base.Function)}
 *
 * @author Gareth Williams (466567)
 */
class BuilderTransformation<T> implements Function<Builder<T>, T> {
    @Override
    public T apply(Builder<T> input) {
        return input.build();
    }
}