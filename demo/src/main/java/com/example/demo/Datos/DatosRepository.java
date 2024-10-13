package com.example.demo.Datos;


import com.example.demo.Datos.Datos;
import org.springframework.data.jpa.repository.JpaRepository;



public interface DatosRepository extends JpaRepository<Datos, Long> {

}