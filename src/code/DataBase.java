package code;

import java.sql.*;

public class DataBase {
    /*
    Команды для sqlite для создания бд:

    .open SportBuildings.db

    CREATE TABLE Buildings (
    id integer primary key autoincrement,
    name varchar(200),
    beginBuiltYear integer,
    endBuiltYear integer,
    totalFunding integer,
    buildingType varchar(50),
    typeOfBuild varchar(100));
    */

    Connection connection;

    public void open(){
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:SportBuildings.db");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void insert(SportBuilding sb){
        var query =
                "insert into Buildings (name, beginBuiltYear, endBuiltYear, totalFunding, buildingType, typeOfBuild) " +
                        "values ('" + sb.name + "', '" + sb.beginBuiltYear+ "', '" +sb.endBuiltYear + "', '" +
                sb.totalFunding + "', '" + sb.buildingType+ "', '" + sb.typeOfBuild + "')";
        try {
            var statement = connection.createStatement();
            statement.executeUpdate(query);
            statement.close();
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public ResultSet select(String query) {
        ResultSet res;
        try {
            var statement = connection.createStatement();
            res = statement.executeQuery(query);
            return res;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void close() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
