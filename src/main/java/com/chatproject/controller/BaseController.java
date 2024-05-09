package com.chatproject.controller;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
public class BaseController {
	public Connection getConnection() throws SQLException{
		String connectionUrl="jdbc:mysql://localhost:3307/chatprojectdb";
		Connection con=DriverManager.getConnection(connectionUrl,"root","root");
		return con;
	}

}
