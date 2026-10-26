/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.monster.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author sebas
 */

/**
 * Conexion a la bd
 *  es el puente entre el codigo Java y la base de datos
 *  MariaDB
 * 
 */
public class AccesoDB {
    
    public static Connection getConnection() throws SQLException {
        Connection cn = null;
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            // URL configurada con puerto 3307
            String url = "jdbc:mariadb://localhost:3307/eurekabank";
            String user = "root";
            String pass = "rootroot";
            
            cn = DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException e) {
            throw new SQLException("No se encontró el driver de MariaDB: " + e.getMessage());
        }
        return cn;
    }
}