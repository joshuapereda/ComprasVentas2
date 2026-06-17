package com.blumbit.CompraVentas2.exception;

public class ResourceNotFoundException extends DomainExeption {

    // El object id indica que es de un tipo genereico, puede ser
    //cadena, lista, numero (int, string etc)
    public ResourceNotFoundException(String resource, Object id) {
        super(resource+"con id "+id+"no encontrado", 404, "RESOURCE_NOT_FOUND");
    }

}
