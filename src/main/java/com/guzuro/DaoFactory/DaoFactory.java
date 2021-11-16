package com.guzuro.DaoFactory;

import com.guzuro.ProductDao.ProductDao;
import io.vertx.core.Vertx;

public abstract class DaoFactory {

    public static final int POSTGRES = 1;

    public abstract ProductDao getProductDao(Vertx vertx);

    public static DaoFactory getDaoFactory(int type) {
    switch (type) {
        case POSTGRES:
            return new PostgresDAOFactory();
        default:
            return null;
    }
}

}
