package uk.co.gcwilliams.interceptor;

import org.aopalliance.intercept.ConstructorInterceptor;
import org.aopalliance.intercept.MethodInterceptor;
import org.glassfish.hk2.api.Filter;
import org.glassfish.hk2.api.InterceptionService;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * The interception service tests
 *
 * Created by GWilliams on 01/04/2015.
 */
public class InterceptionServiceAdapterTest {

    @Test
    public void descriptorFilterReturnTrue() {

        // arrange
        InterceptionService service = new InterceptionServiceAdapter() { };

        // act
        Filter filter = service.getDescriptorFilter();

        // assert
        assertThat(filter.matches(null), equalTo(true));
    }

    @Test
    public void constructorInterceptorsReturnsNull() {

        // arrange
        InterceptionService service = new InterceptionServiceAdapter() { };

        // act
        List<ConstructorInterceptor> interceptors = service.getConstructorInterceptors(null);

        // assert
        assertThat(interceptors, nullValue());
    }

    @Test
    public void methodInterceptorsReturnsNull() {

        // arrange
        InterceptionService service = new InterceptionServiceAdapter() { };

        // act
        List<MethodInterceptor> interceptors = service.getMethodInterceptors(null);

        // assert
        assertThat(interceptors, nullValue());
    }
}