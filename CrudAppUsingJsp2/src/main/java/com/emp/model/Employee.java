package com.emp.model;




import java.io.Serializable;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity(name="employee")
public class Employee implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int eid;
	private String name;
	private int age;
	private Double salary;
	private String skills;

	
	

	public Employee() {
		super();
		
	}

	public Employee(int id, String name, int age, Double salary,  String skills, int deptid) {
		super();
		this.eid = id;
		this.name = name;
		this.age = age;
		this.salary = salary;
		
		this.skills = skills;
		
	}

	public Employee(String name, int age, Double salary, String skills, int deptid) {
		super();
		this.name = name;
		this.age = age;
		this.salary = salary;
		
		this.skills = skills;
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	public Employee(String name, int age, Double salary, String skills) {
		super();
		this.name = name;
		this.age = age;
		this.salary = salary;
	
		this.skills = skills;
	}

	public Employee(int id, String name, int age, Double salary) {
		super();
		this.eid = id;
		this.name = name;
		this.age = age;
		this.salary = salary;
		
	}

	public Employee(int id, String name, int age, Double salary, String skills) {
		super();
		this.eid = id;
		this.name = name;
		this.age = age;
		this.salary = salary;
		
		this.skills = skills;
		
	}

	public int getId() {
		return eid;
	}

	public void setId(int id) {
		this.eid = id;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}




	



}
