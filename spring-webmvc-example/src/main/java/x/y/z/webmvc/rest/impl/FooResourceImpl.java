package x.y.z.webmvc.rest.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import x.y.z.bean.Foo;
import x.y.z.webmvc.rest.FooResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import x.y.z.service.FooService;

import java.math.BigDecimal;

@RequestMapping("/api")
@RestController
public class FooResourceImpl implements FooResource {

    private static final Logger logger = LoggerFactory.getLogger(FooResource.class);

    @Autowired
    private FooService fooService;

    @GetMapping("/string")
    @Override
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

    @PostMapping("/foo")
    public Foo hello(@RequestBody Foo foo){
        return foo;
    }

    @GetMapping("/divide")
    public Integer divide(){
        int i=0/1;
        return i;
    }
}
