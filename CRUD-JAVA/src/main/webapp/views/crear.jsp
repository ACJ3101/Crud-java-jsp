<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/styles.css">
<title>Crear Producto</title>
</head>
<body>
	<h1>Crear Producto</h1>
	<h2><a href="index.jsp"> Ir a inicio</a></h2>
	<h2><% String mensaje = (String) request.getAttribute("mensaje"); %>
<% if (mensaje != null) { %>
    <p><%= mensaje %></p>
<% } %></h2>
	
	<form action="productos" method="post">
		<input type="hidden" name="opcion" value="guardar">
		<input type="hidden" name="tipo" value="uno">
		<table border="1">
			<tr>
				<td>Nombre:</td>
				<td><input type="text" name="nombre" ></td>
			</tr>
			<tr>
				<td>Cantidad:</td>
				<td><input type="text" name="cantidad" ></td>
			</tr>
			<tr>
				<td>Precio:</td>
				<td><input type="text" name="precio" ></td>
			</tr>
		</table>
		<input type="submit" value="Guardar">
	</form>
</body>
</html>