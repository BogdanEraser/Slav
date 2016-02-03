package main.java.slav.model;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Eraser on 01.02.2016.
 */
public interface TovarDAO {
    boolean addTovar(Tovar tovar) throws SQLException, ClassNotFoundException;

    Tovar getTovarByName(String tovarName) throws SQLException;

    Tovar getTovarByID(long tovarID) throws SQLException, ClassNotFoundException, UnsupportedEncodingException;

    List<Tovar> getAllTovars() throws SQLException, ClassNotFoundException, UnsupportedEncodingException;

    List<Tovar> getAllTovarsByCategory(Integer tovarCategory) throws SQLException, ClassNotFoundException;

    boolean updateTovar(Tovar tovar) throws SQLException, ClassNotFoundException;

    boolean deleteTovar(long tovarID) throws SQLException, ClassNotFoundException;

}
