package com.ethereum.manageusers.contract;

import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Int256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EmployeeContract extends Contract {


    private EmployeeContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super("", contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static EmployeeContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new EmployeeContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public RemoteCall<TransactionReceipt> addEmployee(String firstName, String lastName, String position){
        Function function = new Function(
                "addEmployee",
                Arrays.asList(new Utf8String(firstName), new Utf8String(lastName), new Utf8String(position)),
                Collections.emptyList()
        );

        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> getEmployeesCount() {
        Function function = new Function(
                "getEmployeesCount",
                Collections.emptyList(),
                List.of(new TypeReference<Int256>() {
                })
        );
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

//    public RemoteCall<Tuple3<String, String, String>> getEmployee(BigInteger index) {
//        Function function = new Function(
//                "getEmployee",
//                Collections.singletonList(new Uint256(index)),
//                Arrays.asList(
//                        new TypeReference<org.web3j.abi.datatypes.Utf8String>() {},
//                        new TypeReference<org.web3j.abi.datatypes.Utf8String>() {},
//                        new TypeReference<org.web3j.abi.datatypes.Utf8String>() {}
//                )
//        );
//
//        String encodedFunction = FunctionEncoder.encode(function);
//        return new RemoteCall<>(() -> {
//            try {
//                EthCall ethCall = web3j.ethCall(
//                        org.web3j.protocol.core.methods.request.Transaction.createEthCallTransaction(
//                                "<fromAddress>",  // Zastąp wartością odpowiedniego adresu nadawcy
//                                "<contractAddress>",  // Zastąp wartością odpowiedniego adresu kontraktu
//                                encodedFunction
//                        ),
//                        DefaultBlockParameterName.LATEST
//                ).send();
//
//                List<Type> decodedValues = FunctionReturnDecoder.decode(ethCall.getValue(), function.getOutputParameters());
//                Tuple3<String, String, String> result = new Tuple3<>(
//                        (String) decodedValues.get(0).getValue(),
//                        (String) decodedValues.get(1).getValue(),
//                        (String) decodedValues.get(2).getValue()
//                );
//                return result;
//            } catch (Exception e) {
//                throw new RuntimeException("Failed to execute getEmployee function", e);
//            }
//        });

    }