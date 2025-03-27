package springbootApplication.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@CrossOrigin(origins = {"http://dalgrak.com", "http://example.com"}) 
public class SomeController {

    @GetMapping("/some-endpoint")
    public String someMethod() {
        return "Hello, World!";
    }
}
