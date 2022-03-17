package com.guzuro.Daos.Config.WarehouseDao;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;

public interface WarehouseDao {

    CompletableFuture<Warehouse> addWarehouse(Warehouse warehouse, int company_id);

    CompletableFuture<CopyOnWriteArrayList<Warehouse>> getWarehouses(int company_id);

    CompletableFuture<Warehouse> updateWarehouseInfo(Warehouse warehouse);
}
