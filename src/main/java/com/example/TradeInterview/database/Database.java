package com.example.TradeInterview.database;

import com.example.TradeInterview.entity.Wallet;
import com.example.TradeInterview.repository.WalletRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;


@Configuration
public class Database {
    //logger
    private static final Logger logger = LoggerFactory.getLogger(Database.class);
    @Bean
    CommandLineRunner initDatabase(WalletRepository walletRepository) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                String[] currencies = {"BTC", "ETH", "USDT"};
                Long[] userIds ={1L,2L,3L,4L,5L};
                for(Long user : userIds) {
                    logger.info("START=================================== ");
                    logger.info("CREATE USER ID = " + user);
                    for(String currency : currencies) {
                        logger.info("   Create wallet user = " + user + ", currency = " + currency + ", balance = " + 50000);
                        walletRepository.save(new Wallet(user, currency, BigDecimal.valueOf(50000)));
                    }
                    logger.info("END  =================================== ");

                }
            }
        };
    }
}
