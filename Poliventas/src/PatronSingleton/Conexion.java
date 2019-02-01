/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PatronSingleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Luis A. Sarango-Parrales
 */
public class Conexion {

    private static Conexion instancia;
    private Connection connection;
    private final String USER = "root",
            PWD = "Programming0",
            URL = "jdbc:mysql://localhost:3306/PoliventasDB?autoReconnect=true&useSSL=false";

    private Conexion() {
    }

    public static Conexion getInstancia() {
        if (instancia == null) {
            instancia = new Conexion();
        }
        return instancia;
    }

    public void conectar() throws Exception {

        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(URL, USER, PWD);
        System.out.println("Conexión MySql exitosa");
    }

    public void desconectar() throws SQLException {
        if (connection != null) {
            if (!connection.isClosed()) {
                connection.close();
            }
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

}
