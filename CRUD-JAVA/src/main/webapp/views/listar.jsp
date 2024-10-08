<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/CSS/styles.css">
<title>Listar Productos</title>
</head>
<body>
	<h1>Listar Productos</h1>
	<h2>
		<a href="index.jsp"> Ir a inicio</a>
	</h2>
	<table border="1">
		<tr>
			<td>Editar</td>
			<td>Id</td>
			<td>Nombre</td>
			<td>Cantidad</td>
			<td>Precio</td>
			<td>Fecha Creacion</td>
			<td>Fecha Actualizacion</td>
			<td>Accion</td>
		</tr>
		<c:forEach var="producto" items="${lista}">
			<tr>
			<td><button><a href="productos?opcion=meditar&id=<c:out value="${ producto.id}"></c:out>">Editar</button></td>
				<td><c:out value="${ producto.id}"></c:out></td>
				<td><c:out value="${ producto.nombre}"></c:out></td>
				<td><c:out value="${ producto.cantidad}"></c:out></td>
				<td><c:out value="${ producto.precio}"></c:out></td>
				<td><fmt:formatDate value="${producto.fechaCrear}"
						pattern="EEEE, d 'de' MMMM 'de' yyyy" /></td>
				<td><fmt:formatDate value="${producto.fechaActualizar}"
						pattern="EEEE, d 'de' MMMM 'de' yyyy " /></td>
				<td><a
					href="productos?opcion=eliminar&id=<c:out value="${ producto.id}"></c:out>">
						Eliminar </a></td>
			</tr>
		</c:forEach>
	</table>
	<div>
		<h1>Crear Producto</h1>
		<form action="productos" method="post">
			<input type="hidden" name="opcion" value="guardar"> <input
				type="hidden" name="tipo" value="dos">

			<table border="1">
				<tr>
					<td>Nombre:</td>
					<td><input type="text" name="nombre" size="50"></td>
				</tr>
				<tr>
					<td>Cantidad:</td>
					<td><input type="text" name="cantidad" size="50"></td>
				</tr>
				<tr>
					<td>Precio:</td>
					<td><input type="text" name="precio" size="50"></td>
				</tr>
			</table>
			<input type="submit" value="Guardar">
		</form>

		<%
		String mensaje = (String) request.getAttribute("mensaje");
		%>
		<%
		if (mensaje != null) {
		%>
		<h2><%=mensaje%></h2>
		<%
		}
		%>

	</div>
</body>
</html>