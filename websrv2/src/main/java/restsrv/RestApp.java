package restsrv;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

public class RestApp extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new LinkedHashSet<Class<?>>();
        resources.add(restsrv.api.HelloResource.class);
        return resources;
    }
}
