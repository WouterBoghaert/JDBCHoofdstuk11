package be.vdab;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BierenVanEenMaandMain {
    private static final String URL = "jdbc:mysql://localhost/bieren?useSSL=false";
    private static final String USER = "cursist";
    private static final String PASSWORD = "cursist";
    private static final String SELECT_BIEREN_OP_MAAND_VERKOCHTSINDS =
        "select verkochtsinds, naam " +
        "from bieren " +
        "where {fn month(verkochtsinds)} = ? " +
        "order by verkochtsinds";
    public static void main(String[] args) {
        try(Scanner scanner = new Scanner(System.in)){
            System.out.println("Maandnummer (1 - 12)?:");
            int maand = scanner.nextInt();
            try(Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement statement = connection.prepareStatement(SELECT_BIEREN_OP_MAAND_VERKOCHTSINDS)){
                statement.setInt(1, maand);
                try(ResultSet resultSet = statement.executeQuery()){
                    while(resultSet.next()){
                        System.out.println(resultSet.getDate("verkochtsinds") + " " +
                            resultSet.getString("naam"));
                    }
                }
            }
            catch(SQLException ex){
                ex.printStackTrace();
            }
        }
        catch(InputMismatchException ex){
            System.out.println("Verkeerde maand");
        }
    }    
}
