package com.guzuro.Models.Roles;

import com.guzuro.Daos.UserDao.Employement;
import com.guzuro.Daos.UserDao.User;

public class Employee extends User {

    private Employement employement;

    public Employee(Employee employee) {
        super(employee);
        this.employement = employee.employement;
    }

    public Employee() {

    }

    public Employement getEmployement() {
        return employement;
    }

    public void setEmployement(Employement employement) {
        this.employement = employement;
    }
}
