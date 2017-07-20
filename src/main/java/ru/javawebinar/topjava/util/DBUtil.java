package ru.javawebinar.topjava.util;


import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBUtil {
    private static Connection connection;

    public static Connection getConnection(){
        if (connection != null){
            return connection;
        } else {
            try {
                Properties properties = new Properties();
                InputStream inputStream = DBUtil.class.getClassLoader().getResourceAsStream("/application.properties");
                properties.load(inputStream);
                String driver = properties.getProperty("database.driver");
                String url = properties.getProperty("database.url");
                String user = properties.getProperty("database.user");
                String password = properties.getProperty("database.password");
                Class.forName(driver);
                connection = DriverManager.getConnection(url, user, password);
            }  catch (Exception e) {
                e.printStackTrace();
            }
            return connection;
        }
    }
}
