/**
 * Created by sjuul on 10/27/15.
 */
package dk.zpon.foosball.server;

import dk.zpon.foosball.server.Listeners.ExceptionListener;
import org.glassfish.grizzly.http.server.*;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.moxy.json.MoxyJsonConfig;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

public class Main {
    public static final String BASE_URI = "https://0.0.0.0:8082/";


    public static void main(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("First argument must be location of static data");
        }


        final ResourceConfig resourceConfig = new ResourceConfig().packages("dk.zpon.foosball.server.services")
                .register(ExceptionListener.class).register(new DependencyProvider()).register(MoxyJsonConfig.class);
        final HttpServer httpServer = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), resourceConfig);

        // Register static content server
        StaticHttpHandler staticHttpHandler = new StaticHttpHandler
                (args[0]/*"/home/sjuul/nobackup/foosballjava/frontend/"*/);
        httpServer.getServerConfiguration().addHttpHandler(staticHttpHandler, "/html");

        try {
            httpServer.start();
            System.out.println("Press any key to stop the server...");
            System.in.read();
        } catch (Exception e) {
            System.err.println(e);
        }

    }
}
