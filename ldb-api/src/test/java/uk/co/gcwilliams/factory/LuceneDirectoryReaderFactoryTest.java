package uk.co.gcwilliams.factory;

import org.apache.lucene.index.IndexReader;
import org.junit.Test;
import org.jvnet.hk2.annotations.Service;
import uk.co.gcwilliams.ldb.model.StationCode;
import uk.co.gcwilliams.ldb.model.builder.StationCodeBuilder;

import javax.inject.Singleton;
import java.io.IOException;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

/**
 * The lucene directory reader factory tests
 *
 * Created by GWilliams on 30/03/2015.
 */
public class LuceneDirectoryReaderFactoryTest {

    @Test
    public void isService() {

        // act
        Service service = LuceneDirectoryReaderFactory.class.getAnnotation(Service.class);

        // assert
        assertThat(service, instanceOf(Service.class));
    }

    @Test
    public void isSingleton() throws NoSuchMethodException {

        // act
        Singleton singleton = LuceneDirectoryReaderFactory.class.getMethod("provide").getAnnotation(Singleton.class);

        // assert
        assertThat(singleton, instanceOf(Singleton.class));
    }

    @Test
    public void providesService() throws IOException {

        // act
        List<StationCode> codes = singletonList(new StationCodeBuilder().setName("Three Bridges").setStationId("TBD").build());
        LuceneDirectoryReaderFactory factory = new LuceneDirectoryReaderFactory(codes);

        // act
        IndexReader reader = factory.provide();

        // assert
        assertThat(reader, instanceOf(IndexReader.class));
    }
}
