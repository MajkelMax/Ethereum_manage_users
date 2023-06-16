package com.ethereum.manageusers.configuration;

import com.ethereum.manageusers.PRIVATE_DATA;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

@Configuration
public class Web3Config {

    @Bean
    public Web3j web3j() {
        String goerliUrl = PRIVATE_DATA.GOERLI_API_URL_KEY;
        return Web3j.build(new HttpService(goerliUrl));
    }

    @Bean
    public Credentials credentials() {
        String privateKey = PRIVATE_DATA.PRIVATE_WALLET_KEY;
        return Credentials.create(privateKey);
    }

}
