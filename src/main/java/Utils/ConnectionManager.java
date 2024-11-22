package Utils;

import Exceptions.SomeThingWrongWithBDException;

import java.sql.*;

public final class ConnectionManager {
    private static final String URL = "db.url";

    public static Connection getConnection() {
        Connection conn;
        try{
            conn = DriverManager.getConnection(PropertiesUtil.getProperty(URL));
        } catch (SQLException e) {
            throw new SomeThingWrongWithBDException();
        }
        return conn;
    }

    public static void closeConnection(Connection conn) {
        try{
            conn.close();
        }catch (SQLException e){
            throw new SomeThingWrongWithBDException();
        }
    }





}




