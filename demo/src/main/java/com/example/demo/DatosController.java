package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// Anotación para indicar que esta clase es un controlador REST
@RestController
// Anotación para mapear las solicitudes HTTP a /datos a este controlador
@RequestMapping("/datos")
public class DatosController {

    // Inyección de dependencia de la clase DatosService
    @Autowired
    private DatosService datosService;

    // Método para manejar las solicitudes GET a /datos
    // Devuelve una lista de todos los valores de Datos
    @GetMapping
    public List<Datos> getAllValores() {
        return datosService.getAllValores();
    }

    // Método para manejar las solicitudes POST a /datos
    // Acepta un objeto Datos en el cuerpo de la solicitud y lo guarda
    // Devuelve el objeto Datos guardado
    @PostMapping
    public Datos saveValor(@RequestBody Datos datos) {
        return datosService.saveValor(datos);
    }
}