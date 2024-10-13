package com.example.demo.Datos;

import com.example.demo.Fabrica.ServiceFactory;

public class DatosServiceFactory implements ServiceFactory {
    // Referencia a una instancia de DatosService
    private final DatosService datosService;

    // Constructor que acepta una instancia de DatosService
    public DatosServiceFactory(DatosService datosService) {
        this.datosService = datosService;
    }

    // Implementación del método getService() de la interfaz ServiceFactory
    // Devuelve una instancia de Runnable que, cuando se ejecuta, llama al método printDatos() de DatosService
    @Override
    public Runnable getService() {
        return datosService::printDatos;
    }
}