package modelos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import controladores.ConexionDB;

public class UsuarioDAO {
	
	public boolean autenticarUsuario(String nombreUsuario, String contrasena) {
		
		try(Connection conexion = ConexionDB.crearConexion()){
			String query = "SELECT * FROM usuarios WHERE nombre_usuario = ? AND contrasena = ?";
			PreparedStatement statement = conexion.prepareStatement(query);
			
			statement.setString(1, nombreUsuario);
			statement.setString(2, contrasena);
			
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()) {
				actualizarUltimoLogin(nombreUsuario);
				return true;
			}else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
		
	}
	
	public void actualizarUltimoLogin(String nombreUsuario) {
		
		try(Connection conexion = ConexionDB.crearConexion()){
			String updateQuery = "UPDATE usuarios SET ultimo_login = ? WHERE nombre_usuario = ?";
			PreparedStatement updateStatement = conexion.prepareStatement(updateQuery);
			Timestamp fechaHora = new Timestamp(System.currentTimeMillis());
			updateStatement.setTimestamp(1, fechaHora);
			updateStatement.setString(2, nombreUsuario);
			
			updateStatement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public Timestamp verUltimoLogin(String nombreUsuario) {
		try(Connection conexion = ConexionDB.crearConexion()){
			String query = "SELECT * FROM usuarios WHERE nombre_usuario = ?";
			PreparedStatement statement = conexion.prepareStatement(query);
			
			statement.setString(1, nombreUsuario);
			
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()) {
				return resultSet.getTimestamp("ultimo_login");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}







