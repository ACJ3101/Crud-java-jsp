package com.aprendc.conexion;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

/**
 * Clase para manejar la conexión a la base de datos utilizando un pool de
 * conexiones.
 * 
 * <p>
 * Esta clase utiliza Apache DBCP (Database Connection Pool) para crear y
 * gestionar un pool de conexiones a la base de datos. Proporciona un método
 * estático para obtener una conexión a la base de datos.
 * </p>
 */
public class Conexion {

	// Instancia del pool de conexiones
	private static BasicDataSource dataSource = null;

	/**
	 * Método privado que inicializa y devuelve el DataSource (pool de conexiones).
	 * 
	 * <p>
	 * Si el DataSource aún no ha sido inicializado, este método lo configura con
	 * los parámetros de conexión, incluyendo el driver JDBC, las credenciales y las
	 * propiedades del pool, como el número máximo y mínimo de conexiones.
	 * </p>
	 * 
	 * @return DataSource configurado para el pool de conexiones.
	 */
	private static DataSource getDataSource() {
		if (dataSource == null) {
			// Inicializa el pool de conexiones si no existe
			dataSource = new BasicDataSource();
			dataSource.setDriverClassName("org.mariadb.jdbc.Driver"); // Driver JDBC de MariaDB
			dataSource.setUsername("root"); // Nombre de usuario de la base de datos
			dataSource.setPassword("123456"); // Contraseña del usuario
			dataSource.setUrl("jdbc:mariadb://localhost:3306/crud?useTimezone=true&serverTimezone=UTC"); // URL de la
																											// base de
																											// datos
			dataSource.setInitialSize(20); // Número inicial de conexiones en el pool
			dataSource.setMaxIdle(15); // Número máximo de conexiones inactivas en el pool
			dataSource.setMaxTotal(20); // Número máximo total de conexiones en el pool
		}
		return dataSource;
	}

	/**
	 * Obtiene una conexión del pool de conexiones.
	 * 
	 * <p>
	 * Este método llama a {@link #getDataSource()} para obtener el pool de
	 * conexiones y luego devuelve una conexión activa desde el pool.
	 * </p>
	 * 
	 * @return Connection activa a la base de datos.
	 * @throws SQLException si ocurre algún error al obtener la conexión.
	 */
	public static Connection getConnection() throws SQLException {
		return getDataSource().getConnection();
	}
}
