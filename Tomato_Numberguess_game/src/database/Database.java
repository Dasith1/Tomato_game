package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class Database {

	
	public static Connection conn() {
		 try {
			 Class.forName("com.mysql.cj.jdbc.Driver");
			 String url="jdbc:mysql://localhost:3306/tomatogamedb";
			 Connection conn = DriverManager.getConnection(url,"root","");
			 System.out.print("Connection OK");
			 return conn;
			 
			 
		 }catch(SQLException|ClassNotFoundException e) {
			 JOptionPane.showMessageDialog(null, e);
		 }
		 return null;
		
	}
	
}
