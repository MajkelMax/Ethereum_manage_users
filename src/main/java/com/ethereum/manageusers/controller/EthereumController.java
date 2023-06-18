package com.ethereum.manageusers.controller;

import com.ethereum.manageusers.contract.EmployeeContract;
import com.ethereum.manageusers.dto.EmployeeDTO;
import com.ethereum.manageusers.service.EthereumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;
import java.util.List;

@RestController
public class EthereumController {

    private final EthereumService ethereumService;

    private final EmployeeContract employeeContract;

    @Autowired
    EthereumController(EthereumService ethereumService, EmployeeContract employeeContract) {
        this.ethereumService = ethereumService;
        this.employeeContract = employeeContract;
    }

    @GetMapping("employee/count")
    public BigInteger getEmployeesCount() throws Exception {

        return employeeContract.getEmployeesCount().send();
    }

    @PostMapping("/employee")
    public ResponseEntity<String> addEmployee(@RequestBody EmployeeDTO employeeDTO) {
        try {
            RemoteCall<TransactionReceipt> remoteCall = employeeContract.addEmployee(
                    employeeDTO.getFirstName(),
                    employeeDTO.getLastName(),
                    employeeDTO.getPosition()
            );
            TransactionReceipt transactionReceipt = remoteCall.send();

            if (transactionReceipt.isStatusOK()) {
                return ResponseEntity.ok("Employee added successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to add employeeasd");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to add employee");
        }
    }



    @GetMapping("employee/{index}")
    public EmployeeDTO getEmployee(@PathVariable BigInteger index) throws Exception {
        RemoteCall<EmployeeDTO> remoteCall = employeeContract.getEmployee(index);
        return remoteCall.send();
    }

    @GetMapping("/employee") // Time complexity is O = n, I do not recommend if for some big data
    public List<EmployeeDTO> getEmployeeList() throws Exception {
        List<EmployeeDTO> remoteCall = employeeContract.getEmployeeList();
        return remoteCall;
    }


}
