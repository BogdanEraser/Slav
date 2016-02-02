package main.java.slav.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Eraser on 28.01.2016.
 */
public class DBConnection {
    public static Connection conn;

    // --------ПОДКЛЮЧЕНИЕ К БАЗЕ ДАННЫХ--------
    public static Connection OpenDBConnection() throws ClassNotFoundException, SQLException {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("H2 JDBC driver not found. H2 JDBC driver не найден");
        }
        conn = null;
        try {
            String url = "jdbc:h2:./DB;IFEXISTS=TRUE;AUTO_SERVER=TRUE;AUTO_SERVER_PORT=9090";
            String user = "sa";
            String pwds = "123";
            conn = DriverManager.getConnection(url, user, pwds);
            return conn;
        } catch (SQLException e) {
            System.out.println("Connection failed. Ошибка соединения с БД");
        }
        return null;
    }

    // --------Закрытие--------
    public static void CloseDBConnetion() throws ClassNotFoundException, SQLException {
        conn.close();

        System.out.println("Соединения закрыты");
    }

}
