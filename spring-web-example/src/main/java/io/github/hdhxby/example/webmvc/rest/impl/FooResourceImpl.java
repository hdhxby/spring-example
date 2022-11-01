package io.github.hdhxby.example.webmvc.rest.impl;

import io.github.hdhxby.example.webmvc.rest.FooResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import x.y.z.service.FooService;


@RestController
public class FooResourceImpl implements FooResource {

    @Autowired
    private FooService fooService;

    @GetMapping("/api/foo")
    @Override
    public ResponseEntity<String> hello(@RequestParam(value = "name",defaultValue = "world",required = false) String name, @RequestParam(value = "millis",defaultValue = "0",required = false) Long millis){
        if(millis !=0){
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                // nothing.
            }
        }
        return ResponseEntity.ok(String.format("hello %s,sleep %d millis.", name, millis));
    }
}
