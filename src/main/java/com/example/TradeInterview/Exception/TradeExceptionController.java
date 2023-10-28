package com.example.TradeInterview.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TradeExceptionController {

    @ExceptionHandler(value = WalletNotFoundException.class)
    public ResponseEntity<Object> walletNotFoundException(WalletNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(value = SymbolNotSupportException.class)
    public ResponseEntity<Object> symbolNotSupportException(SymbolNotSupportException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(value = BalanceNotEnoughException.class)
    public ResponseEntity<Object> balanceNotEnoughException(BalanceNotEnoughException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }
}