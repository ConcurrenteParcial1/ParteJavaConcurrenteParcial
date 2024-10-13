package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.concurrent.Future;

import java.util.List;


// Anotación para indicar que esta clase es un controlador REST
@RestController
// Anotación para mapear las solicitudes HTTP a /datos a este controlador
@RequestMapping("/datos")
public class DatosController {

    // Inyección de dependencia de la clase DatosService

    @Autowired
    private DatosService datosService;
    @Autowired
    private ExponencialService exponencialService;


    @PostMapping("/printDatos")
    public String printDatos() throws Exception {
        Future<?> future = datosService.printDatos();
        future.get(); // Esperamos a que el hilo termine antes de seguir.
        return "Datos normales impresos";
    }

    @PostMapping("/printExponencial")
    public String printExponencial() throws Exception {
        Future<?> future = exponencialService.printExponencial();
        future.get(); // Esperamos a que el hilo termine antes de seguir.
        return "Datos exponenciales impresos";
    }

    @PostMapping("/shutdown")
    public String shutdown() {
        datosService.shutdownExecutor();
        exponencialService.shutdownExecutor();
        return "Servicios apagados";

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