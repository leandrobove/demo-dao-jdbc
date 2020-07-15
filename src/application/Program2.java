package application;

import connection.DB;
import dao.DaoFactory;
import dao.DepartmentDao;
import entities.Department;

public class Program2 {

	public static void main(String[] args) {

		DepartmentDao depDao = DaoFactory.createDepartmentDao();

		System.out.println("=== Test 1 : insert department ==");

		Department novoDep = new Department(null, "Food");
		depDao.insert(novoDep);
		
		System.out.println("Inserted! New id = " + novoDep.getId());

		DB.closeConnection();
	}

}
