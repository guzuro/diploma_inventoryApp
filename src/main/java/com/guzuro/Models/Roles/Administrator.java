package com.guzuro.Models.Roles;

import com.guzuro.Daos.UserDao.User;

public class Administrator extends User {
    private Employee[] employes;

    public Administrator() {

    }

    public Administrator(Employee[] employes) {
        super();
        this.employes = employes;
    }

    public Employee[] getEmployes() {
        return employes;
    }

    public void setEmployes(Employee[] employes) {
        this.employes = employes;
    }
}
