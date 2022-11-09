package x.y.z.webmvc.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


public interface FooResource {

    @GetMapping("/api/string")
    ResponseEntity<String> string(@RequestParam(value = "name",defaultValue = "world",required = false) String name, @RequestParam(value = "millis",defaultValue = "0",required = false) Long millis);
}
