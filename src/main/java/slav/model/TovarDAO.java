package main.java.slav.model;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Eraser on 01.02.2016.
 */
public interface TovarDAO {
    boolean addTovar(Tovar tovar) throws SQLException;

    Tovar getTovarByName(String tovarName) throws SQLException;

    Tovar getTovarByID(long tovarID) throws SQLException;

    ArrayList<Tovar> getAllTovars() throws SQLException;

    ArrayList<Tovar> getAllTovarsByCategory(Integer tovarCategory) throws SQLException;

    boolean mergeTovarBatch(ArrayList<Tovar> tovarList) throws SQLException;

    boolean updateTovar(Tovar tovar) throws SQLException;

    boolean deleteTovar(long tovarID) throws SQLException;

}
