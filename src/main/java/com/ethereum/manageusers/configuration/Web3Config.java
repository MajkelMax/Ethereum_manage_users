package com.ethereum.manageusers.configuration;

import com.ethereum.manageusers.PRIVATE_DATA;
import com.ethereum.manageusers.contract.EmployeeContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;

import java.math.BigInteger;

@Configuration
public class Web3Config {

    private static final String CONTRACT_ADDRESS = "0x17154b7D44f77e817EAEF2998C1a910d2ca3b87F";

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
    @Bean
    public EmployeeContract contract(Web3j web3j, Credentials credentials) {

        return EmployeeContract.load(CONTRACT_ADDRESS, web3j, credentials, getCurrentGasPrice(), Contract.GAS_LIMIT);
    }

    private static BigInteger getCurrentGasPrice() {
        Web3j web3j = Web3j.build(new HttpService(PRIVATE_DATA.GOERLI_API_URL_KEY));
        try {
            BigInteger gasPrice = web3j.ethGasPrice().send().getGasPrice();
            return gasPrice;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



}
