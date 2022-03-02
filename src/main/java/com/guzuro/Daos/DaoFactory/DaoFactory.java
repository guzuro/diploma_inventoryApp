package com.guzuro.Daos.DaoFactory;

import com.guzuro.Daos.AuthDao.AuthDao;
import com.guzuro.Daos.ProductDao.ProductDao;
import com.guzuro.Daos.UserDao.UserDao;
import io.vertx.core.Vertx;

public abstract class DaoFactory {

    public static final int POSTGRES = 1;

    public abstract ProductDao getProductDao(Vertx vertx);

    public abstract UserDao getUserDao(Vertx vertx);

    public abstract AuthDao getAuthDao(Vertx vertx);

    public static DaoFactory getDaoFactory(int type) {
        switch (type) {
            case POSTGRES:
                return new PostgresDAOFactory();
            default:
                return null;
        }
    }

}
