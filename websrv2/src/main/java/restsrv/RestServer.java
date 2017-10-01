package restsrv;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.GracefulShutdownHandler; 
import io.undertow.server.handlers.cache.DirectBufferCache;
import io.undertow.server.handlers.resource.CachingResourceManager;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.server.handlers.resource.ResourceHandler; 
import io.undertow.server.handlers.resource.ResourceManager;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.servlet.api.ServletContainer;

import java.time.Duration;
import javax.servlet.ServletException;
import org.jboss.logging.Logger;

/**
 * RestServer
 */
public final class RestServer {

    static { //runs when the main class is loaded.
        System.setProperty("org.jboss.logging.provider", "log4j2");
        System.setProperty("Log4jContextSelector", "org.apache.logging.log4j.core.async.AsyncLoggerContextSelector");
    }
    private static final Logger LOGGER = Logger.getLogger(RestServer.class);
    private static final long HTTP_SHUTDOWN_GRACE_PERIOD_MILLIS = 120000L;     
    private final Undertow server;
    private final ServletContainer container;
    private final GracefulShutdownHandler shutdownHandler;     
    private final int port;

    RestServer(int port) {
        this.port = port;
        this.container = Servlets.defaultContainer();

        shutdownHandler = new GracefulShutdownHandler(createStaticResourceHandler(createServletHandler()));

        this.server = Undertow
            .builder()
            .addHttpListener(this.port, "localhost")
            .setHandler(shutdownHandler)
            .build();
    }

    HttpHandler createServletHandler() {
        DeploymentInfo di = Servlets.deployment()
            .setClassLoader(RestServer.class.getClassLoader())
            .setContextPath("/")
            .setDeploymentName("REST Server")
            .addServlets(Servlets.servlet("helloServlet", restsrv.HelloServlet.class)
                     .addMapping("/hello"));

        DeploymentManager manager = container.addDeployment(di);
        manager.deploy();

        try {
            return Handlers.path().addPrefixPath("/", manager.start());
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }

    HttpHandler createStaticResourceHandler(HttpHandler next) {
        final ResourceManager staticResources = new ClassPathResourceManager(RestServer.class.getClassLoader(), "static");

        // Cache tuning is copied from Undertow unit tests.
        final ResourceManager cachedResources =
                new CachingResourceManager(100, 65536,
                                           new DirectBufferCache(1024, 10, 10480),
                                           staticResources,
                                           (int)Duration.ofDays(1).getSeconds());
        final ResourceHandler resourceHandler = new ResourceHandler(cachedResources, next);
        resourceHandler.setWelcomeFiles("index.html");
        resourceHandler.setDirectoryListingEnabled(true);
        return resourceHandler;
    }

    public void run() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));

        LOGGER.info("Starting RestServer @ localhost:" + this.port);
        server.start();
    }

    public void shutdown() {
        try {
            /* NOTE: For some reason, with the new aync logger active
                     this message will not reach the log before termination.
                     So, for now, I'm just printing a message to STDOUT.
             */
            LOGGER.info("Stopping HTTP server.");
            System.out.println("Stopping HTTP server.");
            shutdownHandler.shutdown(); 
            shutdownHandler.awaitShutdown(HTTP_SHUTDOWN_GRACE_PERIOD_MILLIS); 
            server.stop(); 
        } catch (final Exception ie) { 
            Thread.currentThread().interrupt(); 
        }
    }

    static int serverPort(final String[] args) {
        Integer port;
        String errMsg = "Invalid port number: ";

        // from env
        String arg = System.getenv("PORT");
        if (arg != null) {
            try {
                return Integer.parseInt(arg);
            } catch (NumberFormatException e) {
                LOGGER.error(errMsg + arg);
            }
        }

        // from args
        if (args.length > 0) {
            arg = args[0];
            try {
                return Integer.parseInt(arg);
            } catch (NumberFormatException e) {
                LOGGER.error(errMsg + arg);
            }
        }

        // default to 8080
        return 8080;
    }

    public static void main(final String[] args) {
        int port = serverPort(args);
        new RestServer(port).run();
    }
}
