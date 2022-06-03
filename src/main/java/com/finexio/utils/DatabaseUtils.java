package com.finexio.utils;

import org.testng.Assert;

import static com.finexio.config.ConfigurationManager.configuration;

import java.sql.*;

/**
 * @author Ivan Katumba on 10/12/2021
 * @project HFD-Automation
 */
public class DatabaseUtils {

    @SuppressWarnings("unused")
    private String databaseurl;
    @SuppressWarnings("unused")
    private String dbusername;
    @SuppressWarnings("unused")
    private String dbpsswd;
    private String dbEnvironment;

    public DatabaseUtils() {

        try {

            databaseurl = configuration().dbserver();
            dbusername = configuration().dbuser();
            dbpsswd = configuration().dbpsswd();

        } catch (Exception e) {
            System.out.println("Failed to Read from DB Property File");
        }
    }

    public final Connection getConnection() {

        dbEnvironment = System.getProperty("environment");
        if (dbEnvironment == null) {

            dbEnvironment = "sandbox";
        }

        try {

            switch (dbEnvironment) {

                case "stage":
                    databaseurl = configuration().dbserver();
                    break;

                case "sandbox":
                    databaseurl = configuration().dbsandboxserver();
                    break;

            }

            dbusername = configuration().dbuser();
            dbpsswd = configuration().dbpsswd();
            System.out.println("The Database env to connect to is :" + databaseurl);
            return DriverManager.getConnection(databaseurl, dbusername, dbpsswd);
            // getConnection(databaseurl,dbusername,dbpsswd);
        } catch (SQLException e) {
            e.printStackTrace();
            Assert.assertFalse(true, "Connection Failed! Check out Console");
            return null;
        }

    }

    public ResultSet getDBResult(String query) {

        Connection conn = getConnection();
        Statement sqlstatment;
        ResultSet rs = null;

        try {
            sqlstatment = conn.createStatement();
            rs = sqlstatment.executeQuery(query);

        } catch (SQLException e) {
            if (!e.getMessage().equals("No results were returned by query.")) {
                System.out.println("Error on query:" + query);
                System.out.println(e.getMessage());

            }

        }
        return rs;

    }

}
