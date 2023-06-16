package com.ethereum.manageusers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.EthGasPrice;

import java.io.IOException;

@Component
public class EthereumService {

    private final Web3j web3j;
    private final Credentials credentials;

    @Autowired
    public EthereumService(Web3j web3j, Credentials credentials) {
        this.web3j = web3j;
        this.credentials = credentials;
    }

    public String getLatestBlockNumber() throws IOException {
        EthBlockNumber blockNumber = web3j.ethBlockNumber().send();
        return blockNumber.getBlockNumber().toString();
    }


    public Credentials getCredentials() {
        return credentials;
    }
}
