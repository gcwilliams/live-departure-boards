package uk.co.gcwilliams.ldb.model.builder;

import org.joda.time.DateTime;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.co.gcwilliams.ldb.model.Destination;
import uk.co.gcwilliams.ldb.model.Service;
import uk.co.gcwilliams.ldb.model.Station;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * The service builder tests
 *
 * Created by GWilliams on 22/03/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class ServiceBuilderTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Mock
    private DestinationBuilder destinationBuilder;

    @Mock
    private Destination destination;

    @Mock
    private StationBuilder originBuilder;

    @Mock
    private Station origin;

    @Test
    public void testValidationServiceIdIsNull() {

        // arrange
        ServiceBuilder builder = new ServiceBuilder()
            .setDestination(destinationBuilder)
            .setOrigin(originBuilder)
            .setOperator("Southern")
            .setOperatorCode("STH");

        // act
        exception.expect(IllegalStateException.class);
        builder.build();
    }

    @Test
    public void testValidationServiceIdIsEmpty() {

        // arrange
        ServiceBuilder builder = new ServiceBuilder()
            .setDestination(destinationBuilder)
            .setOrigin(originBuilder)
            .setOperator("Southern")
            .setOperatorCode("STH")
            .setServiceId("");

        // act
        exception.expect(IllegalStateException.class);
        builder.build();
    }

    @Test
    public void testValidationOperatorCodeIsNull() {

        // arrange
        ServiceBuilder builder = new ServiceBuilder()
            .setDestination(destinationBuilder)
            .setOrigin(originBuilder)
            .setOperator("Southern")
            .setServiceId("123");

        // act
        exception.expect(IllegalStateException.class);
        builder.build();
    }

    @Test
    public void testValidationOperatorCodeIsEmpty() {

        // arrange
        ServiceBuilder builder = new ServiceBuilder()
            .setDestination(destinationBuilder)
            .setOrigin(originBuilder)
            .setOperator("Southern")
            .setOperatorCode("")
            .setServiceId("123");

        // act
        exception.expect(IllegalStateException.class);
        builder.build();
    }

    @Test
    public void testValidationOperatorIsNull() {

        // arrange
        ServiceBuilder builder = new ServiceBuilder()
            .setDestination(destinationBuilder)
            .setOrigin(originBuilder)
            .setOperatorCode("STH")
            .setServiceId("123");

        // act
        exception.expect(IllegalStateException.class);
        builder.build();
    }

    @Test
    public void testValidationOperatorIsEmpty() {

        // arrange
        ServiceBuilder builder = new ServiceBuilder()
            .setDestination(destinationBuilder)
            .setOrigin(originBuilder)
            .setOperator("")
            .setOperatorCode("STH")
            .setServiceId("123");

        // act
        exception.expect(IllegalStateException.class);
        builder.build();
    }

    @Test
    public void testValidationOriginIsNull() {

        // arrange
        ServiceBuilder builder = new ServiceBuilder()
            .setDestination(destinationBuilder)
            .setOperator("Southern")
            .setOperatorCode("STH")
            .setServiceId("123");

        // act
        exception.expect(IllegalStateException.class);
        builder.build();
    }

    @Test
    public void testValidationDestinationIsEmpty() {

        // arrange
        ServiceBuilder builder = new ServiceBuilder()
            .setOrigin(originBuilder)
            .setOperator("Southern")
            .setOperatorCode("STH")
            .setServiceId("123");

        // act
        exception.expect(IllegalStateException.class);
        builder.build();
    }

    @Test
    public void testBuilderCreatesService() {

        // arrange
        when(destinationBuilder.build()).thenReturn(destination);
        when(originBuilder.build()).thenReturn(origin);
        ServiceBuilder builder = new ServiceBuilder()
            .setDestination(destinationBuilder)
            .setOrigin(originBuilder)
            .setOperator("Southern")
            .setOperatorCode("STH")
            .setServiceId("123")
            .setEstimatedTimeOfArrival(DateTime.now())
            .setEstimatedTimeOfDeparture(DateTime.now())
            .setStandardTimeOfArrival(DateTime.now())
            .setStandardTimeOfDeparture(DateTime.now())
            .setPlatform(10);

        // act
        Service service = builder.build();

        // act
        assertThat(service, notNullValue());
        assertThat(service.getDestination(), instanceOf(Destination.class));
        assertThat(service.getOrigin(), instanceOf(Station.class));
        assertThat(service.getServiceId().get(), equalTo("123"));
        assertThat(service.getOperator(), equalTo("Southern"));
        assertThat(service.getOperatorCode(), equalTo("STH"));
        assertThat(service.getEstimatedTimeOfArrival(), notNullValue());
        assertThat(service.getEstimatedTimeOfArrival().isPresent(), equalTo(true));
        assertThat(service.getEstimatedTimeOfDeparture(), notNullValue());
        assertThat(service.getEstimatedTimeOfDeparture().isPresent(), equalTo(true));
        assertThat(service.getStandardTimeOfArrival(), notNullValue());
        assertThat(service.getStandardTimeOfArrival().isPresent(), equalTo(true));
        assertThat(service.getStandardTimeOfDeparture(), notNullValue());
        assertThat(service.getStandardTimeOfDeparture().isPresent(), equalTo(true));
    }
}