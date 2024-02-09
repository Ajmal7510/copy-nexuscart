package com.ecommerceproject1.ecommerce.Exeption;

public class UserAlredyExistException extends RuntimeException{
    public UserAlredyExistException(String message){

        super(message);
    }

}
