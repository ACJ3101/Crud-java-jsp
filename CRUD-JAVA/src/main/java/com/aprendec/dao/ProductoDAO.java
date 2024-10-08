package com.aprendec.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.aprendc.conexion.Conexion;
import com.aprendec.model.Producto;

/**
 * Clase ProductoDAO para realizar las operaciones CRUD sobre la tabla de
 * productos en la base de datos.
 */
public class ProductoDAO {
	private Connection connection; // Conexión a la base de datos
	private PreparedStatement statement; // Statement para ejecutar las consultas SQL
	private boolean estadoOperacion; // Indicador del estado de la operación (éxito o fallo)

	/**
	 * Método para guardar un producto en la base de datos.
	 * 
	 * @param producto el producto a guardar.
	 * @return true si la operación fue exitosa, false si hubo un fallo o si el
	 *         producto ya existe.
	 * @throws SQLException en caso de error en la base de datos.
	 */
	public boolean guardar(Producto producto) throws SQLException {
		String sql = null;
		estadoOperacion = false;
		connection = obtenerConexion();

		// Validación para evitar duplicados por nombre
		if (obtenerProductoPorNombre(producto.getNombre()).getId() > 0) {
			return false; // Si el producto ya existe, retorna false
		}

		try {
			connection.setAutoCommit(false); // Deshabilita el autocommit para transacciones
			sql = "INSERT INTO productos (id, nombre, cantidad, precio, fecha_crear,fecha_actualizar) VALUES(?,?,?,?,?,?)";
			statement = connection.prepareStatement(sql);

			// Asignación de valores a los parámetros de la consulta
			statement.setString(1, null); // El id es autogenerado
			statement.setString(2, producto.getNombre());
			statement.setDouble(3, producto.getCantidad());
			statement.setDouble(4, producto.getPrecio());
			statement.setDate(5, producto.getFechaCrear());
			statement.setDate(6, producto.getFechaActualizar());

			estadoOperacion = statement.executeUpdate() > 0; // Verifica si se afectaron filas

			connection.commit(); // Confirma la transacción
			statement.close();
			connection.close();
		} catch (SQLException e) {
			connection.rollback(); // Revierte la transacción en caso de error
			e.printStackTrace();
		}

		return estadoOperacion;
	}

	/**
	 * Método para editar un producto existente en la base de datos.
	 * 
	 * @param producto el producto con los datos actualizados.
	 * @return true si la operación fue exitosa, false si hubo un fallo o si ya
	 *         existe un producto con el mismo nombre.
	 * @throws SQLException en caso de error en la base de datos.
	 */
	public boolean editar(Producto producto) throws SQLException {
		String sql = null;
		estadoOperacion = false;
		connection = obtenerConexion();

		// Verifica si ya existe un producto con el mismo nombre
		if (obtenerProductoPorNombre(producto.getNombre()).getId() > 0) {
			return false; // Si ya existe, no permite la actualización
		}

		try {
			connection.setAutoCommit(false);
			sql = "UPDATE productos SET nombre=?, cantidad=?, precio=?, fecha_actualizar=? WHERE id=?";
			statement = connection.prepareStatement(sql);

			// Asignación de valores a los parámetros de la consulta
			statement.setString(1, producto.getNombre());
			statement.setDouble(2, producto.getCantidad());
			statement.setDouble(3, producto.getPrecio());
			statement.setDate(4, producto.getFechaActualizar());
			statement.setInt(5, producto.getId());

			estadoOperacion = statement.executeUpdate() > 0;
			connection.commit(); // Confirma la transacción
			statement.close();
			connection.close();

		} catch (SQLException e) {
			connection.rollback(); // Revierte en caso de error
			e.printStackTrace();
		}

		return estadoOperacion;
	}

	/**
	 * Método para eliminar un producto de la base de datos.
	 * 
	 * @param idProducto el ID del producto a eliminar.
	 * @return true si la operación fue exitosa, false si hubo un fallo.
	 * @throws SQLException en caso de error en la base de datos.
	 */
	public boolean eliminar(int idProducto) throws SQLException {
		String sql = null;
		estadoOperacion = false;
		connection = obtenerConexion();
		try {
			connection.setAutoCommit(false);
			sql = "DELETE FROM productos WHERE id=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, idProducto);

			estadoOperacion = statement.executeUpdate() > 0; // Verifica si se eliminó el producto
			connection.commit(); // Confirma la transacción
			statement.close();
			connection.close();

		} catch (SQLException e) {
			connection.rollback(); // Revierte la transacción en caso de error
			e.printStackTrace();
		}

		return estadoOperacion;
	}

	/**
	 * Método para obtener una lista de todos los productos en la base de datos.
	 * 
	 * @return una lista de objetos Producto.
	 * @throws SQLException en caso de error en la base de datos.
	 */
	public List<Producto> obtenerProductos() throws SQLException {
		ResultSet resultSet = null;
		List<Producto> listaProductos = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm:ss");

		String sql = null;
		estadoOperacion = false;
		connection = obtenerConexion();

		try {
			sql = "SELECT * FROM productos";
			statement = connection.prepareStatement(sql);
			resultSet = statement.executeQuery(sql);

			// Itera sobre el resultado para poblar la lista de productos
			while (resultSet.next()) {
				Producto p = new Producto();
				p.setId(resultSet.getInt(1));
				p.setNombre(resultSet.getString(2));
				p.setCantidad(resultSet.getDouble(3));
				p.setPrecio(resultSet.getDouble(4));
				p.setFechaCrear(resultSet.getDate(5));
				p.setFechaActualizar(resultSet.getDate(6));
				listaProductos.add(p);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listaProductos;
	}

	/**
	 * Método para obtener un producto por su ID.
	 * 
	 * @param idProducto el ID del producto.
	 * @return el producto encontrado, o un producto vacío si no se encuentra.
	 * @throws SQLException en caso de error en la base de datos.
	 */
	public Producto obtenerProductoPorId(int idProducto) throws SQLException {
		ResultSet resultSet = null;
		Producto p = new Producto(); // Producto vacío en caso de no encontrar

		String sql = null;
		estadoOperacion = false;
		connection = obtenerConexion();

		try {
			sql = "SELECT * FROM productos WHERE id =?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, idProducto);

			resultSet = statement.executeQuery();

			if (resultSet.next()) {
				p.setId(resultSet.getInt(1));
				p.setNombre(resultSet.getString(2));
				p.setCantidad(resultSet.getDouble(3));
				p.setPrecio(resultSet.getDouble(4));
				p.setFechaCrear(resultSet.getDate(5));
				p.setFechaActualizar(resultSet.getDate(6));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return p;
	}

	/**
	 * Método para obtener un producto por su nombre.
	 * 
	 * @param nombre el nombre del producto.
	 * @return el producto encontrado, o un producto vacío si no se encuentra.
	 * @throws SQLException en caso de error en la base de datos.
	 */
	public Producto obtenerProductoPorNombre(String nombre) throws SQLException {
		ResultSet resultSet = null;
		Producto p = new Producto(); // Producto vacío en caso de no encontrar

		String sql = null;
		estadoOperacion = false;
		connection = obtenerConexion();

		try {
			sql = "SELECT * FROM productos WHERE nombre =?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, nombre);

			resultSet = statement.executeQuery();

			if (resultSet.next()) {
				p.setId(resultSet.getInt(1));
				p.setNombre(resultSet.getString(2));
				p.setCantidad(resultSet.getDouble(3));
				p.setPrecio(resultSet.getDouble(4));
				p.setFechaCrear(resultSet.getDate(5));
				p.setFechaActualizar(resultSet.getDate(6));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return p;
	}

	/**
	 * Método para obtener una conexión a la base de datos desde el pool de
	 * conexiones.
	 * 
	 * @return un objeto Connection.
	 * @throws SQLException en caso de error al obtener la conexión.
	 */
	private Connection obtenerConexion() throws SQLException {
		return Conexion.getConnection(); // Utiliza la clase de conexión externa
	}
}
