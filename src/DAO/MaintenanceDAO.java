package DAO;

import Database.ConnectDB;
import Vehicle.Maintenance.Maintenance;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MaintenanceDAO {
    private final ConnectDB conDB;
    private String sql;

    public MaintenanceDAO(ConnectDB conDB){
        this.conDB = conDB;
    }

    private PreparedStatement setupConnection (String sql) throws SQLException {
        Connection con = conDB.getConnection(conDB.getServer(), conDB.getSchema());
        return con.prepareStatement(sql);
    }
    public Boolean saveMaintenance(Maintenance maintenance) {
        try {
            sql = "INSERT INTO `refuel`.`maintenance`(`idVehicle`,`idMaintenanceType`,`Date`,`Annotation`,`ReturnDate`) VALUES(?,?,?,?,?);";
            PreparedStatement preparedStatement = setupConnection(sql);
            preparedStatement.setInt(1, maintenance.getIdVehicle());
            preparedStatement.setInt(2, maintenance.getIdType());
            preparedStatement.setString(3, maintenance.getMaintenanceDate());
            preparedStatement.setString(4, maintenance.getAnnotation());
            preparedStatement.setString(5, maintenance.getDateReturn());
            preparedStatement.execute();
            conDB.CloseConnection(preparedStatement.getConnection());
            return true;
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
            return false;
        }
    }
    public List<Maintenance> listMaintenance(Maintenance maintenance) {
        List<Maintenance> maintenanceByVehicle = new ArrayList<>();
        try {
            sql = "SELECT `idMaintenance`,`maintenanceType`.`Type`,`Annotation`,`Date`,`ReturnDate`,concat(`vehicle`.`Model`,' - ',`vehicle`.`licensePlate`) as Vehicle\n" +
                    "FROM `refuel`.`maintenance` \n" +
                    "JOIN `refuel`.`maintenanceType` on `maintenanceType`.`idMaintenanceType` = `maintenance`.`idMaintenanceType`\n" +
                    "JOIN `refuel`.`vehicle` ON `vehicle`.`idVehicle` = `maintenance`.`IdVehicle`\n" +
                    "Where `maintenance`.idVehicle = ?;";
            PreparedStatement preparedStatement = setupConnection(sql);
            preparedStatement.setInt(1, maintenance.getIdVehicle());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Maintenance item = new Maintenance();
                item.setId(resultSet.getInt(1));
                item.setType(resultSet.getString(2));
                item.setAnnotation(resultSet.getString(3));
                item.setMaintenanceDate(resultSet.getString(4));
                item.setDateReturn(resultSet.getString(5));
                item.setVehiclePlate(resultSet.getString(6));
                item.setIdVehicle(maintenance.getIdVehicle());
                maintenanceByVehicle.add(item);
            }
            conDB.CloseConnection(preparedStatement.getConnection());
            return maintenanceByVehicle;
        } catch (SQLException exception) {
            System.err.println(exception.getMessage()+" Stack: "+ Arrays.toString(exception.getStackTrace()));
            return new ArrayList<>();
        }
    }

        public List<String> listMaintenanceType() {

            List<String> maintenanceType = new ArrayList<>();
            try {

                sql = "SELECT `idMaintenanceType`,`Type` FROM `refuel`.`maintenanceType`;";
                PreparedStatement preparedStatement = setupConnection(sql);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()){
                    maintenanceType.add(resultSet.getString(2));
                }
                conDB.CloseConnection(preparedStatement.getConnection());
                return maintenanceType;
            } catch (SQLException exception) {
                System.err.println(exception.getMessage()+" Stack: "+ Arrays.toString(exception.getStackTrace()));
                return new ArrayList<>();
            }
    }

    public boolean listIDMaintenanceType(Maintenance maintenance) {

        try {
            sql = "SELECT `idMaintenanceType`,`Type` FROM `refuel`.`maintenanceType` where `Type` =?;";
            PreparedStatement preparedStatement = setupConnection(sql);
            preparedStatement.setString(1, maintenance.getType());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                maintenance.setIdType(resultSet.getInt(1));
            }
            conDB.CloseConnection(preparedStatement.getConnection());
            return true;
        } catch (SQLException exception) {
            System.err.println(exception.getMessage()+" Stack: "+Arrays.toString(exception.getStackTrace()));
            maintenance.setId(-1);
            return false;
        }
    }

    public Boolean deleteMaintenance(Maintenance maintenance) {
        try {
            sql = "DELETE FROM `refuel`.`maintenance` WHERE idMaintenance =?;";
            PreparedStatement preparedStatement = setupConnection(sql);
            preparedStatement.setInt(1, maintenance.getId());
            preparedStatement.execute();
            conDB.CloseConnection(preparedStatement.getConnection());
            return true;
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
            return false;
        }
    }
}
