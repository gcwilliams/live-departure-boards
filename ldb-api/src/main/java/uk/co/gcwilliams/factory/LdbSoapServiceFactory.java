package uk.co.gcwilliams.factory;

import org.glassfish.hk2.api.Factory;
import uk.co.gcwilliams.ldb.stubs.LDBServiceSoap;
import uk.co.gcwilliams.ldb.stubs.Ldb;

import javax.inject.Singleton;

/**
 * The LDB soap service factor
 *
 * Created by GWilliams on 27/03/2015.
 */
public class LdbSoapServiceFactory implements Factory<LDBServiceSoap> {

    @Override
    @Singleton
    public LDBServiceSoap provide() {
        return new Ldb().getLDBServiceSoap12();
    }

    @Override
    public void dispose(LDBServiceSoap ldbServiceSoap) { }
}
