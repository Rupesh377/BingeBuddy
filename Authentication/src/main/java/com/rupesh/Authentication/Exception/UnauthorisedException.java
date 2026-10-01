package com.rupesh.Authentication.Exception;

public class UnauthorisedException extends RuntimeException{

    private UnauthorisedException(String message)
    {
        super(message);
    }
}
