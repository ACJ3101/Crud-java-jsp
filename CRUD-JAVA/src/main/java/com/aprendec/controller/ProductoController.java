package com.aprendec.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aprendec.dao.ProductoDAO;
import com.aprendec.model.Producto;

/**
 * Servlet implementation class ProductoController.
 * 
 * <p>
 * Este servlet maneja las peticiones relacionadas con la tabla de productos,
 * incluyendo la creación, edición, eliminación y listado de productos.
 * </p>
 */
@WebServlet(description = "Administra peticiones para la tabla productos", urlPatterns = { "/productos" })
public class ProductoController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor del servlet ProductoController.
	 * 
	 * <p>
	 * Llama al constructor de la clase HttpServlet.
	 * </p>
	 */
	public ProductoController() {
		super();
		// Constructor vacío, llamado al iniciar el servlet
	}

	/**
	 * Método que maneja las peticiones GET al servlet.
	 * 
	 * <p>
	 * Dependiendo del parámetro "opcion", este método redirige a las vistas
	 * correspondientes para crear, listar, editar o eliminar productos.
	 * </p>
	 * 
	 * @param request  el objeto HttpServletRequest que contiene la solicitud del
	 *                 cliente
	 * @param response el objeto HttpServletResponse que contiene la respuesta para
	 *                 el cliente
	 * @throws ServletException si ocurre un error relacionado con el servlet
	 * @throws IOException      si ocurre un error de entrada/salida
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Obtiene la opción seleccionada por el usuario
		String opcion = request.getParameter("opcion");

		// Verifica la opción y ejecuta la lógica correspondiente
		if (opcion.equals("crear")) {
			System.out.println("Usted ha presionado la opción crear");
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/crear.jsp");
			requestDispatcher.forward(request, response);
		} else if (opcion.equals("listar")) {
			ProductoDAO productoDAO = new ProductoDAO();
			List<Producto> lista = new ArrayList<>();
			try {
				lista = productoDAO.obtenerProductos();
				for (Producto producto : lista) {
					System.out.println(producto);
				}
				// Añade la lista de productos al request para mostrarla en la vista
				request.setAttribute("lista", lista);
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/listar.jsp");
				requestDispatcher.forward(request, response);
			} catch (SQLException e) {
				e.printStackTrace(); // Manejo de excepciones en la obtención de productos
			}
			System.out.println("Usted ha presionado la opción listar");
		} else if (opcion.equals("meditar")) {
			// Opción para editar un producto
			int id = Integer.parseInt(request.getParameter("id"));
			System.out.println("Editar id: " + id);
			ProductoDAO productoDAO = new ProductoDAO();
			Producto p = new Producto();
			try {
				p = productoDAO.obtenerProductoPorId(id);
				System.out.println(p);
				// Pasa el producto seleccionado a la vista de edición
				request.setAttribute("producto", p);
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/editar.jsp");
				requestDispatcher.forward(request, response);
			} catch (SQLException e) {
				e.printStackTrace(); // Manejo de excepciones en la edición de productos
			}
		} else if (opcion.equals("eliminar")) {
			// Opción para eliminar un producto
			ProductoDAO productoDAO = new ProductoDAO();
			int id = Integer.parseInt(request.getParameter("id"));
			try {
				productoDAO.eliminar(id); // Elimina el producto con el ID especificado
				System.out.println("Registro eliminado satisfactoriamente...");
				// Después de eliminar, actualiza la lista de productos
				List<Producto> lista = productoDAO.obtenerProductos();
				request.setAttribute("lista", lista);
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/listar.jsp");
				requestDispatcher.forward(request, response);
			} catch (SQLException e) {
				e.printStackTrace(); // Manejo de excepciones en la eliminación de productos
			}
		}
	}

	/**
	 * Método que maneja las peticiones POST al servlet.
	 * 
	 * <p>
	 * Este método procesa las solicitudes de guardado y edición de productos,
	 * validando los datos ingresados y actuando en consecuencia.
	 * </p>
	 * 
	 * @param request  el objeto HttpServletRequest que contiene la solicitud del
	 *                 cliente
	 * @param response el objeto HttpServletResponse que contiene la respuesta para
	 *                 el cliente
	 * @throws ServletException si ocurre un error relacionado con el servlet
	 * @throws IOException      si ocurre un error de entrada/salida
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String opcion = request.getParameter("opcion"); // Obtiene la opción seleccionada (guardar o editar)
		String tipo = request.getParameter("tipo"); // Determina si es para crear o listar
		Date fechaActual = new Date(); // Obtiene la fecha actual

		// Opción para guardar un nuevo producto
		if (opcion.equals("guardar")) {
			ProductoDAO productoDAO = new ProductoDAO();
			Producto producto = new Producto();

			String nombre = request.getParameter("nombre");
			String cantidadStr = request.getParameter("cantidad");
			String precioStr = request.getParameter("precio");

			// Validación de campos vacíos
			if (nombre == null || nombre.isEmpty() || cantidadStr == null || cantidadStr.isEmpty() || precioStr == null
					|| precioStr.isEmpty()) {
				if (tipo.equals("uno")) {
					request.setAttribute("mensaje", "Todos los campos son obligatorios.");
					RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/crear.jsp");
					requestDispatcher.forward(request, response);
					return;
				}
				try {
					List<Producto> listaNueva = productoDAO.obtenerProductos();
					request.setAttribute("lista", listaNueva);
					request.setAttribute("mensaje", "Todos los campos son obligatorios.");
					RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/listar.jsp");
					requestDispatcher.forward(request, response);
					return;
				} catch (SQLException e) {
					System.out.println("Error al obtener la lista");
					request.setAttribute("mensaje", "Error al obtener la lista");
					RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/listar.jsp");
					requestDispatcher.forward(request, response);
				}
			}

			// Si los campos están completos, se guarda el producto en la base de datos
			try {
				producto.setNombre(nombre);
				producto.setCantidad(Double.parseDouble(cantidadStr));
				producto.setPrecio(Double.parseDouble(precioStr));
				producto.setFechaCrear(new java.sql.Date(fechaActual.getTime()));

				if (productoDAO.guardar(producto)) {
					request.setAttribute("mensaje", "El producto se ha guardado exitosamente.");
					if (tipo.equals("uno")) {
						RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/crear.jsp");
						requestDispatcher.forward(request, response);
						return;
					}
					List<Producto> listaNueva = productoDAO.obtenerProductos();
					request.setAttribute("lista", listaNueva);
					RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/listar.jsp");
					requestDispatcher.forward(request, response);
					return;
				}

				// Mensaje de error si el producto ya existe
				if (tipo.equals("uno")) {
					request.setAttribute("mensaje", "Error, este producto ya existe.");
					RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/crear.jsp");
					requestDispatcher.forward(request, response);
					return;
				}
				List<Producto> listaNueva = productoDAO.obtenerProductos();
				request.setAttribute("lista", listaNueva);
				request.setAttribute("mensaje", "Error, este producto ya existe.");
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/listar.jsp");
				requestDispatcher.forward(request, response);
				return;

			} catch (NumberFormatException e) {
				request.setAttribute("mensaje", "Los campos de cantidad y precio deben ser numéricos.");
				if (tipo.equals("uno")) {
					RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/crear.jsp");
					requestDispatcher.forward(request, response);
				}
				try {
					List<Producto> listaNueva = productoDAO.obtenerProductos();
					request.setAttribute("lista", listaNueva);
					request.setAttribute("mensaje", "Los campos de cantidad y precio deben ser numéricos.");
					RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/listar.jsp");
					requestDispatcher.forward(request, response);
					return;
				} catch (SQLException e1) {
					request.setAttribute("mensaje", "Error al acceder a la base de datos.");
					RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/listar.jsp");
					requestDispatcher.forward(request, response);
				}
				return;
			} catch (SQLException e) {
				request.setAttribute("mensaje", "Error al acceder a la base de datos.");
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/listar.jsp");
				requestDispatcher.forward(request, response);
			}

		} else if (opcion.equals("editar")) {
			// Opción para editar un producto existente
			Producto producto = new Producto();
			ProductoDAO productoDAO = new ProductoDAO();

			try {
				producto.setId(Integer.parseInt(request.getParameter("id")));
				producto.setNombre(request.getParameter("nombre"));
				producto.setCantidad(Double.parseDouble(request.getParameter("cantidad")));
				producto.setPrecio(Double.parseDouble(request.getParameter("precio")));
				producto.setFechaActualizar(new java.sql.Date(fechaActual.getTime()));
			} catch (Exception e) {
				try {
					Producto producto2 = productoDAO.obtenerProductoPorId(Integer.parseInt(request.getParameter("id")));
					request.setAttribute("mensaje", "Los campos de cantidad y precio deben ser numéricos.");
					request.setAttribute("producto", producto2);
					RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/editar.jsp");
					requestDispatcher.forward(request, response);
				} catch (NumberFormatException | SQLException e1) {
					e1.printStackTrace();
				}
			}

			try {
				if (productoDAO.editar(producto)) {
					List<Producto> listaNueva = productoDAO.obtenerProductos();
					request.setAttribute("mensaje", "Producto actualizado correctamente.");
					request.setAttribute("lista", listaNueva);
					RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/listar.jsp");
					requestDispatcher.forward(request, response);
				}

				request.setAttribute("mensaje", "Error, el nombre no puede ser igual a uno existente.");
				request.setAttribute("producto", producto);
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/editar.jsp");
				requestDispatcher.forward(request, response);
			} catch (SQLException e) {
				request.setAttribute("mensaje", "Error al acceder a la base de datos.");
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/editar.jsp");
				requestDispatcher.forward(request, response);
			}
		}
	}
}
