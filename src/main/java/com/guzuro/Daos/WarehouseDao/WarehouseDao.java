package com.guzuro.Daos.WarehouseDao;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;

public interface WarehouseDao {

    CompletableFuture<CopyOnWriteArrayList<Warehouse>> getWarehouses(int company_id);

    CompletableFuture<Warehouse> updateWarehouseInfo(Warehouse warehouse);
}
