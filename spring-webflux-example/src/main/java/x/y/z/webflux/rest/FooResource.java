package x.y.z.webflux.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.format.WebConversionService;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import x.y.z.bean.Foo;
import x.y.z.service.FooService;

import java.util.Map;

@RequestMapping("/api")
@RestController
public class FooResource {

    private static final Logger logger = LoggerFactory.getLogger(FooResource.class);


    @Autowired
    private FooService fooService;

    @Autowired
    private StandardEnvironment environment;

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private WebConversionService webConversionService;

    @GetMapping("/string")
    public ResponseEntity<String> string(@RequestParam(value = "name",defaultValue = "world",required = false) String name, @RequestParam(value = "millis",defaultValue = "0",required = false) Long millis){
        if(millis !=0){
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                // nothing.
            }
        }
        return ResponseEntity.ok(String.format("hello %s,sleep %d millis.", name, millis));
    }

    @GetMapping("/foo")
    public ResponseEntity<Foo> get(){
        return ResponseEntity.ok(new Foo());
    }

    @PostMapping("/foo")
    public Foo post(@RequestBody Foo foo){
        return foo;
    }

    @PutMapping("/foo")
    public Foo put(@RequestBody Foo foo){
        environment.getPropertySources().addFirst(new MapPropertySource("foo", Map.of("foo.name",foo.getName())));
        return foo;
    }

    @GetMapping("/divide")
    public Integer divide(){
        int i=0/1;
        return i;
    }

//    @Lookup
//    public abstract FooComponent fooComponent();
}
