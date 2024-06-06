package modelos;

import controladores.ConexionDB;

import java.sql.*;
import java.util.ArrayList;

public class VentaDAO {

	public ArrayList<Venta> mostrarVentas() {
		
		ArrayList<Venta> ventas = new ArrayList<Venta>();
		
		try(Connection conexion = ConexionDB.crearConexion();
			Statement st = conexion.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM ventas");
		){
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String descripcion = rs.getString("descripcion");
				int cantidad = rs.getInt("cantidad");
				double precio = rs.getDouble("precio");
				double total = rs.getDouble("total");
				
				Venta venta = new Venta(id, descripcion, cantidad, precio, total);
				ventas.add(venta);
			}
			
		} catch (SQLException e) {
			System.out.println("Error al conectarse u obtener los datos: " + e.getMessage());
		} 
		
		return ventas;
		
	}
	
	public Venta mostrarVenta(int id) {
		Venta venta = null;
		
		try(Connection conexion = ConexionDB.crearConexion();
			Statement st = conexion.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM ventas WHERE id = " + id);
		){
			
			if(rs.next()) {
				String descripcion = rs.getString("descripcion");
				int cantidad = rs.getInt("cantidad");
				double precio = rs.getDouble("precio");
				double total = rs.getDouble("total");
				
				venta = new Venta(id, descripcion, cantidad, precio, total);
			}
			
		} catch (SQLException e) {
			System.out.println("Error al conectarse u obtener los datos: " + e.getMessage());
		} 
		
		return venta;
		
	}
	
	public boolean agregarVenta(Venta venta) {	
		
		try(Connection conexion = ConexionDB.crearConexion();
			PreparedStatement pst = conexion.prepareStatement("INSERT INTO ventas (descripcion, cantidad, precio, total) VALUES (?,?,?,?)")
		){
			pst.setString(1, venta.getDescripcion());
			pst.setInt(2, venta.getCantidad());
			pst.setDouble(3,  venta.getPrecio());
			pst.setDouble(4,  venta.getTotal());
			
			int filasAfectadas = pst.executeUpdate();
			if(filasAfectadas > 0) {
				return true;
			}
			
		} catch (SQLException e) {
			//System.out.println("Error al conectarse u obtener los datos: " + e.getMessage());
			e.printStackTrace();
		}
		
		return false;
		
	}
	
	public boolean actualizarVenta(Venta venta) {
		try(Connection conexion = ConexionDB.crearConexion();
			PreparedStatement pst = conexion.prepareStatement("UPDATE ventas SET descripcion = ?, cantidad = ?, precio = ?, total = ? WHERE id= ?");
		){
			pst.setString(1, venta.getDescripcion());
			pst.setInt(2, venta.getCantidad());
			pst.setDouble(3,  venta.getPrecio());
			pst.setDouble(4, venta.getTotal());
			pst.setInt(5, venta.getId());
			
			int filasAfectadas = pst.executeUpdate();
			if(filasAfectadas > 0 ) {
				System.out.println(filasAfectadas);
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean eliminarVenta(int id) {
		
		try(Connection conexion = ConexionDB.crearConexion();
			PreparedStatement pst = conexion.prepareStatement("DELETE FROM ventas WHERE id=?");
		){
			
			pst.setInt(1, id);
			int filasAfectadas = pst.executeUpdate();
			if(filasAfectadas > 0) {
				System.out.println(filasAfectadas);
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
