package com.example.demo.Exponencial;

import jakarta.persistence.*;
import lombok.Data;

// La anotación @Entity indica que esta clase es una entidad JPA.
@Entity
// La anotación @Data de Lombok genera automáticamente los métodos getter, setter, equals, hashCode y toString.
@Data
// La anotación @Table se utiliza para especificar la tabla asociada con la entidad.
@Table(name = "exponencial")
public class Exponencial {
    // La anotación @Id indica que este campo es la clave primaria de la entidad.
    @Id
    // La anotación @GeneratedValue se utiliza para especificar cómo se genera el valor para la clave primaria.
    // En este caso, se utiliza la estrategia IDENTITY, que significa que el valor se genera automáticamente por la base de datos.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // La anotación @Column se utiliza para especificar la columna asociada con el campo.
    @Column(name = "valor")
    private Double valor;
}