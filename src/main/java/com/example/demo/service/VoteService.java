package com.example.demo.service;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.math.BigInteger;

@Service@RequiredArgsConstructor
public class VoteService {
    Dotenv env = Dotenv.configure().load();

    String ETH_URL = env.get("ETH_URL_TEST");
    String ADDRESS = env.get("CONTRACT_ADDRESS");
    Web3j web3 = Web3j.build(new HttpService(ETH_URL));

    public Request<?, EthGetBalance> tryEth(){
        try {
            BigInteger blockNumber = null;
            try {
                blockNumber = web3.ethBlockNumber().send().getBlockNumber();
                Request<?, EthGetBalance> balance = web3.ethGetBalance(ADDRESS, DefaultBlockParameter.valueOf(blockNumber));
                return balance;
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
//            System.out.println("Latest Ethereum block number: " + blockNumber);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

}
