<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns="http://java.sun.com/xml/ns/javaee" xmlns:web="http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd"
	xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd"
	version="3.0">

	<servlet>
		<servlet-name>RPC</servlet-name>
		<servlet-class>${project.rootPackage}.server.RpcServiceImpl</servlet-class>
	</servlet>
	
	<servlet-mapping>
		<servlet-name>RPC</servlet-name>
		<url-pattern>/${project.name}/RPC</url-pattern>
	</servlet-mapping>
</web-app>