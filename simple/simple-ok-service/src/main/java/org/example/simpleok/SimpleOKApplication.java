package org.example.simpleok;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class SimpleOKApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleOKApplication.class, args);
    }


    @GetMapping(path = "/echoOK")
    public ResponseEntity<String> echo() {
        return ResponseEntity.ok("ok");
    }
}
