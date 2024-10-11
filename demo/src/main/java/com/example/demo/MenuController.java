package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// La anotación @RestController indica que esta clase es un controlador REST.
@RestController
// La anotación @RequestMapping se utiliza para mapear las solicitudes web en métodos de controlador de manejo de solicitudes.
// En este caso, todas las solicitudes que comienzan con "/menu" serán manejadas por este controlador.
@RequestMapping("/menu")
public class MenuController {

    // La anotación @Autowired se utiliza para la inyección automática de dependencias en Spring.
    // En este caso, se inyectan instancias de DatosService y ExponencialService.
    @Autowired
    private DatosService datosService;
    @Autowired
    private ExponencialService exponencialService;

    // La anotación @GetMapping se utiliza para mapear las solicitudes GET HTTP a métodos de controlador de manejo de solicitudes.
    // En este caso, las solicitudes GET a "/menu/printDatos" serán manejadas por este método.
    @GetMapping("/printDatos")
    public String printDatos() {
        // Llama al método printDatos() de DatosService para imprimir los datos.
        datosService.printDatos();
        // Devuelve un mensaje indicando que los datos normales han sido impresos.
        return "Datos normales impresos";
    }

    // La anotación @GetMapping se utiliza para mapear las solicitudes GET HTTP a métodos de controlador de manejo de solicitudes.
    // En este caso, las solicitudes GET a "/menu/printExponencial" serán manejadas por este método.
    @GetMapping("/printExponencial")
    public String printExponencial() {
        // Llama al método printExponencial() de ExponencialService para imprimir los datos.
        exponencialService.printExponencial();
        // Devuelve un mensaje indicando que los datos exponenciales han sido impresos.
        return "Datos exponenciales impresos";
    }
}