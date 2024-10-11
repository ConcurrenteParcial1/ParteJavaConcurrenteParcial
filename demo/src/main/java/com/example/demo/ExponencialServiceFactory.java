package com.example.demo;

public class ExponencialServiceFactory implements ServiceFactory {
    private final ExponencialService exponencialService;

    public ExponencialServiceFactory(ExponencialService exponencialService) {
        this.exponencialService = exponencialService;
    }

    @Override
    public Runnable getService() {
        return exponencialService::printExponencial;
    }
}
