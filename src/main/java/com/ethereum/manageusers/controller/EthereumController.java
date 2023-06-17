package com.ethereum.manageusers.controller;

import com.ethereum.manageusers.contract.EmployeeContract;
import com.ethereum.manageusers.dto.EmployeeDTO;
import com.ethereum.manageusers.service.EthereumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple3;

import java.math.BigInteger;

@RestController
public class EthereumController {

    private final EthereumService ethereumService;

    private final EmployeeContract employeeContract;

    @Autowired
    EthereumController(EthereumService ethereumService, EmployeeContract employeeContract) {
        this.ethereumService = ethereumService;
        this.employeeContract = employeeContract;
    }

    @GetMapping("/")
    public String getBalance() {
        String address = ethereumService.getCredentials().getAddress();
        return address;
    }
    @GetMapping("/count")
    public BigInteger getEmployeesCount() throws Exception {

        return employeeContract.getEmployeesCount().send();
    }

    @PostMapping("/add")
    public ResponseEntity<String> addEmployee(@RequestBody EmployeeDTO employeeDTO) {
        try {
            RemoteCall<TransactionReceipt> remoteCall = employeeContract.addEmployee(
                    employeeDTO.getFirstName(),
                    employeeDTO.getLastName(),
                    employeeDTO.getPosition()
            );
            TransactionReceipt transactionReceipt = remoteCall.send();

            // Sprawdź status transakcji i zwróć odpowiednią odpowiedź HTTP
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

    @GetMapping("/employee/{index}")
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable("index") int index) {
        try {
            RemoteCall<Tuple3<String, String, String>> remoteCall = employeeContract.getEmployee(BigInteger.valueOf(index));
            Tuple3<String, String, String> result = remoteCall.send();

            EmployeeDTO employeeDTO = new EmployeeDTO(result.component1(), result.component2(), result.component3());
            return ResponseEntity.ok(employeeDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

//    @GetMapping("/get")
//    public BigInteger getContractBalance() throws Exception {
//        return counter.getValue().send();
//    }
//
//    @GetMapping("/increase/{value}")
//    public ResponseEntity<String> increaseCounterValue(@PathVariable int value) throws Exception {
//        counter.increaseBy(value).send();
//        return ResponseEntity.ok("Counter value increased by " + value + "<br>" + "Current value is " + getContractBalance());
//    }
//
//    @GetMapping("/decrease/{value}")
//    public ResponseEntity<String> decreaseCounterValue(@PathVariable int value) throws Exception {
//        counter.decrease(value).send();
//        return ResponseEntity.ok("Counter value decreased by " + value + "<br>" + "Current value is " + getContractBalance());
//    }


}
