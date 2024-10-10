package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Service
public class DatosService {

    @Autowired
    private DatosRepository datosRepository;

    public List<Datos> getAllValores() {

        return datosRepository.findAll();
    }

    public Datos saveValor(Datos datos) {
        return datosRepository.save(datos);
    }

    public void loadCSVToDatabase(String csvFile) {
        String line;
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {

                String[] conjunto = line.split(cvsSplitBy);
                Datos datos = new Datos();

                datos.setValue(String.valueOf(conjunto[0]));
                datosRepository.save(datos);
            }
            System.out.println("finalizacion del llenado");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}