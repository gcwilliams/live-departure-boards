package uk.co.gcwilliams.logging;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The logging method interceptor
 *
 * Created by GWilliams on 31/03/2015.
 */
class LoggingMethodInterceptor implements MethodInterceptor {

    private final Logger logger;

    /**
     * Default constructor
     *
     * @param clazz the class to log about
     */
    LoggingMethodInterceptor(Class<?> clazz) {
        logger = LoggerFactory.getLogger(clazz);
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        // 1. trace before call of method
        logger.trace("before invocation of {}", invocation.getMethod().getName());

        Object result;
        try {
            result = invocation.proceed();
        } catch (Throwable throwable) {
            // 2. log all exceptions
            logger.error("error during invocation of {}", invocation.getMethod().getName(), throwable);
            throw throwable;
        }

        // 3. debug after call of method with result
        logger.debug("invocation of {} with result of {}", invocation.getMethod().getName(), result);

        return result;
    }
}
