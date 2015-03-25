package uk.co.gcwilliams.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collector;

/**
 * The functional utils
 *
 * Created by GWilliams on 24/03/2015.
 */
public class FunctionalUtils {

    /**
     * The checked function, a functional interface that throws a
     * checked exception
     *
     * @param <TParam> the input parameter
     */
    @FunctionalInterface
    public interface CheckedConsumer<TParam> {

        /**
         * Applies the function to the input value
         *
         * @param param the input param
         */
        void apply(TParam param) throws Exception;
    }

    /**
     * The checked function, a functional interface that throws a
     * checked exception
     *
     * @param <TParam> the input parameter
     * @param <TReturn> the return type
     */
    @FunctionalInterface
    public interface CheckedFunction<TParam, TReturn> {

        /**
         * Applies the function to the input value
         *
         * @param param the input param
         * @return the return value
         */
        TReturn apply(TParam param) throws Exception;
    }

    /**
     * Wraps a checked function, throwing a runtime exception if the checked function fails
     *
     * @param checkedFunction The checked function
     * @param <TParam> the input parameter
     * @param <TReturn> the return type
     * @return a function which catches and throws any checked exception as a runtime exception
     */
    public static <TParam, TReturn> Function<TParam, TReturn> wrapFunction(CheckedFunction<TParam, TReturn> checkedFunction) {
        return in -> {
            try {
                return checkedFunction.apply(in);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
    }

    /**
     * Wraps a checked consumer, throwing a runtime exception if the checked consumer fails
     *
     * @param checkedConsumer The checked consumer
     * @param <TParam> the input parameter
     * @return a function which catches and throws any checked exception as a runtime exception
     */
    public static <TParam> Consumer<TParam> wrapConsumer(CheckedConsumer<TParam> checkedConsumer) {
        return in -> {
            try {
                checkedConsumer.apply(in);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
    }

    /**
     * An immutable list collector
     *
     * @return the immutable list collector
     */
    public static <T> Collector<T, List<T>, List<T>> toImmutableList() {
        return Collector.of(ArrayList::new, List::add, (l, r) -> {
            l.addAll(r);
            return l;
        }, Collections::unmodifiableList);
    }
}
