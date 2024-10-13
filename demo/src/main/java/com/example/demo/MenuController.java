package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.awt.Desktop;
import java.net.URI;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private DatosService datosService;
    @Autowired
    private ExponencialService exponencialService;

    @PostConstruct
    public void init() {
        openBrowser("http://localhost:8080/MenuPrincipal.html");
    }

    private void openBrowser(String url) {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(new URI(url));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

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