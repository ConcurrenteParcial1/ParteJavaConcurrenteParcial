package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Future;

@RestController
public class FrontendController {

    @Autowired
    private DatosService datosService;
    @Autowired
    private ExponencialService exponencialService;

    @GetMapping("/print-datos")
    public String printDatos() {
        try {
            Future<?> future = datosService.printDatos();
            future.get(); // Espera a que termine la ejecución.
            return "Datos impresos en consola";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error imprimiendo los datos";
        }
    }

    @GetMapping("/print-exponenciales")
    public String printExponenciales() {
        try {
            Future<?> future = exponencialService.printExponencial();
            future.get(); // Espera a que termine la ejecución.
            return "Datos exponenciales impresos en consola";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error imprimiendo los datos exponenciales";
        }
    }
}
