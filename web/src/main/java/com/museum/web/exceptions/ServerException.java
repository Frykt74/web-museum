package com.museum.web.exceptions;

public class ServerException extends RuntimeException {
    public ServerException(String message) {
        super(message);
    }

    public ServerException() {
        super("Internal server error");
    }
}
