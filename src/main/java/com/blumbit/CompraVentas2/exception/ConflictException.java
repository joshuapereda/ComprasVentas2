package com.blumbit.CompraVentas2.exception;

public class ConflictException extends DomainExeption  {

    public ConflictException(String message) {
        super(message, 409,"CONFLICT");
      
    }

}
