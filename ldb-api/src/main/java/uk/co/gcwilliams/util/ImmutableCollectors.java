package uk.co.gcwilliams.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;

/**
 * The immutable collectors
 *
 * Created by GWilliams on 27/03/2015.
 */
public class ImmutableCollectors {

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
