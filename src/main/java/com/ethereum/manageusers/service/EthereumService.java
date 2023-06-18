package com.ethereum.manageusers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;

import java.io.IOException;
import java.math.BigInteger;

@Component
public class EthereumService {
    private final Web3j web3j;
    private final Credentials credentials;

    @Autowired
    public EthereumService(Web3j web3j, Credentials credentials) {
        this.web3j = web3j;
        this.credentials = credentials;
    }

    public BigInteger getLatestBlockNumber() throws IOException {
        BigInteger blockNumber = web3j.ethGasPrice().send().getGasPrice();
        return blockNumber;
    }


    public Credentials getCredentials() {
        return credentials;
    }


}
