package controladores;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexionDB {

	public static Connection crearConexion() {
		
		Connection conexion = null;
		
		Properties propiedades = getProperties();
		String url = propiedades.getProperty("db.url");
		String usuario = propiedades.getProperty("db.usuario");
		String contrasena = propiedades.getProperty("db.contrasena");
		
		try {
			conexion = DriverManager.getConnection(url, usuario, contrasena);
			System.out.println("Se conectó exitosamente a la base de datos.");
		}catch(SQLException ex) {
			System.out.println("Error al conectarse a la base de datos: " + ex.getMessage());
		}
		
		return conexion;
		
	}
	
	public static Properties getProperties() {
		Properties propiedades = new Properties();
		
		try(FileInputStream archivoPropiedades = new FileInputStream("src/configuracion.properties")){
			propiedades.load(archivoPropiedades);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return propiedades;
		
	}
	
}
