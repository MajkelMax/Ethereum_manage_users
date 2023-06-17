package com.ethereum.manageusers.dto;

import org.apache.commons.lang3.StringUtils;

public class EmployeeDTO {
    private String firstName;
    private String lastName;
    private String position;

    public EmployeeDTO(String firstName, String lastName, String position) {
        this.firstName = StringUtils.leftPad(firstName, 32);
        this.lastName = StringUtils.leftPad(lastName, 32);
        this.position = StringUtils.leftPad(position, 32);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPosition() {
        return position;
    }

}
