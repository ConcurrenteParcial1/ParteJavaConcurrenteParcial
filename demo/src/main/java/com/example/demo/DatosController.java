package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/datos")


public class DatosController {
    @Autowired
    private DatosService datosService;

    @GetMapping
    public List<Datos> getAllValores() {

        return datosService.getAllValores();
    }

    @PostMapping
    public Datos saveValor(@RequestBody Datos datos) {
        return datosService.saveValor(datos);
    }
}