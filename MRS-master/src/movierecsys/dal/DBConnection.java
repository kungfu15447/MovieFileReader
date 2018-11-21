/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierecsys.dal;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.Connection;

/**
 *
 * @author Frederik Jensen
 */
public class DBConnection
{
    private static final String SERVER_NAME = "10.176.111.31";
    private static final String DB_NAME = "moviesrs";
    private static final String USER_NAME = "CS2018A_11";
    private static final String PASSWORD = "CS2018A_11";
    SQLServerDataSource ds;
    
    public DBConnection() {
        ds = new SQLServerDataSource();
        ds.setServerName(SERVER_NAME);
        ds.setDatabaseName(DB_NAME);
        ds.setUser(USER_NAME);
        ds.setPassword(PASSWORD);
    }
    public Connection getConnection() throws SQLServerException {
        Connection con = ds.getConnection();
        return con;
    }
}
