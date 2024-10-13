package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.Future;

@RestController
@RequestMapping("/datos")
public class DatosController {

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
    }
}