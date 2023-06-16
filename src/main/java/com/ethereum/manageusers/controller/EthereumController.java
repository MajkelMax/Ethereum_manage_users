package com.ethereum.manageusers.controller;

import com.ethereum.manageusers.service.EthereumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class EthereumController {

    private final EthereumService ethereumService;

    @Autowired
    EthereumController(EthereumService ethereumService) {
        this.ethereumService = ethereumService;
    }

    @GetMapping("/")
    public String getBalance() {
        String address = ethereumService.getCredentials().getAddress();
        return address;
    }
    @GetMapping("/block")
    public String getLastBlock() throws IOException {
        return ethereumService.getLatestBlockNumber();
    }
}
