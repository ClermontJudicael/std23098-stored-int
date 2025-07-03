package com.example.demo.endpoint;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StoredInt {
    @GetMapping("/stored-int")
    public String storedInt() {
        return "Hello world";
    }
}
