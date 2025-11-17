package com.solvd.spotify.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

import static com.solvd.spotify.config.GlobalProperties.*;

public final class DataSourceProvider {

    private static volatile HikariDataSource ds;

    private DataSourceProvider() {}

    public static DataSource getDataSource() {
        if (ds == null) {
            synchronized (DataSourceProvider.class) {
                if (ds == null) {
                    HikariConfig cfg = new HikariConfig();
                    cfg.setJdbcUrl(JDBC_URL);
                    cfg.setUsername(POSTGRES_USERNAME);
                    cfg.setPassword(POSTGRES_PASSWORD);
                    cfg.setMaximumPoolSize(10);
                    cfg.setMinimumIdle(2);
                    cfg.setPoolName("spotify-hikari-pool");
                    cfg.addDataSourceProperty("cachePrepStmts", "true");
                    cfg.addDataSourceProperty("prepStmtCacheSize", "250");
                    cfg.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
                    cfg.setAutoCommit(false);
                    ds = new HikariDataSource(cfg);
                }
            }
        }
        return ds;
    }

    public static void close() {
        if (ds != null) ds.close();
    }
}
