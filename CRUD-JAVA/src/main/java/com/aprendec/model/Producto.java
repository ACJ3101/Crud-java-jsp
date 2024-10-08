package com.aprendec.model;

import java.sql.Date;

/**
 * Clase Producto que representa un producto en la base de datos. Incluye los
 * atributos del producto como su id, nombre, cantidad, precio, fecha de
 * creación y fecha de actualización.
 */
public class Producto {

	// Atributos de la clase
	private int id;
	private String nombre;
	private double cantidad;
	private double precio;
	private Date fechaCrear; // Fecha de creación del producto
	private Date fechaActualizar; // Fecha de la última actualización del producto

	/**
	 * Constructor completo con todos los atributos.
	 * 
	 * @param id              el ID del producto
	 * @param nombre          el nombre del producto
	 * @param cantidad        la cantidad disponible del producto
	 * @param precio          el precio del producto
	 * @param fechaCrear      la fecha de creación del producto
	 * @param fechaActualizar la fecha de la última actualización del producto
	 */
	public Producto(int id, String nombre, double cantidad, double precio, Date fechaCrear, Date fechaActualizar) {
		super(); // Llama al constructor de la superclase (Object)
		this.id = id;
		this.nombre = nombre;
		this.cantidad = cantidad;
		this.precio = precio;
		this.fechaCrear = fechaCrear;
		this.fechaActualizar = fechaActualizar;
	}

	/**
	 * Constructor vacío que permite la creación de un objeto Producto sin valores
	 * iniciales. Útil para cuando se necesita instanciar el objeto antes de asignar
	 * valores.
	 */
	public Producto() {
		// Constructor vacío, se usa para crear instancias sin valores predefinidos.
	}

	// Métodos getter y setter para cada atributo

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getCantidad() {
		return cantidad;
	}

	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public Date getFechaCrear() {
		return fechaCrear;
	}

	public void setFechaCrear(Date fechaCrear) {
		this.fechaCrear = fechaCrear;
	}

	public Date getFechaActualizar() {
		return fechaActualizar;
	}

	public void setFechaActualizar(Date fechaActualizar) {
		this.fechaActualizar = fechaActualizar;
	}

	/**
	 * Sobrescribe el método toString para proporcionar una representación de la
	 * información del producto en formato de texto.
	 */
	@Override
	public String toString() {
		return "Producto [id=" + id + ", nombre=" + nombre + ", cantidad=" + cantidad + ", precio=" + precio
				+ ", fechaCrear=" + fechaCrear + ", fechaActualizar=" + fechaActualizar + "]";
	}
}
