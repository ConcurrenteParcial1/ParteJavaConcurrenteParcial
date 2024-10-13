package com.example.demo.Exponencial;

import com.example.demo.Fabrica.ServiceFactory;

public class ExponencialServiceFactory implements ServiceFactory {
    // Referencia a una instancia de ExponencialService
    private final ExponencialService exponencialService;

    // Constructor que acepta una instancia de ExponencialService
    public ExponencialServiceFactory(ExponencialService exponencialService) {
        this.exponencialService = exponencialService;
    }

    // Implementación del método getService() de la interfaz ServiceFactory
    // Devuelve una instancia de Runnable que, cuando se ejecuta, llama al método printExponencial() de ExponencialService
    @Override
    public Runnable getService() {
        return exponencialService::printExponencial;
    }
}