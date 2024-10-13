package com.example.demo;

import jakarta.persistence.*;
import lombok.Data;

// La anotación @Entity indica que esta clase es una entidad JPA.
// Esto significa que esta clase se mapeará a una tabla en la base de datos.
@Entity

// La anotación @Data de Lombok genera automáticamente los métodos getter, setter, equals, hashCode y toString.
@Data

// La anotación @Table se utiliza para especificar detalles de la tabla a la que se mapeará esta entidad.
// En este caso, se mapeará a la tabla "datos".
@Table(name = "datos")
public class Datos {

    // La anotación @Id indica que este campo es la clave primaria de la tabla.
    // La anotación @GeneratedValue con la estrategia IDENTITY indica que el valor de este campo se generará automáticamente.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // La anotación @Column se utiliza para especificar detalles de la columna a la que se mapeará este campo.
    // En este caso, se mapeará a la columna "value".
    @Column(name = "value")
    private String value;

    // Método getter para el campo 'value'.
    public String getValue() {
        return value;
    }

    // Método setter para el campo 'value'.
    public void setValue(String value) {
        this.value = value;
    }

    // Método getter para el campo 'id'.
    public Long getId() {
        return id;
    }

    // Método setter para el campo 'id'.
    public void setId(Long id) {
        this.id = id;
    }
}