package x.y.z.webmvc.endpoint;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import java.util.Map;

@Endpoint(id="foo")
@Component
public class FooEndpoint {
    @ReadOperation
    public Map map(){
        return Map.of("key","value");
    }
}
