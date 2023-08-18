package com.emp.controller;



import java.io.IOException;
import java.sql.SQLException;

import java.text.ParseException;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.emp.DAO.EmpDAO;
import com.emp.DAO.empDAOwithHib;

import com.emp.model.Employee;


@WebServlet(name = "empServ", urlPatterns = {"/employee", "/new", "/delete","/update","/insert","/edit","/list"})
public class EmpController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private EmpDAO empdao;
	private empDAOwithHib empdaohib;

	public EmpController() {
		this.empdao = new EmpDAO();
		this.empdaohib = new empDAOwithHib();
	}
	

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException {

	String action = request.getServletPath();
		
		switch (action) {
		case "/delete":
			try {
				deleteEmployee(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		default:
			try {
				listemp(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
         //System.out.println("hello this is deDelete method ");
     	//this.doGet(request,response);
	}
    
	
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		switch (action) {
    
		case "/update":
			try {
				updateEmployee(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		default:
			try {
				listemp(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		} //System.out.println("hello this is dePut method ");
	}

	@Override// employee/new == new//false
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		System.out.println(action);
		//System.out.println("do get method from emp cont start");
		switch (action) {
		// employee/new 
		case "/new":
			try {
				showNewForm(request, response);
				listemp(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		case "/edit":
			try {
				showEditForm(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		case "/delete":
			try {
				this.doDelete(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		default:
			try {
				System.out.println("i am default ");
				listemp(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
		System.out.println("hello this is doGet Method  end");
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		System.out.println(action);
		switch (action) {

		case "/insert":
			try {
				insertEmployee(request, response);

			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		case "/update":
			try {
				this.doPut(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		default:
			try {
				listemp(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
		System.out.println("hello this is doPost Method ");
	}
	
	private void listemp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<Employee> listemp = empdaohib.ListAllEmployee();
		
		request.setAttribute("listemp", listemp);
		System.out.println(listemp+"hiii");
		RequestDispatcher rd = request.getRequestDispatcher("/emp-update.jsp");
		 
		rd.forward(request, response);
	
	}

	private void updateEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ParseException {
		//System.out.println(request.toString()); 
		int id = Integer.parseInt(request.getParameter("id"));
		System.out.println(id);
		String name = request.getParameter("name");
		System.out.println(name);
		int age = Integer.parseInt(request.getParameter("age"));
		
		Double salary = Double.parseDouble(request.getParameter("salary"));
		
		// String skills = request.getParameter("skills");
		String skills = request.getParameter("skills");
		
		
		Employee emp = new Employee(id,name, age, salary, skills);
		empdaohib.updateEmployee(emp);
		//response.sendRedirect("list");
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int id = Integer.parseInt(request.getParameter("id"));
		Employee existingemp = empdao.selectEmployee(id);

		System.out.println("exist " + existingemp);
		RequestDispatcher rd = request.getRequestDispatcher("emp-update.jsp");
		request.setAttribute("emp", existingemp);
		
		rd.forward(request, response);
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("emp-update.jsp");
		rd.forward(request, response);
	}

	private void deleteEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		System.out.println(id);
		
		empdaohib.deleteEmployee(id);
		
		//response.sendRedirect("list");
	}

	private void insertEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ParseException {
        System.out.println("in insert Employee"); 
		String name = request.getParameter("name");
		String skills = request.getParameter("skills");
		int age = Integer.parseInt(request.getParameter("age"));
		Double salary = Double.parseDouble(request.getParameter("salary"));
		Employee emp = new Employee(name, age, salary, skills);
		System.out.println(emp + "hiii");
		empdaohib.insertEmp(emp);
		//response.sendRedirect("list");
	}

	
	
}

