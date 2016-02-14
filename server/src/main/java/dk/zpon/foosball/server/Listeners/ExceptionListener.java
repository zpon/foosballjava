package dk.zpon.foosball.server.Listeners;

import org.glassfish.jersey.server.ExtendedUriInfo;
import org.glassfish.jersey.server.monitoring.ApplicationEvent;
import org.glassfish.jersey.server.monitoring.ApplicationEventListener;
import org.glassfish.jersey.server.monitoring.RequestEvent;
import org.glassfish.jersey.server.monitoring.RequestEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExceptionListener implements ApplicationEventListener {

    @Override
    public void onEvent(ApplicationEvent event) {

    }

    @Override
    public RequestEventListener onRequest(RequestEvent requestEvent) {
        return new ExceptionRequestEventListener();
    }

    public static class ExceptionRequestEventListener implements RequestEventListener {
        private final Logger LOGGER;

        public ExceptionRequestEventListener() {
            LOGGER = LoggerFactory.getLogger(getClass());
        }

        @Override
        public void onEvent(RequestEvent event) {
            switch (event.getType()) {
                case ON_EXCEPTION:
                    Throwable t = event.getException();
                    final ExtendedUriInfo uriInfo = event.getUriInfo();
                    System.out.println(event.getContainerRequest().getRequestUri());
                    LOGGER.error("Found exception for requestType: " + uriInfo.getAbsolutePath(), t);
                    break;
            }
        }
    }
}