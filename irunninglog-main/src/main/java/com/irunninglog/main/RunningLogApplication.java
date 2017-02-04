package com.irunninglog.main;

import com.irunninglog.api.security.IAuthenticationService;
import com.irunninglog.spring.context.ContextConfiguration;
import com.irunninglog.vertx.endpoint.EndpointVerticle;
import com.irunninglog.vertx.http.ServerVerticle;
import com.irunninglog.vertx.security.AuthnVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;
import uk.org.lidalia.sysoutslf4j.context.SysOutOverSLF4J;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

final class RunningLogApplication {

    private static final Logger LOG = LoggerFactory.getLogger(RunningLogApplication.class);

    void start(Vertx vertx, Handler<AsyncResult<String>> asyncResultHandler) {
        LOG.info("start:start");

        LOG.info("start:logging:before");
        logging();
        LOG.info("start:logging:after");

        LOG.info("start:applicationContext:before");
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ContextConfiguration.class);
        LOG.info("start:applicationContext:after");

        LOG.info("start:server:before");

        Environment environment = applicationContext.getEnvironment();
        int port = environment.getProperty("httpServer.listenPort", Integer.class, 8080);
        LOG.info("start:server:listenPort:{}", port);

        vertx.deployVerticle(new ServerVerticle(port, event -> LOG.info("start:server:listening")), stringAsyncResult -> {
            LOG.info("start:server:after");
            try {
                verticles(applicationContext, vertx, asyncResultHandler);
            } catch (Exception ex) {
                String errMsg = "Unable to deploy verticles";
                LOG.error(errMsg, ex);
                throw new IllegalStateException(errMsg, ex);
            }
        });
    }

    private void verticles(ApplicationContext applicationContext, Vertx vertx, Handler<AsyncResult<String>> asyncResultHandler) throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException {
        List<AbstractVerticle> list = new ArrayList<>();

        LOG.info("verticles:before");

        Reflections reflections = new Reflections("com.irunninglog");

        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(EndpointVerticle.class);

        for (Class<?> clazz : classes) {
            LOG.info("verticles:{}", clazz);

            Constructor [] constructors = clazz.getConstructors();
            if (constructors.length != 1) {
                LOG.error("verticles:can't find constructor:{}:{}", clazz, constructors.length);
            } else {
                Constructor constructor = constructors[0];
                Object [] args = new Object[constructor.getParameters().length];
                for (int i = 0; i < constructor.getParameters().length; i++) {
                    args[i] = applicationContext.getBean(constructor.getParameters()[i].getType());
                }

                AbstractVerticle verticle = (AbstractVerticle) clazz.getConstructors()[0].newInstance(args);

                LOG.info("verticles:{}", verticle);

                list.add(verticle);
            }
        }

        LOG.info("verticles:will deploy {} verticles", list.size());

        vertx.deployVerticle(new AuthnVerticle(applicationContext.getBean(IAuthenticationService.class)),
                stringAsyncResult -> deployVerticles(list.iterator(), asyncResultHandler, vertx));
    }

    private void deployVerticles(Iterator<AbstractVerticle> iterator, Handler<AsyncResult<String>> asyncResultHandler, Vertx vertx) {
        if (iterator.hasNext()) {
            AbstractVerticle verticle = iterator.next();

            LOG.info("deployVerticle:{}", verticle);

            if (iterator.hasNext()) {
                vertx.deployVerticle(verticle, stringAsyncResult -> deployVerticles(iterator, asyncResultHandler, vertx));
            } else {
                vertx.deployVerticle(verticle, asyncResultHandler);
            }
        }
    }

    private void logging() {
        SysOutOverSLF4J.sendSystemOutAndErrToSLF4J();
        System.out.println("logging:System.out");
        System.err.println("logging:System.err");

        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        java.util.logging.Logger julLogger = java.util.logging.Logger.getLogger(Main.class.getName());
        julLogger.info("logging:JUL");

        org.apache.log4j.Logger log4JLogger = org.apache.log4j.Logger.getLogger(Main.class);
        log4JLogger.info("logging:Log4J");

        Log jclLogger = LogFactory.getLog(Main.class);
        jclLogger.info("logging:JCL");
    }

}
