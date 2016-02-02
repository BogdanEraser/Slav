package main.java.slav.model.implementation;

import main.java.slav.model.DBConnection;
import main.java.slav.model.Tovar;
import main.java.slav.model.TovarDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eraser on 01.02.2016.
 */
public class TovarDAOImpl implements TovarDAO {

    @Override
    public boolean addTovar(Tovar tovar) throws SQLException, ClassNotFoundException {

        Connection conn = DBConnection.OpenDBConnection();
        if (conn != null) {
            PreparedStatement st = conn.prepareStatement("INSERT INTO tblTovar (ID, Name, Weight, Units, GOST, Price, Category) VALUES (default, ?, ?, ?, ?, ?, ?)");
            st.setString(1, tovar.getName());
            st.setDouble(2, tovar.getWeight());
            st.setString(3, tovar.getUnits());
            st.setString(4, tovar.getGost());
            st.setDouble(5, tovar.getPrice());
            st.setInt(6, tovar.getCategory());
            st.execute();
            st.close();
            conn.close();
            //if (rs.next()) {
            //    if (rs.getlong(1)==-1) return false;
            //}

            return true;
        } else {
            System.out.println("Connection failed. Ошибка соединения с БД");
            conn.close();
            return false;
        }


    }

    @Override
    public Tovar getTovarByName(String tovarName) throws SQLException {
        return null;
    }

    @Override
    public Tovar getTovarByID(long tovarID) throws SQLException, ClassNotFoundException {

        Connection conn = DBConnection.OpenDBConnection();
        PreparedStatement st = conn.prepareStatement("SELECT * FROM TBLTOVAR WHERE ID = ?");
        st.setLong(1, tovarID);
        ResultSet rs = st.executeQuery();
        Tovar tovar = new Tovar();

        if (rs.next()) {
            tovar.setID(rs.getLong("ID"));
            tovar.setName(rs.getString("Name"));
            tovar.setWeight(rs.getDouble("WEIGHT"));
            tovar.setUnits(rs.getString("units"));
            tovar.setGost(rs.getString("GOST"));
            tovar.setPrice(rs.getDouble("price"));
            tovar.setCategory(rs.getInt("category"));
        }
        rs.close();
        st.close();
        conn.close();
        return tovar;
    }

    @Override
    public List<Tovar> getAllTovars() throws SQLException, ClassNotFoundException {
        Connection conn = DBConnection.OpenDBConnection();
        PreparedStatement st = conn.prepareStatement("SELECT * FROM tblTovar");
        ResultSet rs = st.executeQuery();

        ArrayList<Tovar> tovarList = new ArrayList<>();
        while (rs.next()) {
            tovarList.add(new Tovar(rs.getLong("ID"),
                    rs.getString("Name"),
                    rs.getDouble("WEIGHT"),
                    rs.getString("units"),
                    rs.getString("GOST"),
                    rs.getDouble("price"),
                    rs.getInt("category")));
        }
        rs.close();
        st.close();
        conn.close();
        return tovarList;
    }

    @Override
    public List<Tovar> getAllTovarsByCategory(Integer tovarCategory) throws SQLException, ClassNotFoundException {

        Connection conn = DBConnection.OpenDBConnection();
        PreparedStatement st = conn.prepareStatement("SELECT * FROM tblTovar; WHERE Category=?");
        st.setInt(1, tovarCategory);
        ResultSet rs = st.executeQuery();

        ArrayList<Tovar> tovarList = new ArrayList<>();
        while (rs.next()) {
            tovarList.add(new Tovar(rs.getInt("ID"),
                    rs.getString("Name"),
                    rs.getDouble("WEIGHT"),
                    rs.getString("units"),
                    rs.getString("GOST"),
                    rs.getDouble("price"),
                    rs.getInt("category")));
        }

        rs.close();
        st.close();
        conn.close();
        return tovarList;
    }

    @Override
    public boolean updateTovar(Tovar tovar) throws SQLException, ClassNotFoundException {

        Connection conn = DBConnection.OpenDBConnection();
        PreparedStatement st = conn.prepareStatement("UPDATE tblTovar SET Name = '?', Weight = ?, Units = '?', GOST = '?', Price = ?, Category = ? WHERE ID = ?;");
        st.setString(1, tovar.getName());
        st.setDouble(2, tovar.getWeight());
        st.setString(3, tovar.getUnits());
        st.setString(4, tovar.getGost());
        st.setDouble(5, tovar.getPrice());
        st.setInt(6, tovar.getCategory());
        st.setLong(7, tovar.getID());
        ResultSet rs = st.executeQuery();
        conn.close();

        return rs.next();

    }

    @Override
    public void deleteTovar(Integer tovarID) {

    }
}
