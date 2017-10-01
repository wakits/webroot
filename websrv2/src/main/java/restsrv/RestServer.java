package restsrv;

import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.cache.DirectBufferCache;
import io.undertow.server.handlers.resource.CachingResourceManager;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.server.handlers.resource.ResourceHandler; 
import io.undertow.server.handlers.resource.ResourceManager;

import java.time.Duration; 

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
    private final Undertow server;
    private final int port;

    RestServer(int port) {
        this.port = port;
        this.server = Undertow
            .builder()
            .addHttpListener(this.port, "localhost")
            .setHandler(createStaticResourceHandler())
            .build();
    }

    HttpHandler createStaticResourceHandler() {
        final ResourceManager staticResources = new ClassPathResourceManager(RestServer.class.getClassLoader(), "static");

        // Cache tuning is copied from Undertow unit tests.
        final ResourceManager cachedResources =
                new CachingResourceManager(100, 65536,
                                           new DirectBufferCache(1024, 10, 10480),
                                           staticResources,
                                           (int)Duration.ofDays(1).getSeconds());
        final ResourceHandler resourceHandler = new ResourceHandler(cachedResources);
        resourceHandler.setWelcomeFiles("index.html");
        resourceHandler.setDirectoryListingEnabled(true);
        return resourceHandler;
    }

    public void run() {
        LOGGER.info("Starting RestServer @ localhost:" + this.port);
        server.start();
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
