package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exponencial")
public class ExponencialController {
    private final ExponencialService exponencialService;

    @Autowired
    public ExponencialController(ExponencialService exponencialService) {
        this.exponencialService = exponencialService;
    }

    @PostMapping
    public Exponencial saveValor(@RequestBody Exponencial exponencial) {
        return exponencialService.saveValor(exponencial);
    }
}