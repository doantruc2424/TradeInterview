package com.example.TradeInterview.service;

import com.example.TradeInterview.payload.WalletRepDto;
import com.example.TradeInterview.entity.Wallet;
import com.example.TradeInterview.repository.WalletRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalletService {
    private static final Logger logger = LoggerFactory.getLogger(TradeService.class);
    @Autowired
    WalletRepository walletRepository;
    @Autowired
    ModelMapper modelMapper;


    /**
     *3. User able to see the crypto currencies wallet balance
     * @return
     */
    public List<WalletRepDto> getWallets(String currency, Long userId) {
        List<Wallet> entities = walletRepository.findByUserId(userId);
        return parseModel(entities);
    }
    private List<WalletRepDto> parseModel(List<Wallet> entities) {
        return entities.stream().map(entity -> modelMapper.map(entity, WalletRepDto.class)).toList();
    }
}
