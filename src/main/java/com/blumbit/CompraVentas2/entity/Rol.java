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
import jakarta.persistence.TableGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@TableGenerator(name = "roles")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column( nullable = false, length = 100)
    private String nombre;
    
    @Column(length = 200)
    private String descripcion;


    //RELACIONES DE N a N
    @ManyToMany
    //GENERRA LA TABLA DE RELACION AUTOMATICAMENTE
    @JoinTable(
        //NOMBRE DE LA TABLA 
        name="roles_permisos",
        joinColumns = @JoinColumn(name="rol_id"),
        inverseJoinColumns = @JoinColumn(name="permiso_id")
    )
    private List<Permiso>permisos;

}
