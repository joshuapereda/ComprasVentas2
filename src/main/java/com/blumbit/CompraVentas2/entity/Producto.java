package com.blumbit.CompraVentas2.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column( nullable = false, length = 50)
    private String nombre;
    @Column(length = 200)
    private String descripcion;
    @Column(length = 20)
    private String unidadMedida;
    @Column(length = 100)
    private String marca;
    @Column(precision = 12, scale = 2)
    private BigDecimal precioVentaActual;
    @Column(length = 250)
    private String imagen;
    @Column(nullable = false)
    private Boolean estado;
    @ManyToOne
    private Categoria categoria;

}
