package main.java.slav.model;

import java.sql.*;

/**
 * Created by Eraser on 28.01.2016.
 */
public class WorkWithDB {
    public static Connection conn;
    public static Statement statmt;
    public static ResultSet resSet;

    // --------ПОДКЛЮЧЕНИЕ К БАЗЕ ДАННЫХ--------
    public static void Conn() throws ClassNotFoundException, SQLException {
        conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("SQLite JDBC driver not found");
        }
        conn = DriverManager.getConnection("jdbc:sqlite:DB.s3db");

        //переводим режим журналирования в WAL (для оптимизации работы с БД). By default busy_timeout=3000ms
        Statement stat = conn.createStatement();
        //stat.executeQuery("pragma journal_mode=delete");  //режим по умолчанию, более надежен в использовании, но немного медленнее
        stat.executeQuery("pragma journal_mode=wal");

        System.out.println("База Подключена!");
    }

    // --------Создание таблицы--------
    public static void CreateUsersDB() throws ClassNotFoundException, SQLException {
        statmt = conn.createStatement();
        statmt.execute("CREATE TABLE if not exists 'users' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'name' text, 'phone' INT);");

        System.out.println("Таблица создана или уже существует.");
    }

    // --------Заполнение таблицы--------
    public static void WriteUsersDB() throws SQLException {
        statmt.execute("INSERT INTO 'users' ('name', 'phone') VALUES ('Petya', 125453); ");
        statmt.execute("INSERT INTO 'users' ('name', 'phone') VALUES ('Vasya', 321789); ");
        statmt.execute("INSERT INTO 'users' ('name', 'phone') VALUES ('Masha', 456123); ");

        System.out.println("Таблица заполнена");
    }

    // -------- Вывод таблицы--------
    public static void ReadUsersDB() throws ClassNotFoundException, SQLException {
        resSet = statmt.executeQuery("SELECT * FROM users");

        while (resSet.next()) {
            int id = resSet.getInt("id");
            String name = resSet.getString("name");
            String phone = resSet.getString("phone");
            System.out.println("ID = " + id);
            System.out.println("name = " + name);
            System.out.println("phone = " + phone);
            System.out.println();
        }

        System.out.println("Таблица выведена");
    }

    // --------Закрытие--------
    public static void CloseDB() throws ClassNotFoundException, SQLException {
        conn.close();
        statmt.close();
        resSet.close();

        System.out.println("Соединения закрыты");
    }

}
