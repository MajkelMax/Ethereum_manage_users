// SPDX-License-Identifier: GPL-3.0
pragma solidity ^0.8.0;

contract EmployeeContract {

        address private owner;

        struct Employee {
        string firstName;
        string lastName;
        string position;
    }
    constructor() {
        owner = msg.sender;
    }

    modifier onlyOwner() {
        require(msg.sender == owner);
        _;
    }

    Employee[] employees;

    function addEmployee(string memory _firstName, string memory _lastName, string memory _position) public onlyOwner{
        Employee memory newEmployee = Employee(_firstName, _lastName, _position);
        employees.push(newEmployee);
    }

    function getEmployeesCount() public view returns (uint) {
        return employees.length;
    }

    function getEmployee(uint index) public view returns (string memory, string memory, string memory) {
        require(index < employees.length, "Invalid index");
        Employee memory employee = employees[index];
        return (employee.firstName, employee.lastName, employee.position);
    }
}