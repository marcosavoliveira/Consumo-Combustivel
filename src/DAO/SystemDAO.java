package DAO;

import Vehicle.Vehicle;

import java.util.List;

public interface SystemDAO {
    Boolean save(Vehicle bean);
    Boolean delete(Object bean);
    Boolean getIdTable(Object bean);
    List<Object> listAll(Object bean);
    Boolean signInUser(Object bean);
}
