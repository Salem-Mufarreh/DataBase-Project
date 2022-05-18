package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataBase {
	private String dbURL;
	private String dbUsername = "root";
	private String dbPassword = "Salemmuf2001@1";
	private String URL = "127.0.0.1";
	private String port = "3306";
	private String dbName = "dentalClinic";
	private Connection con;

	ArrayList<Employees> data = new ArrayList<Employees>();
	ObservableList<Employees> dataList;
	public String getData() throws ClassNotFoundException, SQLException {
		String SQL = null;

		SQL = "select Employee_id,Employee_Name,Address,Phone,Salary from Employee order by Employee_id";
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(SQL);

		while(rs.next()) {

			data.add(new Employees(Integer.parseInt(rs.getString(1)), rs.getString(2), rs.getString(3), Integer.parseInt(rs.getString(4)), Double.parseDouble(rs.getString(5))));
		}
		dataList = FXCollections.observableArrayList(data);
		rs.close();
		statement.close();
		con.close();

		return("connection Closed\t"+ "["+data.size()+"]\n");

	}
	public String connectDB() throws ClassNotFoundException, SQLException {
		dbURL = "jdbc:mysql://" + URL + ":" + port + "/" + dbName + "?verifyServerCertificate=false";
		Properties p = new Properties();
		p.setProperty("user", dbUsername);
		p.setProperty("password", dbPassword);
		p.setProperty("useSSL", "false");
		p.setProperty("autoReconnect", "true");
		Class.forName("com.mysql.jdbc.Driver");

		con = DriverManager.getConnection (dbURL, p);
		if(con != null) {
			return("Connection established\n");
		}
		else {
			return("Error Connecting to DataBase\n");
		}
	}
	
	
	public String updateName(String newName, int id) throws ClassNotFoundException, SQLException {

		connectDB();
		ExecuteStatement("UPDATE `employee` SET `Employee_Name` = '" + newName +"' WHERE (`Employee_id` = '"+ id +"');");
		con.close();
		return ("Name Updated For Employee id =" + id+"\n");
	}
	
	public String updateAddress(String newValue, int Old) throws ClassNotFoundException, SQLException {
		connectDB();
		ExecuteStatement("update Employee set Address= '" + newValue + "'where Employee_id = " + Old);
		con.close();
		return ("update Employee set Address= '" + newValue + "'where Employee_id = " + Old);
		
	}
	public String updatePhone(int newPhone, int id) throws SQLException, ClassNotFoundException {
		connectDB();
		ExecuteStatement("update Employee set Phone = '" + newPhone +"' where Employee_id =" + id);
		con.close();
		return("Phone Updated for Employe id = " + id +"\n");
	}
	
	public String updateSalary(double value , int id) throws ClassNotFoundException, SQLException {
		connectDB();
		ExecuteStatement("Update Employee set Salary = '" + value + "'where Employee_id = " + id);
		con.close();
		return ("Salary Updated For Employee id:" + id+ "\n");
	}
	
	public void ExecuteStatement(String SQL) throws SQLException {

		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate(SQL);
			stmt.close();


		}
		catch(SQLException s) {
			s.printStackTrace();
			System.out.println("SQL statement is not executed!");

		}


	}
	public String insertData(Employees data) throws SQLException, ClassNotFoundException {
		connectDB();
		ExecuteStatement("insert into Employee (Employee_id, Employee_Name, Address, Phone, Salary) Values (" +data.getEid() +",'"+data.getEname() +"','" + data.getEaddress() +"','" + data.getEphone() +"','" + data.getEsalary() +"');") ;
		con.close();
		return ("Employee:-\nid: " +data.getEid() +"\t Name: "+data.getEname() +"\nAddress: " + data.getEaddress() +"\tPhone: " + data.getEphone() +"\nSalary: " + data.getEsalary());
	
	}
	
	public String delete(int id) throws SQLException, ClassNotFoundException {
		connectDB();
		ExecuteStatement("Delete from Employee where (Employee_id ='" +id+"')");
		con.close();
		return ("Employee deleted Where id = " + id);
	}
	
	
}
