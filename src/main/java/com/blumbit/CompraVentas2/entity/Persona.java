package com.blumbit.CompraVentas2.entity;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="personas")
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String apellido;

    private LocalDate fechaNacimiento;
    
    @Column(length = 20)
    private String genero;

    @Column(length = 20)
    private String telefono;

    @Column(length = 255)
    private String direccion;

    @Column(nullable = false, length = 50)
    private String nacionalidad;
    
    //RELACION CON USUARIO
    //para que me pirmita borrar un usuario y que se borre
    //  la persona relacionada con el usuario
    @OneToOne(cascade = CascadeType.REMOVE)
    //definir la column a con la que esta relacionada en este caso el 
    // indentificador que es el ID de la tabla usuario
    @JoinColumn(name="usuario_id", referencedColumnName = "id")
    private Usuario usuario;
}
