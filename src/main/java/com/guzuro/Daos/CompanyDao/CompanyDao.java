package com.guzuro.Daos.CompanyDao;

import java.util.concurrent.CompletableFuture;

public interface CompanyDao {
    CompletableFuture<Company> createCompany(Company company);

    CompletableFuture<Company> getCompany(Number company_id);

    CompletableFuture<Company> updateCompanyInfo(Company company);
}