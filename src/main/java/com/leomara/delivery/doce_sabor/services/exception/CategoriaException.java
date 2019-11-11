package com.leomara.delivery.doce_sabor.services.exception;

public class CategoriaException extends RuntimeException {

    public CategoriaException(String msg) {
        super(msg);
    }

    public CategoriaException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
