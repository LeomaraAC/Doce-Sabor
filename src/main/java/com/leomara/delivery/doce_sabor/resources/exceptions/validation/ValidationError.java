package com.leomara.delivery.doce_sabor.resources.exceptions.validation;

import com.leomara.delivery.doce_sabor.resources.exceptions.StandardError;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {

    private List<FieldMessage> errors = new ArrayList<>();
    public ValidationError(Integer status, String message) {
        super(status, message);
    }

    public List<FieldMessage> getErrors() {
        return errors;
    }

    public void addError(String fieldName, String message) {
        errors.add(new FieldMessage(fieldName, message));
    }
}
