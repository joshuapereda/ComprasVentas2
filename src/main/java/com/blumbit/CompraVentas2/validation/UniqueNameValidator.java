package com.blumbit.CompraVentas2.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class UniqueNameValidator  implements ConstraintValidator<UniqueName,String>{
    //2
     @Autowired
     private IUniqueNameChecker uniqueNameChecker;
     private String fieldName;
    
     @Override
    public void initialize(UniqueName constraintAnnotation){
        this.fieldName=constraintAnnotation.fieldName();
    }
    //1
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        //definir logica
        if(value == null || value.isBlank()){
            return true;
        }
        boolean isUnique = uniqueNameChecker.isUniqueName(value);
        //logica 
        if (!isUnique) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("El valor del  " + fieldName + "  ya existe")
                .addConstraintViolation();
            return false;
        }
        return true;
    }

}
