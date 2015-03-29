package uk.co.gcwilliams.properties;

import javax.inject.Qualifier;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The LDB key qualifier
 *
 * Created by GWilliams on 27/03/2015.
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.TYPE, ElementType.PARAMETER })
public @interface LdbKey { }
