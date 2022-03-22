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
    private ConnectDB conDB;
    public MaintenanceDAO(ConnectDB conDB){
        this.conDB = conDB;
    }
    public Boolean saveMaintenance(Maintenance maintenance) {
        try {
            Connection con = conDB.getConnection(conDB.getServer(), conDB.getSchema());
            String sql = "INSERT INTO `refuel`.`maintenance`(`idVehicle`,`idMaintenanceType`,`Date`,`Annotation`,`ReturnDate`) VALUES(?,?,?,?,?);";
            PreparedStatement preparedSt = con.prepareStatement(sql);
            preparedSt.setInt(1, maintenance.getIdVehicle());
            preparedSt.setInt(2, maintenance.getIdType());
            preparedSt.setString(3, maintenance.getMaintenanceDate());
            preparedSt.setString(4, maintenance.getAnnotation());
            preparedSt.setString(5, maintenance.getDateReturn());
            preparedSt.execute();
            preparedSt.close();
            conDB.CloseConnection(con);
            return true;
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
            return false;
        }
    }
    public List<Maintenance> listMaintenance(Maintenance maintenance) {
        List<Maintenance> maintenanceByVehicle = new ArrayList<>();
        try {
            Connection con = conDB.getConnection(conDB.getServer(), conDB.getSchema());
            String sql = "SELECT `idMaintenance`,`maintenanceType`.`Type`,`Annotation`,`Date`,`ReturnDate`,concat(`vehicle`.`Model`,' - ',`vehicle`.`licensePlate`) as Vehicle\n" +
                    "FROM `refuel`.`maintenance` \n" +
                    "JOIN `refuel`.`maintenanceType` on `maintenanceType`.`idMaintenanceType` = `maintenance`.`idMaintenanceType`\n" +
                    "JOIN `refuel`.`vehicle` ON `vehicle`.`idvehicle` = `maintenance`.`IdVehicle`\n" +
                    "Where `maintenance`.idVehicle = ?;";
            PreparedStatement preparedSt = con.prepareStatement(sql);
            preparedSt.setInt(1, maintenance.getIdVehicle());
            ResultSet resultSet = preparedSt.executeQuery();
            while (resultSet.next()){
                Maintenance item = new Maintenance();
                item.setId(resultSet.getInt(1));
                item.setType(resultSet.getString(2));
                item.setAnnotation(resultSet.getString(3));
                item.setMaintenanceDate(resultSet.getString(4));
                item.setDateReturn(resultSet.getString(5));
                item.setVehiclePlate(resultSet.getString(6));
                maintenanceByVehicle.add(item);
            }
            preparedSt.close();
            conDB.CloseConnection(con);
            return maintenanceByVehicle;
        } catch (SQLException exception) {
            System.err.println(exception.getMessage()+" Stack: "+ Arrays.toString(exception.getStackTrace()));
            return new ArrayList<>();
        }
    }

        public List<String> listMaintenanceType() {
            List<String> maintenanceType = new ArrayList<>();
            try {
                Connection con = conDB.getConnection(conDB.getServer(), conDB.getSchema());
                String sql = "SELECT `idMaintenanceType`,`Type` FROM `refuel`.`maintenanceType`;";
                PreparedStatement preparedSt = con.prepareStatement(sql);
                ResultSet resultSet = preparedSt.executeQuery();
                maintenanceType.add("Tipo");
                while (resultSet.next()){
                    maintenanceType.add(resultSet.getString(2));
                }
                preparedSt.close();
                conDB.CloseConnection(con);
                return maintenanceType;
            } catch (SQLException exception) {
                System.err.println(exception.getMessage()+" Stack: "+ Arrays.toString(exception.getStackTrace()));
                return new ArrayList<>();
            }
    }

    public boolean listIDMaintenanceType(Maintenance maintenance) {

        try {
            Connection con = conDB.getConnection(conDB.getServer(), conDB.getSchema());
            String sql = "SELECT `idMaintenanceType`,`Type` FROM `refuel`.`maintenanceType` where `Type` =?;";
            PreparedStatement preparedSt = con.prepareStatement(sql);
            preparedSt.setString(1, maintenance.getType());
            ResultSet resultSet = preparedSt.executeQuery();
            while (resultSet.next()){
                maintenance.setIdType(resultSet.getInt(1));
            }
            preparedSt.close();
            conDB.CloseConnection(con);
            return true;
        } catch (SQLException exception) {
            System.err.println(exception.getMessage()+" Stack: "+Arrays.toString(exception.getStackTrace()));
            maintenance.setId(-1);
            return false;
        }
    }

    public Boolean deleteMaintenance(Maintenance maintenance) {
        try {
            Connection con = conDB.getConnection(conDB.getServer(), conDB.getSchema());
            String sql = "DELETE FROM `refuel`.`maintenance` WHERE idMaintenance =?;";
            PreparedStatement preparedSt = con.prepareStatement(sql);
            preparedSt.setInt(1, maintenance.getId());
            preparedSt.execute();
            preparedSt.close();
            conDB.CloseConnection(con);
            return true;
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
            return false;
        }
    }
}
