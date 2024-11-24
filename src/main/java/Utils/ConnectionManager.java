package Utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;

public final class ConnectionManager {
    private static final String URL = "db.url";
    private static final String POOL = "db.maxPool";
    private static final String DRIVER = "db.jdbcDriver";
    private static final HikariDataSource dataSource;


    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(PropertiesUtil.getProperty(URL));
        config.setMaximumPoolSize(Integer.parseInt(PropertiesUtil.getProperty(POOL)));
        config.setDriverClassName(PropertiesUtil.getProperty(DRIVER));
        config.setMaxLifetime(18_000_000);    //5 hour lifetime
        config.setIdleTimeout(1_800_000);     // 30 min non-active
        dataSource = new HikariDataSource(config);

    }
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }


}











