package Utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;

public final class ConnectionManager {
    private static final String URL = "db.url";
    private static final Integer POOL = 10;
    private static final String DRIVER = "db.jdbcDriver";
    private static final HikariDataSource dataSource;


    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(PropertiesUtil.getProperty(URL));
        config.setMaximumPoolSize(POOL);
        config.setDriverClassName(PropertiesUtil.getProperty(DRIVER));
        config.setMaxLifetime(6000000);
        config.setIdleTimeout(3000000);
        dataSource = new HikariDataSource(config);

    }
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }








//
//    public static Connection getConnection() {
//        Connection conn;
//
//
//        try{
//            Class.forName("org.sqlite.JDBC");
//            conn = DriverManager.getConnection(PropertiesUtil.getProperty(URL));
//
//        } catch (SQLException e) {
//            throw new SomeThingWrongWithBDException();
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//
//        return conn;
//    }




//    private static DataSource dataSource;
//
//    static {
//        try {
//            SQLiteDataSource sqLiteDataSource = new SQLiteDataSource();
//            sqLiteDataSource.setUrl(PropertiesUtil.getProperty(URL));
//            dataSource = sqLiteDataSource;
//        } catch (Exception e) {
//            throw new SomeThingWrongWithBDException();
//        }
//    }
//
//    public static Connection getConnection() throws SQLException {
//        return dataSource.getConnection();
//    }
}











