package com.example.apiDisney.service.exception;

import org.springframework.dao.EmptyResultDataAccessException;

public class IdDoesNotExistException extends EmptyResultDataAccessException {

    public IdDoesNotExistException(int expectedSize) {
        super(expectedSize);
    }
}
