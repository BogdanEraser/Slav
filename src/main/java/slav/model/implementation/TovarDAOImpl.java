package main.java.slav.model.implementation;

import javafx.beans.property.SimpleObjectProperty;
import main.java.slav.model.DBConnection;
import main.java.slav.model.Tovar;
import main.java.slav.model.TovarDAO;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Eraser on 01.02.2016.
 */
public class TovarDAOImpl implements TovarDAO {

    @Override
    public boolean addTovar(Tovar tovar) throws SQLException {

        Connection conn = DBConnection.OpenDBConnection();
        if (conn != null) {
            PreparedStatement st = conn.prepareStatement("INSERT INTO tblTovar (ID, Name, Weight, Units, GOST, Price, Category) VALUES (default, ?, ?, ?, ?, ?, ?)");
            st.setString(1, tovar.getName());
            st.setBigDecimal(2, tovar.getWeight());
            st.setString(3, tovar.getUnits());
            st.setString(4, tovar.getGost());
            st.setBigDecimal(5, tovar.getPrice());
            st.setInt(6, tovar.getCategory());
            st.execute();
            if (st.getUpdateCount() == 1) {
                st.close();
                conn.close();
                return true;
            } else {
                System.out.println("Insert failed. Ошибка добавления данных в таблицу товаров");
                st.close();
                conn.close();
                return false;
            }
        } else {
            System.out.println("Connection failed. Ошибка соединения с БД");
            return false;
        }
    }

    @Override
    public Tovar getTovarByName(String tovarName) throws SQLException {
        return null;
    }

    @Override
    public Tovar getTovarByID(long tovarID) throws SQLException {
        Tovar tovar = new Tovar();
        Connection conn = DBConnection.OpenDBConnection();
        if (conn != null) {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM TBLTOVAR WHERE ID = ?");
            st.setLong(1, tovarID);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                tovar.setID(rs.getLong("ID"));
                tovar.setName(rs.getString("Name"));
                tovar.setWeight(rs.getBigDecimal("WEIGHT"));
                tovar.setUnits(rs.getString("units"));
                tovar.setGost(rs.getString("GOST"));
                tovar.setPrice(rs.getBigDecimal("price"));
                tovar.setCategory(rs.getInt("category"));
            }
            rs.close();
            st.close();
            conn.close();
        }
        return tovar;
    }

    @Override
    public ArrayList<Tovar> getAllTovars() throws SQLException {
        Connection conn = DBConnection.OpenDBConnection();
        ArrayList<Tovar> tovarList = new ArrayList<>();
        if (conn != null) {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM tblTovar ORDER BY ID");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                SimpleObjectProperty<BigDecimal> p = new SimpleObjectProperty<BigDecimal>(rs.getBigDecimal("price"));
                tovarList.add(new Tovar(rs.getLong("ID"),
                        rs.getString("Name"),
                        rs.getBigDecimal("WEIGHT"),
                        rs.getString("units"),
                        rs.getString("GOST"),
                        p,
                        rs.getInt("category")));
            }
            rs.close();
            st.close();
            conn.close();
        } else {
            System.out.println("Connection failed. Ошибка соединения с БД");
        }
        return tovarList;
    }

    @Override
    public ArrayList<Tovar> getAllTovarsByCategory(Integer tovarCategory) throws SQLException {

        Connection conn = DBConnection.OpenDBConnection();
        ArrayList<Tovar> tovarList = new ArrayList<>();
        if (conn != null) {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM tblTovar WHERE Category=?");
            st.setInt(1, tovarCategory);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                tovarList.add(new Tovar(rs.getInt("ID"),
                        rs.getString("Name"),
                        rs.getBigDecimal("WEIGHT"),
                        rs.getString("units"),
                        rs.getString("GOST"),
                        (SimpleObjectProperty<BigDecimal>) rs.getObject("price"),
                        rs.getInt("category")));
            }
            rs.close();
            st.close();
            conn.close();
        } else {
            System.out.println("Connection failed. Ошибка соединения с БД");
        }
        return tovarList;
    }

    @Override
    public boolean mergeTovarBatch(ArrayList<Tovar> tovarList) throws SQLException {

        Connection conn = DBConnection.OpenDBConnection();
        if (conn != null) {
            //PreparedStatement st = conn.prepareStatement("UPDATE tblTovar SET Name = ?, Weight = ?, Units = ?, GOST = ?, Price = ?, Category = ? WHERE ID = ?;");
            PreparedStatement st = conn.prepareStatement("MERGE INTO tblTovar (ID, Name, Weight, Units, GOST, Price, Category) KEY (ID) VALUES(?, ?, ?, ?, ?, ?, ?)");
            final int batchSize = 100;
            int count = 0;
            for (Tovar tovar : tovarList) {
                st.setLong(1, tovar.getID());
                st.setString(2, tovar.getName());
                st.setBigDecimal(3, tovar.getWeight());
                st.setString(4, tovar.getUnits());
                st.setString(5, tovar.getGost());
                st.setBigDecimal(6, tovar.getPrice());
                st.setInt(7, tovar.getCategory());
                st.addBatch();
                if (++count % batchSize == 0) {
                    st.executeBatch();
                }
            }
            st.executeBatch(); // insert remaining records
            if (st.getUpdateCount() == 1) {
                st.close();
                conn.close();
                return true;
            } else {
                System.out.println("Update failed. Ошибка обновления данных в таблице товаров");
                st.close();
                conn.close();
                return false;
            }
        } else {
            System.out.println("Connection failed. Ошибка соединения с БД");
            return false;
        }
    }


    @Override
    public boolean updateTovar(Tovar tovar) throws SQLException {

        Connection conn = DBConnection.OpenDBConnection();
        if (conn != null) {
            PreparedStatement st = conn.prepareStatement("UPDATE tblTovar SET Name = ?, Weight = ?, Units = ?, GOST = ?, Price = ?, Category = ? WHERE ID = ?;");
            st.setString(1, tovar.getName());
            st.setBigDecimal(2, tovar.getWeight());
            st.setString(3, tovar.getUnits());
            st.setString(4, tovar.getGost());
            st.setBigDecimal(5, tovar.getPrice());
            st.setInt(6, tovar.getCategory());
            st.setLong(7, tovar.getID());
            st.executeUpdate();
            if (st.getUpdateCount() == 1) {
                st.close();
                conn.close();
                return true;
            } else {
                System.out.println("Update failed. Ошибка изменения данных в таблице товаров");
                st.close();
                conn.close();
                return false;
            }
        } else {
            System.out.println("Connection failed. Ошибка соединения с БД");
            return false;
        }
    }

    @Override
    public boolean deleteTovar(long tovarID) throws SQLException {
        Connection conn = DBConnection.OpenDBConnection();
        if (conn != null) {
            PreparedStatement st = conn.prepareStatement("DELETE FROM tblTovar WHERE ID = ?");
            st.setLong(1, tovarID);
            st.execute();
            if (st.getUpdateCount() == 1) {
                st.close();
                conn.close();
                return true;
            } else {
                System.out.println("Insert failed. Ошибка добавления данных в таблицу товаров");
                st.close();
                conn.close();
                return false;
            }
        } else {
            System.out.println("Connection failed. Ошибка соединения с БД");
            return false;
        }
    }
}
