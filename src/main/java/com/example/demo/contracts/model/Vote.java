package com.example.demo.contracts.model;

import io.github.cdimascio.dotenv.Dotenv;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.math.BigInteger;

public class Vote {
    Dotenv env = Dotenv.configure().load();

    String ETH_URL = env.get("ETH_URL");

    Web3j web3 = Web3j.build(new HttpService(ETH_URL));

    public String tryEth(){
        try {
            BigInteger blockNumber = null;
            try {
                blockNumber = web3.ethBlockNumber().send().getBlockNumber();
                return blockNumber.toString();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
//            System.out.println("Latest Ethereum block number: " + blockNumber);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

}
