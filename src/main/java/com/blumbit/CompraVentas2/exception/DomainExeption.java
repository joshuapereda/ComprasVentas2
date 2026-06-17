package com.blumbit.CompraVentas2.exception;

import lombok.Getter;

//definir como clase abstract, para utilizarlo como molde
@Getter
public abstract class DomainExeption  extends RuntimeException{
    private int statusCode;
    private String errorCode;
    public DomainExeption(String message,int statusCode,String errorCode ){
        super(message);
        this.statusCode=statusCode;
        this.errorCode=errorCode;
    }


}
