package io.github.hdhxby.example.webflux.web.rest.impl;

import io.github.hdhxby.example.webflux.web.rest.HelloResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RestController
public class HelloResourceImpl implements HelloResource {

    @GetMapping("/api/hello")
    @Override
    public Mono<String> hello(@RequestParam(value = "name",defaultValue = "world",required = false) String name, @RequestParam(value = "millis",defaultValue = "0",required = false) Long millis){
        if(millis !=0){
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                // nothing.
            }
        }
        return Mono.just(String.format("hello %s,sleep %d millis.", name, millis));
    }
}
