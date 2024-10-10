package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private DatosService datosService;
    @Autowired
    private ExponencialService exponencialService;

    @GetMapping("/printDatos")
    public String printDatos() {
        datosService.printDatos();
        return "Datos normales impresos";
    }

    @GetMapping("/printExponencial")
    public String printExponencial() {
        exponencialService.printExponencial();
        return "Datos exponenciales impresos";
    }
}