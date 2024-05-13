package org.leo.moonpool.handler;

public class JwtExceptionHandler extends RuntimeException{
    public JwtExceptionHandler(String message){
        super(message);
    }
}