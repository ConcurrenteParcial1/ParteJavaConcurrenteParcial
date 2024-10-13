package com.example.demo.Datos;

import com.example.demo.Exponencial.ExponencialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.Future;
import java.util.List;

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
        future.get();
        return "Datos normales impresos";
    }

    @PostMapping("/printExponencial")
    public String printExponencial() throws Exception {
        Future<?> future = exponencialService.printExponencial();
        future.get();
        return "Datos exponenciales impresos";
    }

    @PostMapping("/shutdown")
    public String shutdown() {
        datosService.shutdownExecutor();
        exponencialService.shutdownExecutor();
        return "Servicios apagados";
    }

    @GetMapping
    public List<Datos> getAllValores() {
        return datosService.getAllValores();
    }

    @PostMapping
    public Datos saveValor(@RequestBody Datos datos) {
        return datosService.saveValor(datos);
    }
}