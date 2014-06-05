package com.example.it3197_casestudy.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.example.it3197_casestudy.model.User;
import com.mysql.jdbc.PreparedStatement;

import android.widget.Toast;

public class MySQLConnection {

	
	public static final String DRIVER = "com.mysql.jdbc.Driver";
	
	// Uncomment to connect local database
	
		public static final String URL = "jdbc:mysql://localhost:3306";
		public static final String USERNAME = "root";
		public static final String PASSWORD = "root";
		
		
		public boolean addUser(User user){
			String addUserSQL = "insert into userTable"
					+ "(username, password,email,address,age,phonenumber,usernric"
					+ "(?,?,?,?,?,?,?)";
			try {
				Connection conn = getConnection();
				PreparedStatement ps = (PreparedStatement) conn.prepareStatement(addUserSQL);
				ps.setString(1, user.getName());
				ps.setString(2, user.getPassword());
				ps.setString(3, user.getEmail());
				ps.setInt(4, user.getAge());
				ps.setString(5, user.getPhoneNumber());
				ps.setString(6, user.getUserNRIC());
				
				ps.executeUpdate();
				
				return true;
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			
			
		}
		   
		 private Connection getConnection() throws IllegalAccessException, InstantiationException, 
		 ClassNotFoundException, SQLException 
		  { 
			 Class.forName(DRIVER);
			 return DriverManager.getConnection(URL, USERNAME, PASSWORD);
		  }

}
