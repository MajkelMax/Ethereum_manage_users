package com.ethereum.manageusers;

import com.ethereum.manageusers.service.EthereumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ManageusersApplication implements CommandLineRunner{

	@Autowired
	EthereumService ethereumService;

	public static void main(String[] args) {
		SpringApplication.run(ManageusersApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {

	}
}
