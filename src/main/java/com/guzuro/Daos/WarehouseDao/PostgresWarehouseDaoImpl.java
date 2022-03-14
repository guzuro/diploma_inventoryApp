package com.guzuro.Daos.WarehouseDao;

import com.guzuro.Daos.DaoFactory.PostgresDAOFactory;
import io.vertx.core.Vertx;
import io.vertx.sqlclient.SqlClient;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;

public class PostgresWarehouseDaoImpl implements WarehouseDao {
    SqlClient pgClient;

    public PostgresWarehouseDaoImpl(Vertx vertx) {
        pgClient = PostgresDAOFactory.createConnection(vertx);
    }

    @Override
    public CompletableFuture<CopyOnWriteArrayList<Warehouse>> getWarehouses(int company_id) {
        return null;
    }

    @Override
    public CompletableFuture<Warehouse> updateWarehouseInfo(Warehouse warehouse) {
        return null;
    }
}
