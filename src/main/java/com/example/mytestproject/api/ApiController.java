package com.example.mytestproject.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
    @GetMapping("/api/hi")
    public String Hi(){
        return "Hellow API World~!";
    }




}
