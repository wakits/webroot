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

    static int serverPort(final String[] args) {
        // TODO: read PORT from environment or as argument
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
