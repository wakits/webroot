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
public class RestServer {

    static { //runs when the main class is loaded.
        System.setProperty("org.jboss.logging.provider", "log4j2");
    }

    private static final Logger LOGGER = Logger.getLogger(RestServer.class);

    static Integer intOrNull(String str, String errMsg) {
        if (str == null) return null;
        try {
            return Integer.valueOf(str);
        } catch (NumberFormatException e) {
            // log error
            LOGGER.error(errMsg + str);
        }
        return null;
    }

    static int serverPort(final String[] args) {
        Integer port;
        String msg = "Invalid port number: ";

        // from env
        port = intOrNull(System.getenv("PORT"), msg);
        if (port != null) return port.intValue();

        // from args
        if (args.length > 0) {
            port = intOrNull(args[0], msg);
            if (port != null) return port.intValue();
        }

        // default to 8080
        return 8080;
    }

    static HttpHandler createStaticResourceHandler() {
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

    public static void main(final String[] args) {
        int port = serverPort(args);

        Undertow server = Undertow
            .builder()
            .addHttpListener(port, "localhost")
            .setHandler(createStaticResourceHandler())
            .build();
        LOGGER.info("Starting RestServer @ localhost:" + port);
        server.start();
    }
}
