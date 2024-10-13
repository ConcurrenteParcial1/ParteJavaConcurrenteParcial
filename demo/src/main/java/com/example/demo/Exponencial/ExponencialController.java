package com.example.demo.Exponencial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

// La anotación @RestController indica que esta clase es un controlador REST.
@RestController
// La anotación @RequestMapping se utiliza para mapear las solicitudes web en métodos de controlador de manejo de solicitudes.
// En este caso, todas las solicitudes que comienzan con "/exponencial" serán manejadas por este controlador.
@RequestMapping("/exponencial")
public class ExponencialController {
    // Referencia a una instancia de ExponencialService
    private final ExponencialService exponencialService;

    // La anotación @Autowired se utiliza para la inyección automática de dependencias en Spring.
    // En este caso, se inyecta una instancia de ExponencialService.
    @Autowired
    public ExponencialController(ExponencialService exponencialService) {
        this.exponencialService = exponencialService;
    }

    // La anotación @PostMapping se utiliza para mapear las solicitudes POST HTTP a métodos de controlador de manejo de solicitudes.
    // En este caso, las solicitudes POST a "/exponencial" serán manejadas por este método.
    @PostMapping
    // La anotación @RequestBody indica que el parámetro del método debe estar vinculado al cuerpo de la solicitud web.
    public Exponencial saveValor(@RequestBody Exponencial exponencial) {
        // Llama al método saveValor() de ExponencialService para guardar el objeto Exponencial y luego devuelve el objeto Exponencial guardado.
        return exponencialService.saveValor(exponencial);
    }
}