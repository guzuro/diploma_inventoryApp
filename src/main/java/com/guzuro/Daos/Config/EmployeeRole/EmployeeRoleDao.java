package com.guzuro.Daos.Config.EmployeeRole;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;

public interface EmployeeRoleDao {
    CompletableFuture<EmployeeRole> addEmployeeRole(EmployeeRole employeeRole, int company_id);

    CompletableFuture<EmployeeRole> updateEmployeeRole(EmployeeRole employeeRole);

    CompletableFuture<Boolean> deleteEmployeeRole(int role_id);

    CompletableFuture<CopyOnWriteArrayList<EmployeeRole>> getEmployeeRoles(int company_id);
}