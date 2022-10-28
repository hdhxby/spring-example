package com.hdhxby.ecommerce.hello.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


public interface HelloResource {

    @GetMapping("/api/hello")
    ResponseEntity<String> hello(@RequestParam(value = "name",defaultValue = "world",required = false) String name, @RequestParam(value = "millis",defaultValue = "0",required = false) Long millis);
}
