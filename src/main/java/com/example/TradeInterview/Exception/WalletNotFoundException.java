package com.example.TradeInterview.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class WalletNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public WalletNotFoundException() {
        super();
    }
    public WalletNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public WalletNotFoundException(String message) {
        super(message);
    }
}
