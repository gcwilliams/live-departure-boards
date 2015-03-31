package uk.co.gcwilliams.logging;

import org.aopalliance.intercept.MethodInterceptor;
import org.glassfish.hk2.api.Filter;
import org.jvnet.hk2.annotations.Service;
import uk.co.gcwilliams.interceptor.InterceptionServiceAdapter;

import java.lang.reflect.Method;
import java.util.List;

import static java.util.Collections.singletonList;

/**
 * The logging interception service
 *
 * Created by GWilliams on 31/03/2015.
 */
@Service
public class LoggingInterceptionService extends InterceptionServiceAdapter {

    @Override
    public Filter getDescriptorFilter() {
        return (d) -> d.getQualifiers().contains(Loggable.class.getName());
    }

    @Override
    public List<MethodInterceptor> getMethodInterceptors(Method method) {
        return singletonList(new LoggingMethodInterceptor(method.getDeclaringClass()));
    }
}
