package com.example.TradeInterview.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BalanceNotEnoughException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BalanceNotEnoughException() {
        super();
    }
    public BalanceNotEnoughException(String message, Throwable cause) {
        super(message, cause);
    }
    public BalanceNotEnoughException(String message) {
        super(message);
    }
}
