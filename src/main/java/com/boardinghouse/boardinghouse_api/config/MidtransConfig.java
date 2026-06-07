package com.boardinghouse.boardinghouse_api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.midtrans.Config;
import com.midtrans.ConfigFactory;
import com.midtrans.service.MidtransCoreApi;

@Configuration
public class MidtransConfig {
    @Value("${midtrans.server-key}")
    private String serverKey;
    @Value("${midtrans.client-key}")
    private String clientKey;
    //@Value("${midtrans.is-production:false}")
    private boolean isProduction = false;

    @Bean
    public MidtransCoreApi midtransCoreApi(){
        Config config = Config.builder()
            .setServerKey(serverKey)
            .setClientKey(clientKey)
            .setIsProduction(isProduction)
            .build();

        return new ConfigFactory(config).getCoreApi();
    }
}
