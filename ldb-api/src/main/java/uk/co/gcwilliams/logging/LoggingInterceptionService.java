package uk.co.gcwilliams.logging;

import org.aopalliance.intercept.MethodInterceptor;
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
    public List<MethodInterceptor> getMethodInterceptors(Method method) {
        if (method.getAnnotation(Loggable.class) != null) {
            return singletonList(new LoggingMethodInterceptor(method.getDeclaringClass()));
        }
        return null;
    }
}
