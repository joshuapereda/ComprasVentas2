package com.blumbit.CompraVentas2.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "usuarios")
public class Usuario {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column( unique = true ,nullable = false, length = 50)
    private String nombre;

    @Column(unique = true ,nullable = false, length = 255)
    private String email;
    
    @Column(nullable = false, length = 255)
    private String password;

     @ManyToMany
    //GENERRA LA TABLA DE RELACION AUTOMATICAMENTE
    @JoinTable(
        //NOMBRE DE LA TABLA 
        name="usuario_roles",
        joinColumns = @JoinColumn(name="usuario_id"),
        inverseJoinColumns = @JoinColumn(name="rol_id")
    )
    private List<Rol>roles;

    @OneToOne(mappedBy ="usuario")
    private Persona persona;
    

}
