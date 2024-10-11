package com.example.demo;

public class DatosServiceFactory implements ServiceFactory {
    private final DatosService datosService;

    public DatosServiceFactory(DatosService datosService) {
        this.datosService = datosService;
    }

    @Override
    public Runnable getService() {
        return datosService::printDatos;
    }
}