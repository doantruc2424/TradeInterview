package com.example.TradeInterview.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SymbolNotSupportException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public SymbolNotSupportException() {
        super();
    }
    public SymbolNotSupportException(String message, Throwable cause) {
        super(message, cause);
    }
    public SymbolNotSupportException(String message) {
        super(message);
    }
}
