package com.emp.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EmUtil {

	private static EntityManagerFactory emf = null;
	static {
		emf = Persistence.createEntityManagerFactory("employee");
	}

	public static EntityManager getEntityManager() {
		System.out.println("connecting");
		return emf.createEntityManager();
	}
}
