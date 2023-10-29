package com.example.TradeInterview.controller;

import com.example.TradeInterview.payload.WalletRepDto;
import com.example.TradeInterview.payload.ApiResponse;
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

    /**
     * 3. User able to see the crypto currencies wallet balance
     * @param currency
     * @param userId
     * @return
     */
    @GetMapping("/wallets")
    public ResponseEntity<ApiResponse> getWallets(@RequestParam(required = false) String currency, @RequestParam(required = true) Long userId) {
        List<WalletRepDto> dto = walletService.getWallets(currency, userId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Ok", "success", dto));
    }
}
