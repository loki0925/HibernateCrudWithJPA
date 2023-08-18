package com.emp.DAO;




import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.emp.model.Employee;

public class EmpDAO {
	
	private String jdbcUrl = "jdbc:mysql://localhost:3306/demo?useSSL=false";

	private String jdbcUsername = "root";

	private String jdbcpassword = "1234";

	private static final String insertEmp = "insert into employee (eid,name,Age,Salary,skills, joining_date) "
			+ "values(?,?,?,?,?,?);";

	private static final String selectEmpById = "select * from employee where eid=?;";

	private static final String selectAllEmP = "select * from employee;";

	private static final String deleteEmpById = "delete  from employee where eid= ?;	";

	private static final String UpdateEmpById = "update employee set name=? ,  Age=?, Salary = ?, `Joining_date` = ? , skills = ?   where eid = ? ";
	
	private static final String selectAllEmPWithDept = "select * from employee;";
	
	int generateId;
	
	protected Connection getConnection() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcpassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;

	}

	public void insertEmp(Employee emp) throws SQLException {
		try (Connection connecion = getConnection(); PreparedStatement ps = connecion.prepareStatement(insertEmp, java.sql.Statement.RETURN_GENERATED_KEYS)) {
			
			ps.setInt(1, emp.getId());
			ps.setString(2, emp.getName());
			ps.setInt(3, emp.getAge());
			ps.setDouble(4, emp.getSalary());
			
			
			String strDate = "ChangeFor Date";
			
			ps.setString(6, strDate);
			
			ps.setString(5,  emp.getSkills());
			
			int affectedrows = ps.executeUpdate();
			
			if (affectedrows > 0) {
				ResultSet generatedKeys = ps.getGeneratedKeys();
				if (generatedKeys.next()) {
					generateId = generatedKeys.getInt(1);
				}
				generatedKeys.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public boolean updateEmployee(Employee emp) throws SQLException {
		boolean rowupdated = false;
		try (Connection connecion = getConnection(); PreparedStatement ps = connecion.prepareStatement(UpdateEmpById)) {
			
			ps.setString(1, emp.getName());
			ps.setInt(2, emp.getAge());
			ps.setDouble(3, emp.getSalary());
			

			String strDate =" change for date";
			
			ps.setString(4, strDate);
		    
			
			ps.setString(5, emp.getSkills());
			
			ps.setInt(6, emp.getId());
			
			rowupdated = ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rowupdated;

	}

	public Employee selectEmployee(int id) {
		Employee emp = null;
		try (Connection connecion = getConnection(); PreparedStatement ps = connecion.prepareStatement(selectEmpById)) {
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String name = rs.getString("name");
				int age = rs.getInt("Age");
				Double salary = rs.getDouble("Salary");
				
				String skill = rs.getString("Skills");
			
				
				emp = new Employee(id, name, age, salary,  skill);
				System.out.println("Dao employee " + emp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return emp;
	}

	public List<Employee> ListAllEmployee() {
		List<Employee> emp = new ArrayList<Employee>();
		try (Connection connecion = getConnection(); PreparedStatement ps = connecion.prepareStatement(selectAllEmPWithDept)) {
			System.out.println(ps);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("eid");
				String name = rs.getString("name");
				int age = rs.getInt("Age");
				Double salary = rs.getDouble("Salary");
				
				String str = rs.getString("Skills");
				
			

				emp.add(new Employee(id, name, age, salary,  str));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return emp;
	}

	public boolean deleteEmployee(int id) {
		boolean rowdeleted = false;
		try (Connection connecion = getConnection(); PreparedStatement ps = connecion.prepareStatement(deleteEmpById)) {
			ps.setInt(1, id);
			rowdeleted = ps.executeUpdate() > 0;
		} catch (Exception e) {
		}
		return rowdeleted;
	}

}

