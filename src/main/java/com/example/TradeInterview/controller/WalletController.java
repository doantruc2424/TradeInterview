package com.example.TradeInterview.controller;

import com.example.TradeInterview.dto.TradeRepDto;
import com.example.TradeInterview.dto.WalletRepDto;
import com.example.TradeInterview.response.ApiResponse;
import com.example.TradeInterview.service.TradeService;
import com.example.TradeInterview.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/wallet")
public class WalletController {

    @Autowired
    WalletService walletService;

    @GetMapping("/get_wallets")
    public ResponseEntity<ApiResponse> getWallets(@RequestParam(required = false) String currency) {
        List<WalletRepDto> dto = walletService.getWallets(currency);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Ok", "success", dto));
    }


}
