package com.ethereum.manageusers.contract;

import com.ethereum.manageusers.configuration.Web3Config;
import com.ethereum.manageusers.dto.EmployeeDTO;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Int256;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;

import java.math.BigInteger;
import java.util.ArrayList;
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




    public RemoteCall<EmployeeDTO> getEmployee(BigInteger index) {
        Function function = new Function(
                "getEmployee",
                Arrays.asList(new Uint256(index)),
                Arrays.asList(new TypeReference<Utf8String>() {},
                        new TypeReference<Utf8String>() {},
                        new TypeReference<Utf8String>() {})
        );

        String encodedFunction = FunctionEncoder.encode(function);

        Transaction ethCallTransaction = Transaction.createEthCallTransaction( null,
                Web3Config.CONTRACT_ADDRESS, encodedFunction);

        org.web3j.protocol.core.methods.response.EthCall ethCall;
        try {
            ethCall = web3j.ethCall(ethCallTransaction, DefaultBlockParameterName.LATEST).send();
        } catch (Exception e) {
            return null;
        }

        List<Type> results = FunctionReturnDecoder.decode(
                ethCall.getValue(), function.getOutputParameters());

        Utf8String firstName = (Utf8String) results.get(0);
        Utf8String lastName = (Utf8String) results.get(1);
        Utf8String position = (Utf8String) results.get(2);

        return new RemoteCall<>(() -> new EmployeeDTO(
                firstName.getValue(),
                lastName.getValue(),
                position.getValue()));
    }

    public List<EmployeeDTO> getEmployeeList() throws Exception {
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        for (int i = 0; i < getEmployeesCount().send().intValue(); i++){
            employeeDTOList.add(getEmployee(BigInteger.valueOf(i)).send());
        }
        return employeeDTOList;
    }
}