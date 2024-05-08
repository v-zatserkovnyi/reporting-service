package com.example.reporting.support;

import com.example.reporting.support.ProfileMethod.Level;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;

@Aspect
@Component
public class ProfileMethodAspect implements Ordered {

    public static final String START_TRACE_MESSAGE = "-> tag: {}, method: {}";
    public static final String END_TRACE_MESSAGE = "<- tag: {}, method: {} executed at {} ms";

    private static final String AROUND_POINTCUT = "@annotation(com.example.reporting.support.ProfileMethod)";
    private static final Map<Level, BiFunction<Logger, Object[], Consumer<String>>> LOGGER_STRATEGY = new EnumMap<>(Level.class);

    // @formatter:off
    static {
        LOGGER_STRATEGY.put(Level.INFO, (logger, values) -> message -> logger.info(message, values));
        LOGGER_STRATEGY.put(Level.DEBUG, (logger, values) -> message -> logger.debug(message, values));
        LOGGER_STRATEGY.put(Level.ERROR, (logger, values) -> message -> logger.error(message, values));
        LOGGER_STRATEGY.put(Level.TRACE, (logger, values) -> message -> logger.trace(message, values));
    }
    // @formatter:on

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    @Around(AROUND_POINTCUT)
    public Object aroundCall(final ProceedingJoinPoint call) throws Throwable {
        final Object result;
        final Method method = getMethod(call);
        final ProfileMethod profileMethod = method.getAnnotation(ProfileMethod.class);
        if (profileMethod.enabled()) {
            result = proceedAndWatch(call, method, profileMethod);
        } else {
            result = call.proceed();
        }
        return result;
    }

    private static Object proceedAndWatch(final ProceedingJoinPoint call, final Method method,
                                          final ProfileMethod annotation) throws Throwable {
        final Logger logger = LoggerFactory.getLogger(call.getTarget().getClass());
        before(method, annotation, logger);
        final StopWatch watcher = new StopWatch();
        watcher.start();

        try {
            return call.proceed();
        } finally {
            trace(method, annotation, logger, watcher);
        }
    }

    private static void before(final Method method, final ProfileMethod annotation, final Logger logger) {
        Optional.ofNullable(LOGGER_STRATEGY.get(annotation.level()))
                .ifPresent(i ->
                        i.apply(
                            logger,
                            new Object[]{
                                    StringUtils.defaultIfBlank(annotation.tag(), method.getDeclaringClass().getSimpleName()), method.getName()})
                                .accept(START_TRACE_MESSAGE));
    }

    private static void trace(final Method method, final ProfileMethod annotation, final Logger logger, final StopWatch watcher) {
        Optional.ofNullable(LOGGER_STRATEGY.get(annotation.level()))
                .ifPresent(i ->
                        i.apply(
                            logger,
                            new Object[]{
                                    StringUtils.defaultIfBlank(annotation.tag(), method.getDeclaringClass().getSimpleName()), method.getName(),
                                    watcher.getTime()})
                                .accept(END_TRACE_MESSAGE));
    }

    private static Method getMethod(final ProceedingJoinPoint call) {
        return ((MethodSignature) call.getSignature()).getMethod();
    }
}
