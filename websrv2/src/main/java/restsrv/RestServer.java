package restsrv;

import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.cache.DirectBufferCache;
import io.undertow.server.handlers.resource.CachingResourceManager;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.server.handlers.resource.ResourceHandler; 
import io.undertow.server.handlers.resource.ResourceManager;

import java.time.Duration; 

/**
 * RestServer
 */
public class RestServer {

    static Integer intOrNull(String str) {
        if (str == null) return null;
        try {
            return Integer.valueOf(str);
        } catch (NumberFormatException e) {
            // log error
            // TODO
        }
        return null;
    }

    static int serverPort(final String[] args) {
        Integer port;

        // from env
        port = intOrNull(System.getenv("PORT"));
        if (port != null) return port.intValue();

        // from args
        if (args.length > 0) {
            port = intOrNull(args[0]);
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
        server.start();
    }
}
